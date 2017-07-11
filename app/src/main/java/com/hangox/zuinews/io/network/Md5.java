package com.hangox.zuinews.io.network;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/8
 * Time 下午1:53
 * 一个MD5 实现的类
 */

public class Md5 {

    private MessageDigest mDigest;

    public Md5() {
        try {
            mDigest = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取MD5 string
     * @param source
     * @return
     */
    public String getString(String source){
        return String.format("%032X", new BigInteger(1,mDigest.digest(source.getBytes())));
    }
}
