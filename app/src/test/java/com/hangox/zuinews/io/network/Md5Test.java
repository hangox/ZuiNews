package com.hangox.zuinews.io.network;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/8
 * Time 下午2:00
 */


public class Md5Test {


    @Test
    public void getString() throws Exception {
        assertEquals(new Md5().getString("121636767167367167167312673217"), "8A724A0ABB78DC8ABEA29A4852E90E04");
    }


}