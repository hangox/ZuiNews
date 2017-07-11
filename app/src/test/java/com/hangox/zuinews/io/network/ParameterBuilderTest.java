package com.hangox.zuinews.io.network;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/8
 * Time 下午5:23
 */
public class ParameterBuilderTest {
    @Test
    public void put() throws Exception {
        String key = "name",value = "Nici";
        ParameterBuilder builder = new ParameterBuilder();
        Map<String,String> map = builder.put(key,value).create();
        Assert.assertTrue(map.containsKey(key));
        Assert.assertEquals(map.get(key),value);
    }

    @Test
    public void create() throws Exception {
        String key = "name",value = "Nici";
        ParameterBuilder builder = new ParameterBuilder();
        Map<String,String> map = builder.put(key,value).create();
        Assert.assertTrue(map.containsKey(key));
        Assert.assertEquals(map.get(key),value);

    }

}