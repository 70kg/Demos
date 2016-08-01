package com.demos.mvvm.mycase;

import com.android.volley.VolleyError;
import com.demos.mvvm.mvvmView.MvvmLceView;
import com.demos.mvvm.mycase.model.LiveShow;

import java.util.List;

/**
 * Created by wangpeng on 16/5/18.
 */
public interface DetailView extends MvvmLceView<List<LiveShow>> {
    void handleError(VolleyError volleyError);
}
