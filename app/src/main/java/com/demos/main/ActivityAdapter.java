package com.demos.main;

import android.app.Activity;
import android.content.Context;

import com.demos.AllActivitys;
import com.demos.R;
import com.demos.main.base.BaseAdapter;
import com.demos.main.base.MyViewHolder;

/**
 * Created by Mr_Wrong on 16/2/2.
 */
public abstract class ActivityAdapter extends BaseAdapter<Activity> {
    protected ActivityAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBind(MyViewHolder viewHolder, int Position) {
        Activity activity = get(Position);
        int start = activity.getClass().getName().lastIndexOf(".") + 1;
        String name = activity.getClass().getName().substring(start);
        viewHolder.setTextView(R.id.tv_item, name);
        viewHolder.getConvertView().setTag(AllActivitys.values()[Position]);
    }

    @Override
    protected int getLayout() {
        return R.layout.item;
    }
}
