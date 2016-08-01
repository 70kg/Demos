package com.demos.headlist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demos.R;
import com.demos.main.base.StringAdapter;
import com.demos.main.base.ToolBarActivity;

import butterknife.Bind;

/**
 * Created by Mr_Wrong on 16/3/2.
 */
public class RefreshListActivity extends ToolBarActivity {
    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    StringAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.refreshlist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StringAdapter(this) {

            @Override
            protected void onItemClick(View v, int position) {

            }
        };
        recycleview.setAdapter(mAdapter);
        for (int i = 0; i < 30; i++) {
            mAdapter.add("hehe" + i);
        }
    }
}
