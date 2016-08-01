package com.demos.recycleview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

import butterknife.Bind;

/**
 * Created by wangpeng on 16/4/22.
 */
public class RecycleViewActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.recycleview;
    }

    @Bind(R.id.rv)
    MyRecycleView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecycleAdapter(this));
    }
}
