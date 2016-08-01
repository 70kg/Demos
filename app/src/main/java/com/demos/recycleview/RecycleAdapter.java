package com.demos.recycleview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demos.R;
import com.demos.main.base.StringAdapter;

import java.util.Arrays;

/**
 * Created by wangpeng on 16/4/22.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;

    protected RecycleAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new myViewHolder(LayoutInflater.from(mContext).inflate(R.layout.in_recycle, parent, false));
        return new emptyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.empty, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0)
            return 0;
        return 1;
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class emptyViewHolder extends RecyclerView.ViewHolder {

        public emptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        StringAdapter adapter;

        public myViewHolder(View itemView) {
            super(itemView);
            RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.inn_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            adapter = new StringAdapter(mContext) {
                @Override
                protected void onItemClick(View v, int position) {

                }
            };
            recyclerView.setAdapter(adapter);
            adapter.addAll(Arrays.asList("hh", "11", "22", "33", "44", "hh", "11", "22", "33", "44", "hh", "11", "22", "33", "44"));

        }
    }
}
