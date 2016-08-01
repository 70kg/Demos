package com.demos.mvvm.mvvmfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.demos.R;
import com.demos.mvvm.mvvmView.MvvmLceView;
import com.demos.mvvm.mvvmView.MvvmView;
import com.demos.mvvm.mvvmViewModel.MvvmViewModel;

/**
 * Created by wangpeng on 16/5/18.
 */
public abstract class MvvmLceFragment<M, V extends MvvmView, VM extends MvvmViewModel<V>>
        extends MvvmFragment<V, VM> implements MvvmLceView<M> {

    protected View loadingView;

    protected View contentView;

    protected View errorView;

    protected TextView errorMsg;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentView = view.findViewById(R.id.page_content);
        loadingView = view.findViewById(R.id.loading_indicator);
        errorView = view.findViewById(R.id.error_indicator);
        errorMsg = (TextView) view.findViewById(R.id.error_msg);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {
        if (contentView.getVisibility() == View.VISIBLE) {
            errorView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
        } else {
            loadingView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
            contentView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }


}
