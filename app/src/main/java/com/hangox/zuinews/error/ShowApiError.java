package com.hangox.zuinews.error;

import com.hangox.zuinews.io.bean.ShowApiBean;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/17
 * Time 下午10:25
 */

public class ShowApiError extends RuntimeException {
    private int mCode;

    public ShowApiError(String message, int code) {
        super(message);
        mCode = code;
    }

    public ShowApiError(ShowApiBean showApi) {
        this(showApi.getShowApiResError(),showApi.getShowApiResCode());
    }


    public ShowApiError(String message) {
        super(message);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShowApiError{");
        sb.append("mCode=").append(mCode);
        sb.append('}');
        return sb.toString() + super.toString();
    }

    public int getCode() {
        return mCode;
    }
}
