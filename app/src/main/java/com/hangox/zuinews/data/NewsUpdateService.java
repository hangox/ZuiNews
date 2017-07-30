package com.hangox.zuinews.data;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hangox.xlog.XLog;
import com.hangox.zuinews.BuildConfig;
import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.bean.ChannelApiBean;
import com.hangox.zuinews.io.bean.ChannelBean;
import com.hangox.zuinews.io.entry.ChannelEntity;
import com.hangox.zuinews.io.network.NewsApi;

import java.util.List;

import io.reactivex.schedulers.Schedulers;


/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/16
 * Time 下午8:46
 */

public class NewsUpdateService extends Service {

    public static final String ACTION_UPDATE_ALL = BuildConfig.APPLICATION_ID + ".UPDATE_ALL_NEWS";
    public static final String ACTION_UPDATE_SINGLE_CHANNLE = BuildConfig.APPLICATION_ID + ".UPDATE_SINGLE_CHANNEL";
    public static final String EXTRA_CHANNEL_ID = "exChannelId";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (ACTION_UPDATE_ALL.equals(intent.getAction())) {
                startUpdateDatas();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startUpdateDatas() {
        NewsApi.requestNewsChannel(null)
                .observeOn(Schedulers.io())
                .subscribe(channelApiBean -> {
                    ChannelApiBean.ShowapiResBodyBean bodyBean = channelApiBean.getShowApiResBody();
                    Db.session().getChannelEntityDao().deleteAll();
                    List<ChannelBean> list = bodyBean.getChannelList();
                    XLog.v("update channel list size " + list.size());
                    for (ChannelBean channelBean : list) {
                        XLog.v("channel " + channelBean);
                        Db.session().getChannelEntityDao().insert(new ChannelEntity(channelBean));
                    }
                }, Throwable::printStackTrace);

    }

//
//    private void startUpdateChannel(String channelId, int page) {
//        NewsApi.requestNewsList(channelId, page, null)
//                .subscribe(newsApiBean -> {
//                     list = newsApiBean.getShowApiResBody().getPagebean();
//                }, Throwable::printStackTrace);
//    }

}
