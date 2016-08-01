package com.demos.main.base;

import android.content.Context;

/**
 * Created by Mr_Wrong on 16/3/2.
 */
public abstract class StringAdapter extends BaseAdapter<String> {

    protected StringAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBind(MyViewHolder viewHolder, int Position) {
        viewHolder.setTextView(android.R.id.text1, get(Position));
    }

    @Override
    protected int getLayout() {
        return android.R.layout.simple_list_item_1;
    }

}
