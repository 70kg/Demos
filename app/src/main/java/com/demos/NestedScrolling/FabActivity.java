package com.demos.NestedScrolling;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demos.R;
import com.demos.blanklayout.BlankLayout;
import com.demos.blanklayout.MyReLayout;
import com.demos.main.base.BaseAdapter;
import com.demos.main.base.MyViewHolder;
import com.demos.main.base.ToolBarActivity;
import com.demos.support_23_2.MyBottomBehavior;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Mr_Wrong on 16/2/24.
 */
public class FabActivity extends ToolBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorlayout;
    @Bind(R.id.nestedscrollview)
    NestedScrollView nestedscrollview;
    RecyclerView mRecyclerView;
    @Override
    public int getLayout() {
        return R.layout.fab_layout;
    }


    MyBottomBehavior behavior;

    @Bind(R.id.blanklayout)
    BlankLayout blanklayout;

    @Bind(R.id.refresh)
    MyReLayout mRefreshLayout;

    @OnClick(R.id.fab)
    void _fab() {
        blanklayout.showBlankView();

//        behavior.setState(MyBottomBehavior.STATE_EXPANDED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = mTool.find(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(this);
        mRecyclerView.setAdapter(adapter);
        for (int i = 0; i < 60; i++) {
            adapter.add("位置--" + i);
        }
        mRefreshLayout.setOnRefreshListener(this);

        behavior = MyBottomBehavior.from(nestedscrollview);
        behavior.setHideable(true);
        behavior.setPeekHeight(200);

    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                blanklayout.removeBlankView();
                mRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    class Adapter extends BaseAdapter<String> {

        protected Adapter(Context context) {
            super(context);
        }

        @Override
        protected void onItemClick(View v, int position) {

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
}
