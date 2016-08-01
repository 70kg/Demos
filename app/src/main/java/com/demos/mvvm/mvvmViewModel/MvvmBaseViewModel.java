package com.demos.mvvm.mvvmViewModel;

import android.os.Bundle;

import com.demos.mvvm.mvvmView.MvvmView;

import java.lang.ref.WeakReference;

/**
 * Created by wangpeng on 16/5/18.
 */
public class MvvmBaseViewModel<V extends MvvmView> implements MvvmViewModel<V> {

    private String mViewModelId;

    private WeakReference<V> viewRef;

    @Override
    public void onCreate(Bundle arguments, Bundle savedInstanceState) {

    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }


    @Override
    public void bindView(MvvmView view) {

    }

    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public void setViewModelId(String viewModelId) {

    }

    @Override
    public void onModelRemoved() {

    }
}
