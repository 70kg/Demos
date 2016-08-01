package com.demos.mvvm.mvvmView;

/**
 * Created by wangpeng on 16/5/18.
 */
public interface MvvmLceView<M> extends MvvmView {
    void showLoading(boolean pullToRefresh);

    void setData(M data);

    void showContent();

    void showError(Throwable e, boolean pullToRefresh);

    void loadData(boolean pullToRefresh);

}
