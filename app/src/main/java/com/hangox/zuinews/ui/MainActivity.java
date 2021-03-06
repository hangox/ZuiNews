package com.hangox.zuinews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;

import com.hangox.zuinews.R;
import com.hangox.zuinews.data.NewsData;
import com.hangox.zuinews.databinding.ActivityMainBinding;
import com.hangox.zuinews.io.entry.ChannelEntity;
import com.hangox.zuinews.io.network.NetworksUtils;
import com.tencent.bugly.beta.Beta;

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
    private NewsListAdapter mAdapter;
    private ArrayMap<Integer,NewsListFragment> mFragmentArrayMap = new ArrayMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsData = new NewsData();
        mNewsData.getChannelList(this)
                .subscribe(this::setUpChannel, Throwable::printStackTrace);
        if (NetworksUtils.isNetworkUp(this)) {
            mNewsData.requestUpdateChannel();
        } else {
            Snackbar.make(mDataBinding.constraintLayout, R.string.no_connection_show_cache,Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, v -> {})
                    .show();
        }
        setSupportActionBar(mDataBinding.toolbar);

    }

    private void setUpChannel(@NonNull List<ChannelEntity> channelEntities) {
        mTitles = new ArrayList<>(channelEntities.size());
        for (ChannelEntity channelEntity : channelEntities) {
            mTitles.add(channelEntity.getName());
        }
        mChannelEntities = channelEntities;
        mDataBinding.viewPager.setAdapter(mAdapter = new NewsListAdapter(getSupportFragmentManager()));
        mDataBinding.tabs.setupWithViewPager(mDataBinding.viewPager);
        mDataBinding.viewPager.setOffscreenPageLimit(mAdapter.getCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.checkUpdate){
            checkUpdate();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkUpdate() {
        Beta.checkUpgrade(true,false);
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
            if(!mFragmentArrayMap.containsKey(position)){
                ChannelEntity entity = mChannelEntities.get(position);
                mFragmentArrayMap.put(position,NewsListFragment.newInstance(entity.getName(), entity.getChannelId()));
            }
            return mFragmentArrayMap.get(position);
        }

        @Override
        public int getCount() {
            return mChannelEntities.size();
        }
    }

}
