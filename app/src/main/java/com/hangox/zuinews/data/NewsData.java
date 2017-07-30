package com.hangox.zuinews.data;

import android.content.Context;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangox.xlog.XLog;
import com.hangox.zuinews.error.ShowApiError;
import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.bean.ChannelBean;
import com.hangox.zuinews.io.bean.NewsPageBean;
import com.hangox.zuinews.io.entry.ChannelEntity;
import com.hangox.zuinews.io.entry.ChannelEntityDao;
import com.hangox.zuinews.io.entry.NewsEntity;
import com.hangox.zuinews.io.entry.NewsEntityDao;
import com.hangox.zuinews.io.network.NewsApi;
import com.hangox.zuinews.io.network.RequestManager;
import com.hangox.zuinews.io.network.RxGsonRequest;

import org.greenrobot.essentials.io.IoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/14
 * Time 下午10:09
 */

public class NewsData {


    public Observable<List<ChannelEntity>> getChannelList(Context context) {
        return Db.session()
                .getChannelEntityDao()
                .queryBuilder()
                .rx()
                .list()
                .map(channelEntities -> {
                    //列表为空就读取缓存
                    if (channelEntities.isEmpty()) {
                        try {
                            InputStream stream = context.getResources().getAssets().open("channel.json");
                            String chanelJson = new String(IoUtils.readAllBytes(stream));
                            List<ChannelBean> beans =
                                    new Gson().fromJson(chanelJson,
                                            new TypeToken<ArrayList<ChannelBean>>() {
                                            }.getType());

                            for (ChannelBean bean : beans) {
                                ChannelEntity entity = new ChannelEntity(bean);
                                Db.session().getChannelEntityDao().insert(entity);
                                channelEntities.add(entity);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return channelEntities;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 请求更新Channel
     */
    public Observable<List<ChannelBean>> requestUpdateChannel() {
        return NewsApi.requestNewsChannel(null)
                .doOnNext(channelApiBean -> {
                    if (channelApiBean.getShowApiResCode() != NewsApi.STATE_CODE_SUCCESS) {
                        throw new ShowApiError(channelApiBean);
                    } else if (channelApiBean.getShowApiResBody().getRetCode() != NewsApi.STATE_CODE_SUCCESS) {
                        throw new ShowApiError("未知错误", channelApiBean.getShowApiResBody().getRetCode());
                    }
                })
                .map(channelApiBean -> channelApiBean.getShowApiResBody().getChannelList())
                .observeOn(Schedulers.io())
                .doOnNext(channelList -> {
                    ChannelEntityDao dao = Db.session().getChannelEntityDao();
                    dao.deleteAll();
                    for (ChannelBean channelBean : channelList) {
                        dao.insert(new ChannelEntity(channelBean));
                    }
                    XLog.v("insert channel " + channelList.size());
                })
                .observeOn(AndroidSchedulers.mainThread());

    }
//
//    public static void requestUpdateChannelList(String channelId,int page,){
//        return NewsApi.requestNewsList(channelId,page,null)
//    }


    public Observable<List<NewsEntity>> getNewsList(String channelId, int pageSize) {
        return Db.session().getNewsEntityDao().queryBuilder()
                .orderAsc(NewsEntityDao.Properties.Date)
                .where(NewsEntityDao.Properties.ChannelId.eq(channelId))
                .limit(pageSize)
                .rx()
                .list()
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<NewsPageBean> requestNewsList(String channelId, int page, int pageSize, boolean isNeedHtml, Object tag) {
        return NewsApi.requestNewsList(channelId, page, pageSize, isNeedHtml, tag);
    }


    public Observable<NewsPageBean> requestNewsList(String channelId, int page, Object tag) {
        return NewsApi.requestNewsList(channelId, page, 20, true, tag);
    }


    /**
     * @param link
     * @param tag
     * @return
     */
    public Observable<String> requestNewsDetail(String link, Object tag) {
        return new RxGsonRequest.Builder<String>()
                .setTag(tag)
                .setClass(String.class)
                .setMethod(Request.Method.GET)
                .setUrl(link)
                .build()
                .bindToQueue(RequestManager.Q())
                .rx();
    }
}
