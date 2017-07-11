package com.hangox.zuinews.ui;

import android.databinding.ViewDataBinding;

import com.hangox.databinding.context.BindingActivity;
import com.hangox.zuinews.io.network.RequestManager;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/10
 * Time 上午10:15
 */

public abstract class MyActivity<T extends ViewDataBinding> extends BindingActivity<T> {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.Q().cancelAll(this);
    }
}
