package com.demos.flux.dispatcher;

import com.demos.flux.actions.Action;
import com.demos.flux.stores.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Wrong on 16/3/25.
 */
public class Dispatcher {
    static Dispatcher mDispatcher;
    private List<Store> stores = new ArrayList<>();

    public static Dispatcher getInstance() {
        if (mDispatcher == null) {
            synchronized (Dispatcher.class) {
                if (mDispatcher == null) {
                    mDispatcher = new Dispatcher();
                }
            }
        }
        return mDispatcher;
    }

    public void register(Store store) {
        if (!stores.contains(store)) {
            stores.add(store);
        }
    }

    public void unregister(Store store) {
        stores.remove(store);
    }

    public void dispatch(Action action) {
        for (Store store : stores) {
            store.onAction(action);
        }
    }


}
