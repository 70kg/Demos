package com.demos.mvvm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.android.volley.VolleyLog;
import com.demos.mvvm.mvvmView.MvvmView;
import com.demos.mvvm.mvvmViewModel.MvvmViewModel;

import java.util.UUID;

/**
 * Created by wangpeng on 16/5/18.
 */
public class ViewModelHelper<V extends MvvmView, VM extends MvvmViewModel<V>> {
    private String mViewModelId;

    VM viewModel;

    private boolean mModelRemoved;

    private boolean mOnSaveInstanceCalled;

    Class<? extends MvvmViewModel<V>> mViewModelClass;

    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable Class<? extends MvvmViewModel<V>> viewModelClass,
                         @Nullable Bundle arguments) {

        if (viewModelClass == null)
            return;
        this.mViewModelClass = viewModelClass;

        if (mViewModelId == null) {
            if (savedInstanceState == null) {
                mViewModelId = UUID.randomUUID().toString();
            } else {
                mViewModelId = savedInstanceState.getString(getViewModelIdFieldName());
            }
        }

        ViewModelProvider.ViewModleWrapper<V> viewModleWrapper = ViewModelProvider.getInstacne().getViewModelWrapper(mViewModelId, viewModelClass);
        viewModel = (VM) viewModleWrapper.viewModel;

        mOnSaveInstanceCalled = false;
        if (viewModleWrapper.wasCreated) {
            viewModel.onCreate(arguments, savedInstanceState);
        }
    }

    public VM getViewModel() {
        return viewModel;
    }


    private String getViewModelIdFieldName() {
        return "__vm_id_" + mViewModelClass.getName();
    }

    public void attachView(V view) {
        if (viewModel == null) return;
        viewModel.attachView(view);
    }

    public void onDestroyView(Fragment fragment) {
        if (viewModel == null) {
            return;
        }
        viewModel.detachView(true);
        if (fragment.getActivity() != null && fragment.getActivity().isFinishing()) {
            removeViewModel();
        }

    }

    private void removeViewModel() {
        if (!mModelRemoved) {
            ViewModelProvider.getInstacne().removeViewModel(mViewModelId);
            viewModel.onModelRemoved();
            mModelRemoved = true;
            viewModel = null;
        }
    }

    public void bindView(MvvmView view) {
        if (viewModel == null)
            return;
        viewModel.bindView(view);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(getViewModelIdFieldName(), mViewModelId);
        if (viewModel != null && !mOnSaveInstanceCalled) {
            viewModel.saveState(bundle);
            mOnSaveInstanceCalled = true;
        }
    }

    public void onDestroy(Fragment fragemt) {
        if (viewModel == null)
            return;
        if (fragemt.getActivity().isFinishing()) {
            removeViewModel();
        } else if (fragemt.isRemoving() && !mOnSaveInstanceCalled) {
            removeViewModel();
        }
    }
}
