package com.hangox.zuinews.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hangox.zuinews.BuildConfig;
import com.hangox.zuinews.data.NewsData;
import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.bean.NewsContentBean;
import com.hangox.zuinews.io.bean.NewsPageBean;
import com.hangox.zuinews.io.entry.ChannelEntity;
import com.hangox.zuinews.io.entry.NewsEntity;
import com.hangox.zuinews.io.network.NetworksUtils;
import com.hangox.zuinews.io.network.RequestManager;

import hugo.weaving.DebugLog;
import io.reactivex.schedulers.Schedulers;


/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/16
 * Time 下午8:46
 */

public class NewsUpdateService extends Service {

    public static final String ACTION_UPDATE = BuildConfig.APPLICATION_ID + ".UPDATE";
    public static final String ACTION_STOP = BuildConfig.APPLICATION_ID + ".STOP";

    private NewsData data = new NewsData();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @DebugLog
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (ACTION_UPDATE.equals(intent.getAction())) {
                if(NetworksUtils.isNetworkUp(this)) {
                    startUpdateDatas();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startUpdateDatas() {
        Db.session().getChannelEntityDao()
                .queryBuilder()
                .rx()
                .list()
                .subscribe(channelEntities -> {
                    for (ChannelEntity channelEntity : channelEntities) {
                        //只要同步200条就好了，再多就无用了
                        data.requestNewsList(channelEntity.getChannelId(), 1, 200, true, NewsUpdateService.this)
                                .map(NewsPageBean::getContentlist)
                                .observeOn(Schedulers.io())
                                .doOnNext(newsContentBeen -> {
                                    for (NewsContentBean newsContentBean : newsContentBeen) {
                                        Db.session().insertOrReplace(new NewsEntity(newsContentBean));
                                    }
                                }).subscribe(newsContentBeen -> {}, Throwable::printStackTrace);
                    }
                });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestManager.Q().cancelAll(this);
    }
}
