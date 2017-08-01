package com.hangox.zuinews.ui;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hangox.databinding.context.BindingActivity;
import com.hangox.databinding.recycleview.BindingRecyclerAdapter;
import com.hangox.databinding.recycleview.BindingViewHolder;
import com.hangox.zuinews.R;
import com.hangox.zuinews.databinding.ActivityCommentListBinding;
import com.hangox.zuinews.databinding.ItemCommentBinding;
import com.hangox.zuinews.io.bean.CommentBean;

import java.util.ArrayList;

/**
 * @author hangox
 * 评论列表
 */
public class CommentListActivity extends BindingActivity<ActivityCommentListBinding> {


    ArrayList<CommentBean> mComments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComments = getIntent().getParcelableArrayListExtra("comments");

        RecyclerView commentList = mDataBinding.commentList;
        commentList.setLayoutManager(new LinearLayoutManager(getApplication(),LinearLayoutManager.VERTICAL,false));
        commentList.setAdapter(new CommentAdapter());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.ic_divide_line));
        commentList.addItemDecoration(itemDecoration);

    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_comment_list;
    }

    class CommentAdapter extends BindingRecyclerAdapter<BindingViewHolder<ItemCommentBinding>>{

        @Override
        public BindingViewHolder<ItemCommentBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingViewHolder<>(R.layout.item_comment, parent);
        }

        @Override
        public void onBindViewHolder(BindingViewHolder<ItemCommentBinding> holder, int position) {
            holder.mBinding.setComment(mComments.get(position));
            holder.mBinding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }
    }
}
