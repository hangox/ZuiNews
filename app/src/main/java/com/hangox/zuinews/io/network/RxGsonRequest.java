package com.hangox.zuinews.io.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/8
 * Time 下午5:47
 */

public class RxGsonRequest<T> extends Request<T> {

    private Subject<T> mSubject = PublishSubject.create();

    private Gson mGson = new Gson();
    private Class<T> mClazz;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;


    public static class Builder<T> {
        private Class<T> mClass;
        private Map<String, String> mHeader;
        private Map<String, String> mParameter;
        private String mUrl;
        private int mMethod;
        private Gson mGson;
        private Object mTag;


        public Builder<T> setClass(Class<T> aClass) {
            mClass = aClass;
            return this;
        }

        public Builder<T> setHeader(Map<String, String> header) {
            mHeader = header;
            return this;
        }

        public Builder<T> setParameter(Map<String, String> params) {
            mParameter = params;
            return this;
        }

        public Builder<T> setUrl(String url) {
            mUrl = url;
            return this;
        }

        public Builder<T> setMethod(int method) {
            mMethod = method;
            return this;
        }

        public Builder<T> setGson(Gson gson) {
            mGson = gson;
            return this;
        }

        public Builder<T> setTag(Object tag) {
            mTag = tag;
            return this;
        }

        public RxGsonRequest<T> build() {
            if (mMethod == Method.GET && mParameter != null && !mParameter.isEmpty()) {
                try {
                    mUrl = mUrl + "?" + NetworksUtils.buildParameter(mParameter, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            RxGsonRequest<T> request = new RxGsonRequest<T>(mMethod, mUrl, mClass);
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

    private RxGsonRequest(int method, String url, Class<T> clazz) {
        super(method, url, null);
        mClazz = clazz;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if(mHeaders !=null && !mHeaders.isEmpty()){
            mHeaders.putAll(super.getHeaders());
            return mHeaders;
        }
        return super.getHeaders();
    }

    @DebugLog
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if(mParams != null && !mParams.isEmpty()){
            mParams.putAll(super.getParams());
            return mParams;
        }
        return super.getParams();
    }

    @DebugLog
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    public void deliverError(VolleyError error) {
        mSubject.onError(error);
    }

    @Override
    protected void deliverResponse(T response) {
        mSubject.onNext(response);
        mSubject.onComplete();
    }

    @DebugLog
    public RxGsonRequest<T> bindToQueue(RequestQueue queue){
        queue.add(this);
        return this;
    }



    public Observable<T> rx() {
        return mSubject;
    }
}
