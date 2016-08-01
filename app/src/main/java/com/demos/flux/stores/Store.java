package com.demos.flux.stores;

import com.demos.flux.actions.Action;
import com.squareup.otto.Bus;

/**
 * Created by Mr_Wrong on 16/3/25.
 */
public abstract class Store {
    private static final Bus BUS = new Bus();

    public Store() {

    }
    public void register(Object o) {
        BUS.register(o);
    }

    public void unregister(Object o) {
        BUS.unregister(o);
    }

    public void post() {
        BUS.post(changeEvent());
    }

    public abstract void onAction(Action action);

    public abstract StoreChangeEvent changeEvent();

    public class StoreChangeEvent {
    }

}
