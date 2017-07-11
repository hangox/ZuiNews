package com.hangox.zuinews.io.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/9
 * Time 上午11:07
 */

public class NetworksUtils {

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
}
