package com.hangox.zuinews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.hangox.databinding.recycleview.BindingRecyclerAdapter;
import com.hangox.databinding.recycleview.BindingViewHolder;
import com.hangox.more.recycerview.MoreDelegate;
import com.hangox.more.recycerview.MoreDelegateImp;
import com.hangox.zuinews.R;
import com.hangox.zuinews.data.NewsData;
import com.hangox.zuinews.databinding.FraNewListBinding;
import com.hangox.zuinews.databinding.ItemNewsBinding;
import com.hangox.zuinews.error.ShowApiError;
import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.bean.NewsContentBean;
import com.hangox.zuinews.io.bean.NewsPageBean;
import com.hangox.zuinews.io.entry.NewsEntity;
import com.hangox.zuinews.io.entry.NewsEntityDao;
import com.hangox.zuinews.io.network.NetworksUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/10
 * Time 下午9:26
 */

public class NewsListFragment extends MyFragment<FraNewListBinding> {

    private static final int PAGE_SIZE = 20;

    private String mChannelId;
    //没有实际作用，调试用的
    private String mChannelName;

    private List<NewsEntity> mNewsEntities = new ArrayList<>();

    private NewsData mNewsData;

    private int mCurrentPage = 1;
    private boolean mIsLastPage;
    private boolean isNetworkUp;
    private NewsAdapter mAdapter;


    @DebugLog
    public static NewsListFragment newInstance(String channelName, String channelId) {
        Bundle bundle = new Bundle();
        bundle.putString("channelId", channelId);
        bundle.putString("channelName", channelName);
        Timber.v("set channelId %s ,channelName %s ", channelId, channelName);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsData = new NewsData();

        Bundle args = getArguments();
        mChannelId = args.getString("channelId", "");
        mChannelName = args.getString("channelName", "");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setupView
        mBinding.recyclerView.setAdapter(mAdapter = new NewsAdapter());
        mBinding.recyclerView.setMoreListener(recyclerView -> {
            recyclerView.lockMoreCall();
            requestNextPage();
        });


        mBinding.recyclerView.setMoreDelegate(new MoreDelegateImp(getContext()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.ic_divide_line));
        mBinding.recyclerView.addItemDecoration(itemDecoration);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        mBinding.swipeRefreshLayout.setOnRefreshListener(this::requestFirstPage);
        mBinding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        isNetworkUp = NetworksUtils.isNetworkUp(getContext());
        requestFirstPage();
    }

