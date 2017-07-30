package com.hangox.zuinews.io.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Arrays;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/4
 * Time 下午10:20
 */

public class RequestManager {

    public static final RequestManager I = new RequestManager();


    private RequestManager(){}

    private RequestQueue mRequestQueue;

    public void init(Context context){
//        RequestQueue queue = Volley.newRequestQueue(this, new StethoVolleyStack());
        mRequestQueue = Volley.newRequestQueue(context,new OkHttp3Stack(Arrays.asList(new StethoInterceptor())));
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static RequestQueue Q(){
        return I.mRequestQueue;
    }


}
