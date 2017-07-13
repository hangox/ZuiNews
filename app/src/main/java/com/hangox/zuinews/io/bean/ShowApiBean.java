package com.hangox.zuinews.io.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/13
 * Time 上午9:08
 */

public class ShowApiBean <T> {
    @SerializedName("showapi_res_code")
    private int showApiResCode;

    @SerializedName("showapi_res_error")
    private String showApiResError;

    @SerializedName("showapi_res_body")
    private T showApiResBody;


    public int getShowApiResCode() {
        return showApiResCode;
    }

    public String getShowApiResError() {
        return showApiResError;
    }

    public T getShowApiResBody() {
        return showApiResBody;
    }

    public void setShowApiResCode(int showApiResCode) {
        this.showApiResCode = showApiResCode;
    }

    public void setShowApiResError(String showApiResError) {
        this.showApiResError = showApiResError;
    }

    public void setShowApiResBody(T showApiResBody) {
        this.showApiResBody = showApiResBody;
    }
}
