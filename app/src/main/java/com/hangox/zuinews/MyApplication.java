package com.hangox.zuinews;

import android.app.Application;

import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.network.RequestManager;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/4
 * Time 下午10:20
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.I.init(this);
        Db.init(this);
    }
}
