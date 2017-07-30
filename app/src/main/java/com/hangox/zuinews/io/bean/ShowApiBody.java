package com.hangox.zuinews.io.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/22
 * Time 下午5:08
 */

public class ShowApiBody  {
    @SerializedName("ret_code")
    protected int retCode;

    public int getRetCode() {
        return retCode;
    }

    public ShowApiBody setRetCode(int retCode) {
        this.retCode = retCode;
        return this;
    }

}
