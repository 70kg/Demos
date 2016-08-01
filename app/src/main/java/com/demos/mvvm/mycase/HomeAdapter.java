package com.demos.mvvm.mycase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demos.R;
import com.demos.mvvm.mycase.model.LiveShow;

import java.util.List;

/**
 * Created by wangpeng on 16/7/29.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    public List<LiveShow> liveShows;
    private Context mContext;

    public void setLiveShows(List<LiveShow> liveShows) {
        this.liveShows = liveShows;
    }

    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tweet_item, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding(liveShows.get(position));
    }

    @Override
    public int getItemCount() {
        return liveShows != null ? liveShows.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }

        public void binding(LiveShow liveShow) {
            name.setText(liveShow.name);
        }
    }
}
