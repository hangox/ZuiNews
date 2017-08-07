package com.hangox.zuinews.io.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.hangox.zuinews.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/9
 * Time 上午11:07
 */

public class NetworksUtils {

    public static final ArrayMap<Class,Integer> ERROR_TEXT = new ArrayMap<>();
    static {
        ERROR_TEXT.put(NetworkError.class, R.string.net_error_network);
        ERROR_TEXT.put(NoConnectionError.class, R.string.net_error_no_connection);
        ERROR_TEXT.put(ServerError.class, R.string.net_error_server);
        ERROR_TEXT.put(TimeoutError.class, R.string.net_error_timeout);
    }

    public static String buildParameter(Map<String, String> parameter,String encode) throws UnsupportedEncodingException {
        Iterator<Map.Entry<String, String>> iterator = parameter.entrySet().iterator();
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String> keyValue = iterator.next();
            builder.append(keyValue.getKey())
                    .append("=")
                    .append(URLEncoder.encode(keyValue.getValue(), encode))
                    .append("&");
        }
        if(builder.length() != 0){
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * 网络是否可以用
     * @param context
     * @return
     */
    public static boolean isNetworkUp(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
//        return false;
    }


    /**
     * 创建一个响应
     * @param context
     * @return
     */
    public static Consumer<? super Throwable> createOnVolleyNetworkHandler(Context context){
        return throwable -> {
            if(throwable instanceof VolleyError) {
                Toast.makeText(context, getNetworkErrorDescriptionId(throwable), Toast.LENGTH_LONG).show();
            }
        };
    }

    private static Integer getNetworkErrorDescriptionId(Throwable throwable) {
        Integer textId = ERROR_TEXT.get(throwable.getClass());
        if(textId == null){
            textId = R.string.net_error_un_know;
        }
        return textId;
    }
}
