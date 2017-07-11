package com.hangox.zuinews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hangox.zuinews.R;
import com.hangox.zuinews.databinding.ActivityMainBinding;
import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.entry.ChannelEntity;

/**
 * @author hangox
 */
public class MainActivity extends MyActivity <ActivityMainBinding>{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Db.session().queryBuilder(ChannelEntity.class).rx().list();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isShowNavigationUp() {
        return false;
    }


    class NewsListAdapter extends FragmentPagerAdapter{

        public NewsListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }


}
