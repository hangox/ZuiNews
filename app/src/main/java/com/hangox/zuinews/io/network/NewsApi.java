package com.hangox.zuinews.io.network;

import com.android.volley.Request;
import com.hangox.zuinews.io.bean.ChannelApiBean;
import com.hangox.zuinews.io.bean.NewsApiBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/5
 * Time 下午9:52
 */

public class NewsApi {




    /**
     * 获取新闻频道
     * @param tag
     * @return
     */
    public static Observable<ChannelApiBean> requestNewsChannel(Object tag){
        return new RxGsonRequest.Builder<ChannelApiBean>()
                .setClass(ChannelApiBean.class)
                .setMethod(Request.Method.GET)
                .setParameter(new ParameterFactory().create())
                .setUrl("http://route.showapi.com/109-34")
                .setTag(tag)
                .build()
                .bindToQueue(RequestManager.Q())
                .rx();

    }


    /**
     * 获取新闻信息
     * @param channelId
     * @param page
     * @param tag
     * @return
     */
    public static Observable<NewsApiBean> requestNewsList(String channelId, int page, Object tag){
        Map<String,String> parameter = new ParameterFactory()
                .add("channel",channelId)
                .add("page",page)
                .add("needContent",0)
                .add("needHtml",0)
                .add("needAllList",0)
                .add("maxResult",20)
                .create();
        return new RxGsonRequest.Builder<NewsApiBean>()
                .setClass(NewsApiBean.class)
                .setMethod(Request.Method.GET)
                .setParameter(parameter)
                .setUrl("http://route.showapi.com/109-35")
                .setTag(tag)
                .build()
                .bindToQueue(RequestManager.Q())
                .rx();
    }




}
