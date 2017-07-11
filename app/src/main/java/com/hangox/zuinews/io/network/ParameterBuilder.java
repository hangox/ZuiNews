package com.hangox.zuinews.io.network;

import android.support.v4.util.ArrayMap;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/8
 * Time 下午5:17
 * 方便穿件参数
 */

public class ParameterBuilder {
    private final ArrayMap<String,String> mParams;
    public ParameterBuilder() {
        this(new ArrayMap<String,String>());
    }

    public ParameterBuilder(ArrayMap<String, String> params) {
        if(params == null) throw new IllegalArgumentException("params can not be null");
        mParams = params;
    }

    public ParameterBuilder put(Object key, Object value){
        if(key == null){
            throw new IllegalArgumentException("null can not be null");
        }
        if(value == null){
            mParams.put(key.toString(),null);
        }else{
            mParams.put(key.toString(),value.toString());
        }
        return this;
    }


    public ArrayMap<String,String> create(){
        return mParams;
    }
}
