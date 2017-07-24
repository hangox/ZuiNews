package com.hangox.zuinews.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hangox.databinding.recycleview.BindingViewHolder;
import com.hangox.more.recycerview.MoreDelegate;
import com.hangox.more.recycerview.MoreDelegateImp;
import com.hangox.zuinews.R;
import com.hangox.zuinews.data.NewsData;
import com.hangox.zuinews.databinding.FraNewListBinding;
import com.hangox.zuinews.databinding.ItemNewsBinding;
import com.hangox.zuinews.error.ShowApiError;
import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.bean.NewsApiBean;
import com.hangox.zuinews.io.entry.NewsEntity;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
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


    @DebugLog
    public static NewsListFragment newInstance(String channelName,String channelId) {
        Bundle bundle = new Bundle();
        bundle.putString("channelId", channelId);
        bundle.putString("channelName",channelName);
        Timber.v("set channelId %s ,channelName %s ",channelId,channelName);
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
        mChannelName = args.getString("channelName","");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setupView
        mBinding.recyclerView.setAdapter(new NewsAdapter());
        mBinding.recyclerView.setMoreListener(recyclerView -> {
            recyclerView.lockMoreCall();
            requestNextPage();
        });
        mBinding.recyclerView.setMoreDelegate(new MoreDelegateImp(getContext()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        mBinding.recyclerView.addItemDecoration(itemDecoration);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this::requestFirstPage);

//        mNewsData.getNewsList(mChannelId, PAGE_SIZE)
//                .subscribe(newsEntities -> {
//                    if (!newsEntities.isEmpty()) {
//                        mNewsEntities.addAll(newsEntities);
//                        mBinding.recyclerView.getAdapter().notifyDataSetChanged();
//                        mBinding.recyclerView.setVisibility(View.VISIBLE);
//                        mBinding.waiting.setVisibility(View.GONE);
//                    }
//                    requestFirstPage();
//                }, Throwable::printStackTrace);

        requestFirstPage();
    }

    private void requestFirstPage() {
        mNewsData.requestNewsList(mChannelId, 1, this)
                .doOnNext(createUpdatePageInfo())
                .observeOn(Schedulers.io())
                .map(pagebeanBean -> {
                    Timber.d("request channelId %s,response %s",mChannelId,pagebeanBean);
                    List<NewsApiBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list =
                            pagebeanBean.getContentlist();
                    List<NewsEntity> entities = new ArrayList<>();
                    for (NewsApiBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean bean : list) {
                        NewsEntity entity = new NewsEntity(bean);
                        Db.session().insertOrReplace(entity);
                        entities.add(entity);
                    }
                    return entities;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsEntities -> {
                    mNewsEntities.clear();
                    mNewsEntities.addAll(newsEntities);
                    if (mBinding.swipeRefreshLayout.isRefreshing()) {
                        mBinding.swipeRefreshLayout.setRefreshing(false);
                    }
                    mBinding.recyclerView.getAdapter().notifyDataSetChanged();
                    mBinding.waiting.setVisibility(View.GONE);
                    mBinding.swipeRefreshLayout.setVisibility(View.VISIBLE);
                }, throwable -> {
                    if(throwable instanceof ShowApiError){
                        ShowApiError showApiError = (ShowApiError) throwable;
                        Snackbar.make(mBinding.constraintLayout,showApiError.getMessage(), Snackbar.LENGTH_LONG)
                                .show();
                    }
                }, () -> {

                });
    }

    @NonNull
    private Consumer<NewsApiBean.ShowapiResBodyBean.PagebeanBean> createUpdatePageInfo() {
        return pagebeanBean -> {
            mCurrentPage = pagebeanBean.getCurrentPage();
            setIsLastPage(pagebeanBean.getCurrentPage() == pagebeanBean.getAllNum());
        };
    }

    private void requestNextPage() {

        mNewsData.requestNewsList(mChannelId, mCurrentPage + 1,this)
                .doOnNext(createUpdatePageInfo())
                .doOnNext(pagebeanBean -> mCurrentPage = pagebeanBean.getCurrentPage())
                .observeOn(Schedulers.io())
                .map(pagebeanBean -> {
                    List<NewsApiBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list =
                            pagebeanBean.getContentlist();
                    List<NewsEntity> entities = new ArrayList<>();
                    for (NewsApiBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean bean : list) {
                        NewsEntity entity = new NewsEntity(bean);
                        Db.session().insertOrReplace(entity);
                        entities.add(entity);
                    }
                    if(!entities.isEmpty()){
                        entities.remove(0);
                    }
                    return entities;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsEntities -> {
                    int lastPosition = mNewsEntities.size();
                    mNewsEntities.addAll(newsEntities);
                    mBinding.recyclerView.getAdapter().notifyItemInserted(lastPosition);
                    mBinding.recyclerView.unlockMoreCall();
                },throwable -> {
                    mBinding.recyclerView.getMoreDelegete().setViewState(MoreDelegate.ViewState.ERROR);
                    mBinding.recyclerView.unlockMoreCall();
                },() -> {

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


    class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewsViewHolder(R.layout.item_news, parent);
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
    }
}
