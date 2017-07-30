package com.hangox.zuinews.data;

import android.content.Context;

import org.greenrobot.essentials.io.IoUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created With Android Studio
 * User HangoX
 * Date 2017/7/30
 * Time 下午10:53
 */

public class AssertUtils {
    public static String getStringFromAssert(Context context,String fileName) throws IOException {
        InputStream stream = context.getResources().getAssets().open(fileName);
        return new String(IoUtils.readAllBytes(stream));
    }
}
