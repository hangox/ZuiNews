package com.hangox.zuinews.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.hangox.zuinews.R;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/22
 * Time 下午9:00
 */

public class MyViewAdapter {

    @BindingAdapter(
            value = {"glideUrl", "glideError", "glidePlaceholder"}
            , requireAll = false
    )
    public static void setGlideUrl(ImageView view, String imageUrl, Drawable errorDrawable, Drawable placeHolder) {
        if (errorDrawable == null) {
            errorDrawable = view.getContext().getResources().getDrawable(R.drawable.ic_loading_failure);
        }

        if (placeHolder == null) {
            placeHolder = view.getContext().getResources().getDrawable(R.drawable.ic_img_loading);
        }

        GlideApp.with(view.getContext())
                .load(imageUrl)
                .placeholder(placeHolder)
                .error(errorDrawable)
                .fallback(R.drawable.ic_fallback)
                .into(view);
    }
}
