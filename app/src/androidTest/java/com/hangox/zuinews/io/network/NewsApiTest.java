package com.hangox.zuinews.io.network;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hangox.xlog.XLog;
import com.hangox.zuinews.io.bean.ChannelApiBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static org.junit.Assert.assertEquals;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/8
 * Time 下午5:04
 */
@RunWith(AndroidJUnit4.class)
public class NewsApiTest {

    private RequestQueue mQueue;

    @Test
    public void requestNewsChannel() throws Exception {
        NewsApi.requestNewsChannel(null).subscribe(new Observer<ChannelApiBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ChannelApiBean channelApiBean) {
                XLog.v(channelApiBean);
                assertEquals("1","2");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                XLog.e(e);
                assertEquals("1","2");
            }

            @Override
            public void onComplete() {
                assertEquals("1","2");
            }
        });
    }

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        mQueue = Volley.newRequestQueue(appContext);
    }

}