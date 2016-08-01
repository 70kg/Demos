package com.demos.main.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demos.R;

import butterknife.Bind;

/**
 * Created by Mr_Wrong on 16/3/7.
 */
public abstract class ListActivity extends ToolBarActivity {
    @Bind(R.id.recycleview)
    RecyclerView mRecyclerView;
    StringAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StringAdapter(this) {
            @Override
            protected void onItemClick(View v, int position) {

            }
        };
        mRecyclerView.setAdapter(mAdapter);
        for (int i = 0; i < 20; i++) {
            mAdapter.add("测试--" + i);
        }
    }
}
