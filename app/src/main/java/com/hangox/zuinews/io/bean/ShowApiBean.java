package com.hangox.zuinews.io.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/13
 * Time 上午9:08
 */

public class ShowApiBean <T extends ShowApiBody> {
    @SerializedName("showapi_res_code")
    protected int showApiResCode;

    @SerializedName("showapi_res_error")
    protected String showApiResError;

    @SerializedName("showapi_res_body")
    protected T showApiResBody;


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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShowApiBean{");
        sb.append("showApiResCode=").append(showApiResCode);
        sb.append(", showApiResError='").append(showApiResError).append('\'');
        sb.append(", showApiResBody=").append(showApiResBody);
        sb.append('}');
        return sb.toString();
    }
}
