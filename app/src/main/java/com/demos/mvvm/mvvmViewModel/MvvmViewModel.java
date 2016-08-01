package com.demos.mvvm.mvvmViewModel;

import android.os.Bundle;

import com.demos.mvvm.mvvmView.MvvmView;

/**
 * Created by wangpeng on 16/5/18.
 */
public interface MvvmViewModel<V extends MvvmView> {

    void onCreate(Bundle arguments, Bundle savedInstanceState);

    void saveState(Bundle bundle);

    void attachView(V view);

    void bindView(MvvmView view);

    void detachView(boolean retainInstance);

    void setViewModelId(String viewModelId);

    void onModelRemoved();
}
