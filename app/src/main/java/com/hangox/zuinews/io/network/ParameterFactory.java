package com.hangox.zuinews.io.network;

import android.support.v4.util.ArrayMap;

import com.hangox.zuinews.AppConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/13
 * Time 上午9:39
 */

public class ParameterFactory {

    private ArrayMap<String,String> mParamter = new ArrayMap<>();

    private final static ArrayMap<String,String> DEFAULT_PARAMETER = new ArrayMap<>();
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);

    static {
        DEFAULT_PARAMETER.put("showapi_appid", AppConfig.SHOW_API_ID);
        DEFAULT_PARAMETER.put("showapi_sign_method", "md5");
        DEFAULT_PARAMETER.put("showapi_res_gzip", "0");
    }


    /**
     * 获取默认的参数
     * @return
     */
    private static ArrayMap<String,String> getDefaultParameter(){
        DEFAULT_PARAMETER.put("showapi_timestamp",SIMPLE_DATE_FORMAT.format(new Date()));
        return new ArrayMap<>(DEFAULT_PARAMETER);
    }

    /**
     * 生成最后的参数
     * @param keyValues
     * @return
     */
    private static Map<String,String> generate(Object ... keyValues){
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
        signParameter(map);
        return map;
    }


    /**
     * 生成最后的参数
     * @param parameter
     * @return
     */
    private static Map<String,String> generate(Map<String,String> parameter){
        ArrayMap<String,String> map = getDefaultParameter();
        map.putAll(parameter);
        signParameter(map);
        return map;
    }

    /**
     * 签名参数
     * @param map
     */
    private static void signParameter(ArrayMap<String,String> map){
        map.put("showapi_sign",generateSign(map));
    }

    /**
     * 根据参数生成签名
     * @param map
     * @return
     */
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

    public ParameterFactory add(Object key,Object value){
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        mParamter.put(key.toString(),value.toString());
        return this;
    }

    public Map<String,String> create(){
        return generate(mParamter);
    }

}
