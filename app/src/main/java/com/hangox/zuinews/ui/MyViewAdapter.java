package com.hangox.zuinews.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hangox.zuinews.R;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/22
 * Time 下午9:00
 */

public class MyViewAdapter {

    @BindingAdapter(value = {"glideUrl", "glideError"},requireAll = false)
    public static void setGlideUrl(ImageView view, String imageUrl, Drawable errorDrawable) {
        if(errorDrawable == null){
            errorDrawable  = view.getContext().getResources().getDrawable(R.drawable.ic_load_failure);
        }
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view)
                .onLoadFailed(errorDrawable);
    }
}
