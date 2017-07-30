package com.hangox.zuinews.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.hangox.databinding.context.BindingActivity;
import com.hangox.xlog.XLog;
import com.hangox.zuinews.R;
import com.hangox.zuinews.data.NewsData;
import com.hangox.zuinews.databinding.ActivityNewDetailBinding;
import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.entry.NewsEntity;

import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewDetailActivity extends BindingActivity<ActivityNewDetailBinding> {

    private NewsEntity mNewsEntity;
    private NewsData mNewsData;
    private float mContentWidth;
    private String mExpandedTitle;
    private String mCollapsedTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsData = new NewsData();
        mNewsEntity = getIntent().getParcelableExtra("news");
        mNewsEntity.__setDaoSession(Db.session());
        XLog.v(mNewsEntity);

        setSupportActionBar(mDataBinding.toolbar);
        mDataBinding.content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mDataBinding.content.getViewTreeObserver().removeOnPreDrawListener(this);
                mContentWidth = mDataBinding.content.getWidth();
                setUpData(mNewsEntity);
                return false;
            }
        });

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        mCollapsedTitle = " ";
        mExpandedTitle = mNewsEntity.getTitle();
        mDataBinding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int titleBarHeight = getStateBarHeight();
            if (Math.abs(verticalOffset) == (mDataBinding.appBar.getHeight() - mDataBinding.toolbar.getHeight() - Math.abs(titleBarHeight))) {
                mDataBinding.toolbar.setTitle(mNewsEntity.getTitle());
            }else{
                mDataBinding.toolbar.setTitle(" ");
            }
        });
    }

    private int getStateBarHeight() {
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentViewTop - statusBarHeight;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_new_detail;
    }

    public void setUpData(NewsEntity data) {
        Observable
                .create((ObservableOnSubscribe<Spanned>) e -> {
                    Spanned spanned = Html.fromHtml(data.getContentHtml(), source -> {

                        try {
                            if (source.equals(data.getImageUrl())) {
                                Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
                                return drawable;
                            }
                            BitmapDrawable drawable = (BitmapDrawable) GlideApp.with(NewDetailActivity.this)
                                    .load(source).into((int) mContentWidth, (int) mContentWidth).get();
                            Bitmap bitmap = drawable.getBitmap();
                            int height = (int) (mContentWidth / bitmap.getWidth() * bitmap.getHeight());
                            drawable.setBounds(0, 0, (int) mContentWidth, height);
                            return drawable;
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                            Drawable drawable = getResources().getDrawable(R.drawable.ic_img_loading);
                            int height = (int) (mContentWidth / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
                            drawable.setBounds(0, 0, (int) mContentWidth, height);
                            return drawable;
                        }
                    }, null);
                    e.onNext(spanned);
                    e.onComplete();
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(spannable -> mDataBinding.content.setText(spannable), Throwable::printStackTrace);

        Drawable colorBigPicture = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
        if (TextUtils.isEmpty(data.getImageUrl())) {
            mDataBinding.bigPicture.setImageDrawable(colorBigPicture);
        } else {
            GlideApp.with(this)
                    .load(data.getImageUrl())
                    .into(mDataBinding.bigPicture)
                    .onLoadFailed(colorBigPicture);
        }


        mDataBinding.title.setText(data.getTitle());
        mDataBinding.time.setText(data.getDate());
        mDataBinding.source.setText(data.getSource());
    }
}
