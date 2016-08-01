package com.demos.mvvm.mvvmfragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.demos.mvvm.mvvmView.MvvmView;
import com.demos.mvvm.mvvmViewModel.MvvmViewModel;
import com.demos.mvvm.ViewModelHelper;

/**
 * Created by wangpeng on 16/5/18.
 */
public abstract class MvvmFragment<V extends MvvmView, VM extends MvvmViewModel<V>> extends Fragment implements MvvmView {

    private final ViewModelHelper<V, VM> mViewModeHelper = new ViewModelHelper<>();

    protected VM viewModel;

    protected abstract Class<VM> getViewModelClass();

    public VM getViewModel() {
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModeHelper.onCreate(savedInstanceState, getViewModelClass(), getArguments());
        viewModel = mViewModeHelper.getViewModel();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModeHelper.attachView((V) this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModeHelper.onDestroyView(this);
    }

    protected void bindView(MvvmView view) {
        mViewModeHelper.bindView(view);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewModeHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModeHelper.onDestroy(this);
        viewModel = null;
    }
}
