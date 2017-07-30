package com.hangox.zuinews.ui;

import android.os.Bundle;

import com.hangox.databinding.context.BindingActivity;
import com.hangox.zuinews.R;
import com.hangox.zuinews.databinding.ActivityCommentListBinding;

public class CommentListActivity extends BindingActivity<ActivityCommentListBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_comment_list;
    }
}
