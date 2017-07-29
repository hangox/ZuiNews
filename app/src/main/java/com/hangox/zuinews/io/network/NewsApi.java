package com.hangox.zuinews.io.network;

import android.util.ArrayMap;

import com.android.volley.Request;
import com.hangox.zuinews.error.ShowApiError;
import com.hangox.zuinews.io.bean.ChannelApiBean;
import com.hangox.zuinews.io.bean.NewsApiBean;
import com.hangox.zuinews.io.bean.NewsPageBean;
import com.hangox.zuinews.io.bean.ShowApiBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/5
 * Time 下午9:52
 */

public class NewsApi {


    public static int STATE_CODE_SUCCESS = 0;
    public static int RES_CODE_SUCCESS = 0;


    private static Consumer<ShowApiBean> createErrorCheck(){
        return showApiBean -> {
            if(STATE_CODE_SUCCESS != showApiBean.getShowApiResCode()){
                throw new ShowApiError(showApiBean);
            }else if(RES_CODE_SUCCESS !=  showApiBean.getShowApiResBody().getRetCode()){
                throw new ShowApiError("resCode error");
            }
        };

    }
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
                .rx()
                .doOnNext(createErrorCheck());

    }




    /**
     * 获取新闻信息
     * @param channelId
     * @param page
     * @param tag
     * @return
     */
    public static Observable<NewsPageBean> requestNewsList(String channelId,
                                                           int page, int pageSize, boolean isNeedHtml,Object tag){
        Map<String,String> parameter = new ParameterFactory()
                .add("channelId",channelId)
                .add("page",page)
                .add("needContent",0)
                .add("needHtml", isNeedHtml ? 1 : 0)
                .add("needAllList",0)
                .add("maxResult",pageSize)
                .create();
        return createNewsListRequest(tag, parameter);
    }

    /**
     * 创建请求News
     * @param tag
     * @param parameter
     * @return
     */
    private static Observable<NewsPageBean> createNewsListRequest(Object tag, Map<String, String> parameter) {
        Map<String,String> header = new ArrayMap<>();
        header.put("enctype","application/x-www-form-urlencoded");

        return new RxGsonRequest.Builder<NewsApiBean>()
                .setClass(NewsApiBean.class)
                .setMethod(Request.Method.GET)
                .setParameter(parameter)
                .setHeader(header)
                .setUrl("http://route.showapi.com/109-35")
                .setTag(tag)
                .build()
                .bindToQueue(RequestManager.Q())
                .rx()
                .doOnNext(createErrorCheck())
                .map(newsApiBean -> newsApiBean.getShowApiResBody().getPagebean());
    }



    public static Observable<NewsPageBean> requestNewsById(String newsId,Object tag){
        Map<String,String> parameter = new ParameterFactory()
                .add("id",newsId)
                .add("needHtml",1)
                .create();

        return createNewsListRequest(tag,parameter);
    }


}
