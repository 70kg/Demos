package com.demos.mvvm.mycase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.demos.R;
import com.demos.mvvm.mvvmfragment.MvvmLceFragment;
import com.demos.mvvm.mycase.model.LiveShow;

import java.util.List;

/**
 * Created by wangpeng on 16/7/29.
 */
public class LiveFragment extends MvvmLceFragment<List<LiveShow>, DetailView, DetailViewModel> implements DetailView {
    @Override
    protected Class<DetailViewModel> getViewModelClass() {
        return DetailViewModel.class;
    }

    @Override
    public void setData(List<LiveShow> data) {
        TextView textView = (TextView) getView().findViewById(R.id.page_content);
        textView.setText("这是第二页");
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.live_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.loadData();
    }

    @Override
    public void handleError(VolleyError volleyError) {
        TextView textView = (TextView) getView().findViewById(R.id.error_msg);
        textView.setText(volleyError.toString());
    }
}
