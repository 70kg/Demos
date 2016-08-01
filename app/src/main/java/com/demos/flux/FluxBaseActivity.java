package com.demos.flux;

import android.os.Bundle;

import com.demos.flux.actions.ActionsCreater;
import com.demos.flux.dispatcher.Dispatcher;
import com.demos.flux.stores.Store;
import com.demos.main.base.ToolBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Wrong on 16/3/25.
 */
public abstract class FluxBaseActivity extends ToolBarActivity {
    List<Store> mStoreList = new ArrayList<>();

    protected void initStore(Store store) {
        mStoreList.add(store);
    }

    Dispatcher mDispatcher;
    ActionsCreater mActionsCreater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDispatcher = Dispatcher.getInstance();
        mActionsCreater = ActionsCreater.getInstance(mDispatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mStoreList.isEmpty()) {
            mStoreList.get(0).register(this);
            for (Store store : mStoreList) {
                //这个是为了mDispatcher给store发消息
                mDispatcher.register(store);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mStoreList.isEmpty())
            mStoreList.get(0).unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Store store : mStoreList) {
            mDispatcher.unregister(store);
        }

    }

}
