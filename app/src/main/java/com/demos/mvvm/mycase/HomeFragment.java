package com.demos.mvvm.mycase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * Created by wangpeng on 16/5/18.
 */
public class HomeFragment extends MvvmLceFragment<List<LiveShow>, DetailView, DetailViewModel> implements DetailView {
    private RecyclerView recyclerView;
    private HomeAdapter adapter;

    @Override
    protected Class<DetailViewModel> getViewModelClass() {
        return DetailViewModel.class;
    }

    @Override
    public void setData(List<LiveShow> data) {
        adapter.setLiveShows(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        viewModel.loadData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.page_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (adapter == null) {
            adapter = new HomeAdapter(getContext());
        }
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(false);
    }

    @Override
    public void handleError(VolleyError volleyError) {
        TextView textView = (TextView) getView().findViewById(R.id.error_msg);
        textView.setText(volleyError.toString());
    }
}
