package com.hangox.zuinews.io.network;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/9
 * Time 上午11:37
 */
public class NetworksUtilsTest {
    @Test
    public void buildParameter() throws Exception {
        Map<String,String> parameter = new android.support.v4.util.ArrayMap<>();
        parameter.put("name","永旺");
        parameter.put("sex","男");
        parameter.put("address","广东");
        String encode = NetworksUtils.buildParameter(parameter,"utf-8");
        String result = "name=%E6%B0%B8%E6%97%BA&sex=%E7%94%B7&address=%E5%B9%BF%E4%B8%9C";

        Map<String, String> sourceMap = beMap(encode);
        Map<String,String> resultMap = beMap(result);

        for (Map.Entry<String, String> entry : sourceMap.entrySet()) {
            Assert.assertEquals(resultMap.get(entry.getKey()), entry.getValue());
        }
    }

    private Map<String, String> beMap(String s){
        Map<String, String> map = new HashMap<>();
        String[] split = s.split("&");
        for (int i = 0; i < split.length; i++) {
            String[] keyValues = split[i].split("=");
            map.put(keyValues[0],keyValues[1]);
        }
        return map;
    }

}