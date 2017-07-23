package com.hangox.zuinews.io.network;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/21
 * Time 上午8:27
 */

public class RxShowApiRequest<T> extends RxGsonRequest<T> {
    protected RxShowApiRequest(int method, String url, Class<T> clazz) {
        super(method, url, clazz);

    }



    public static class Builder<T> extends RxGsonRequest.Builder<T> {
        private Class<T> mClass;
        private Map<String, String> mHeader;
        private Map<String, String> mParameter;
        private String mUrl;
        private int mMethod;
        private Gson mGson;
        private Object mTag;


        public RxShowApiRequest.Builder<T> setClass(Class<T> aClass) {
            mClass = aClass;
            return this;
        }

        public RxShowApiRequest.Builder<T> setHeader(Map<String, String> header) {
            mHeader = header;
            return this;
        }

        public RxShowApiRequest.Builder<T> setParameter(Map<String, String> params) {
            mParameter = params;
            return this;
        }

        public RxShowApiRequest.Builder<T> setUrl(String url) {
            mUrl = url;
            return this;
        }

        public RxShowApiRequest.Builder<T> setMethod(int method) {
            mMethod = method;
            return this;
        }

        public RxShowApiRequest.Builder<T> setGson(Gson gson) {
            mGson = gson;
            return this;
        }

        public RxShowApiRequest.Builder<T> setTag(Object tag) {
            mTag = tag;
            return this;
        }

        public RxShowApiRequest<T> build() {
            if (mMethod == Method.GET && mParameter != null && !mParameter.isEmpty()) {
                try {
                    mUrl = mUrl + "?" + NetworksUtils.buildParameter(mParameter, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            RxShowApiRequest<T> request = new RxShowApiRequest<>(mMethod, mUrl, mClass);
            if (mGson == null) {
                request.mGson = new Gson();
            }else{
                request.mGson = mGson;
            }
            request.mParams = mParameter;
            request.mHeaders = mHeader;
            request.setTag(mTag);
            return request;
        }
    }


    @Override
    protected void deliverResponse(T response) {
        super.deliverResponse(response);
    }
}
