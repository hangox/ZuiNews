package com.hangox.zuinews;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.network.RequestManager;

import timber.log.Timber;

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
        Db.init(this);
        Stetho.initializeWithDefaults(this);

        RequestManager.I.init(this);


        Timber.plant(new Timber.DebugTree());




    }
}
