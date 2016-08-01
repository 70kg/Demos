package com.demos.mvvm.mvvmViewModel;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.demos.mvvm.mvvmView.MvvmView;

/**
 * Created by wangpeng on 16/5/18.
 */
public abstract class MvvmBindingViewModel<M, V extends MvvmView> extends MvvmBaseViewModel<V>
        implements Response.Listener<M>, Response.ErrorListener {

    @Override
    public void onResponse(M m) {

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}
