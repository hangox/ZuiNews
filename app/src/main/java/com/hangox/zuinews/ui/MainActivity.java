package com.hangox.zuinews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hangox.zuinews.R;
import com.hangox.zuinews.data.NewsData;
import com.hangox.zuinews.databinding.ActivityMainBinding;
import com.hangox.zuinews.io.entry.ChannelEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * @author hangox
 */
public class MainActivity extends MyActivity<ActivityMainBinding> {

    List<String> mTitles;

    List<ChannelEntity> mChannelEntities;

    NewsData mNewsData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsData = new NewsData();
        mNewsData.getChannelList(this)
                .subscribe(this::setUpChannel,Throwable::printStackTrace);
        mNewsData.requestUpdateChannel();

    }

    private void setUpChannel(@NonNull List<ChannelEntity> channelEntities) {
        mTitles = new ArrayList<>(channelEntities.size());
        for (ChannelEntity channelEntity : channelEntities) {
            mTitles.add(channelEntity.getName());
        }
        mChannelEntities = channelEntities;
        mDataBinding.viewPager.setAdapter(new NewsListAdapter(getSupportFragmentManager()));
        mDataBinding.tabs.setupWithViewPager(mDataBinding.viewPager);
    }


    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isShowNavigationUp() {
        return false;
    }


    class NewsListAdapter extends FragmentPagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        public NewsListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ChannelEntity entity = mChannelEntities.get(position);
            return NewsListFragment.newInstance(entity.getName(),entity.getChannelId());
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }
    }

}