    private void requestFirstPage() {
        Observable<List<NewsEntity>> observable = null;
        if (isNetworkUp) {
            observable = mNewsData.requestNewsList(mChannelId, 1, this)
                    .doOnNext(createUpdatePageInfo())
                    .observeOn(Schedulers.io())
                    .map(pagebeanBean -> {
                        Timber.d("request channelId %s,response %s", mChannelId, pagebeanBean);
                        List<NewsContentBean> list =
                                pagebeanBean.getContentlist();
                        List<NewsEntity> entities = new ArrayList<>();
                        for (NewsContentBean bean : list) {
                            NewsEntity entity = new NewsEntity(bean);
                            Db.session().insertOrReplace(entity);
                            entities.add(entity);
                        }
                        return entities;
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = Db.session()
                    .getNewsEntityDao()
                    .queryBuilder()
                    .where(NewsEntityDao.Properties.ChannelId.eq(mChannelId))
                    .orderDesc(NewsEntityDao.Properties.Date)
                    .rx()
                    .list()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(newsEntities -> setIsLastPage(newsEntities.isEmpty()));
        }

        observable.subscribe(newsEntities -> {
            mNewsEntities.clear();
            mNewsEntities.addAll(newsEntities);
            if (mBinding.swipeRefreshLayout.isRefreshing()) {
                mBinding.swipeRefreshLayout.setRefreshing(false);
            }
            mBinding.waiting.setVisibility(View.GONE);
            mBinding.swipeRefreshLayout.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }, throwable -> {
            if (throwable instanceof ShowApiError) {
                ShowApiError showApiError = (ShowApiError) throwable;
                Snackbar.make(mBinding.constraintLayout, showApiError.getMessage(), Snackbar.LENGTH_LONG)
                        .show();
            }
        }, () -> {

        });
    }

    @NonNull
    private Consumer<NewsPageBean> createUpdatePageInfo() {
        return pagebeanBean -> {
            mCurrentPage = pagebeanBean.getCurrentPage();
            setIsLastPage(pagebeanBean.getCurrentPage() == pagebeanBean.getAllPages());
        };
    }

    private void requestNextPage() {

        Observable<List<NewsEntity>> observable = null;
        if (isNetworkUp) {
            observable = mNewsData.requestNewsList(mChannelId, mCurrentPage + 1, this)
                    .doOnNext(createUpdatePageInfo())
                    .doOnNext(pagebeanBean -> mCurrentPage = pagebeanBean.getCurrentPage())
                    .observeOn(Schedulers.io())
                    .map(pagebeanBean -> {
                        List<NewsContentBean> list =
                                pagebeanBean.getContentlist();
                        List<NewsEntity> entities = new ArrayList<>();
                        for (NewsContentBean bean : list) {
                            NewsEntity entity = new NewsEntity(bean);
                            Db.session().insertOrReplace(entity);
                            entities.add(entity);
                        }
                        if (!entities.isEmpty()) {
                            entities.remove(0);
                        }
                        return entities;
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            QueryBuilder<NewsEntity> queryBuilder = Db.session().getNewsEntityDao().queryBuilder()
                    .where(NewsEntityDao.Properties.ChannelId.eq(mChannelId))
                    .orderDesc(NewsEntityDao.Properties.Date);
            if (!mNewsEntities.isEmpty()) {
                queryBuilder.where(NewsEntityDao.Properties.ChannelId.lt(mNewsEntities.get(mNewsEntities.size() - 1).getDate()));
            }
            observable = queryBuilder.limit(PAGE_SIZE)
                    .rx()
                    .list()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(newsEntities -> setIsLastPage(newsEntities.isEmpty()));

        }


        observable.doOnSubscribe(disposable -> {
            mBinding.recyclerView.getMoreDelegete().setViewState(MoreDelegate.ViewState.LOADING);
        }).subscribe(newsEntities -> {
            int lastPosition = mNewsEntities.size();
            if (!newsEntities.isEmpty()) {
                mNewsEntities.addAll(newsEntities);
                mAdapter.notifyItemInserted(lastPosition);
            }
            if (!mIsLastPage) {
                mBinding.recyclerView.unlockMoreCall();
            }
        }, throwable -> {
            mBinding.recyclerView.getMoreDelegete().setViewState(MoreDelegate.ViewState.ERROR);
            mBinding.recyclerView.unlockMoreCall();
        }, () -> {
        });

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NewsListFragment{");
        sb.append("mChannelId='").append(mChannelId).append('\'');
        sb.append(", mChannelName='").append(mChannelName).append('\'');
        sb.append(", mCurrentPage=").append(mCurrentPage);
        sb.append('}');
        return sb.toString();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fra_new_list;
    }

    public void setIsLastPage(boolean isLastPage) {
        mIsLastPage = isLastPage;
        MoreDelegate.ViewState viewState = MoreDelegate.ViewState.LOADING;
        if (isLastPage) {
            viewState = MoreDelegate.ViewState.LAST_PAGE;
            mBinding.recyclerView.lockMoreCall();
        }
        mBinding.recyclerView.getMoreDelegete().setViewState(viewState);
    }


    public static class NewsViewHolder extends BindingViewHolder<ItemNewsBinding> {

        public NewsViewHolder(@LayoutRes int layoutId, ViewGroup parent) {
            super(layoutId, parent);
        }
    }


    class NewsAdapter extends BindingRecyclerAdapter<NewsViewHolder> {

        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NewsViewHolder viewHolder = new NewsViewHolder(R.layout.item_news, parent);
            viewHolder.setOnViewHolderClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(NewsViewHolder holder, int position) {
            holder.mBinding.setNews(mNewsEntities.get(position));
            holder.mBinding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mNewsEntities.size();
        }

        @Override
        public void onRootViewClick(View rootView, int index) {
            Intent intent = new Intent(getContext(),NewDetailActivity.class);
            intent.putExtra("news",mNewsEntities.get(index));
            startActivity(intent);
        }
    }
}
