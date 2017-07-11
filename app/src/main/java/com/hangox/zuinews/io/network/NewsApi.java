package com.hangox.zuinews.io.network;

import android.support.v4.util.ArrayMap;

import com.android.volley.Request;
import com.hangox.zuinews.AppConfig;
import com.hangox.zuinews.io.bean.ChannelListBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/5
 * Time 下午9:52
 */

public class NewsApi {


    private final static ArrayMap<String,String> DEFAULT_PARAMETER = new ArrayMap<>();
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);

    static {
        DEFAULT_PARAMETER.put("showapi_appid", AppConfig.SHOW_API_ID);
        DEFAULT_PARAMETER.put("showapi_sign_method", "md5");
        DEFAULT_PARAMETER.put("showapi_res_gzip", "0");
    }


    public static ArrayMap<String,String> getDefaultParameter(){
        DEFAULT_PARAMETER.put("showapi_timestamp",SIMPLE_DATE_FORMAT.format(new Date()));
        return new ArrayMap<>(DEFAULT_PARAMETER);
    }

    private static Map<String,String> generateParameter(Object ... keyValues){
        if(keyValues.length % 2 != 0) {
            throw new IllegalArgumentException("key value 不符合");
        }

        ArrayMap<String, String> map = getDefaultParameter();
        if(keyValues.length != 0){
            for (int i = 0; i < keyValues.length; i += 2) {
                Object key = keyValues[i];
                Object value = keyValues[i + 1];
                if (key == null || value == null) {
                    throw new IllegalArgumentException("key or value can not be null");
                }
                map.put(key.toString(), value.toString());
            }
        }

        map.put("showapi_sign",generateSign(map));
        return map;
    }

    private static String generateSign(ArrayMap<String, String> map) {
        //获取所有的key
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            keys.add(map.keyAt(i));
        }
        Collections.sort(keys);

        //构建需要签名的串
        StringBuilder builder = new StringBuilder();
        for (String key : keys) {
            builder.append(key).append(map.get(key));
        }
        builder.append(AppConfig.SHOW_API_SECRET);
        return new Md5().getString(builder.toString());
    }

    public static Observable<ChannelListBean> requestNewsChannel(Object tag){
        RxGsonRequest<ChannelListBean> request = new RxGsonRequest.Builder<ChannelListBean>()
                .setClass(ChannelListBean.class)
                .setMethod(Request.Method.GET)
                .setParameter(generateParameter())
                .setUrl("http://route.showapi.com/109-34")
                .setTag(tag)
                .build();

        return request.bindToQueue(RequestManager.I.getRequestQueue()).rx();

    }

}
