package com.demos.agera;

import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Reservoir;
import com.google.android.agera.Reservoirs;
import com.google.android.agera.Updatable;

/**
 * Created by wangpeng on 16/6/2.
 */

public class AgeraBus {
    private static Reservoir<Object> reservoir;
    private static Repository<Object> repository;
    static {
        reservoir = Reservoirs.reservoir();
        repository = Repositories.repositoryWithInitialValue(new Object())
                .observe(reservoir)
                .onUpdatesPerLoop()
                .goLazy()
                .thenAttemptGetFrom(reservoir).orSkip()
                .compile();
    }

    public static void post(Object object) {
        reservoir.accept(object);
    }

    public static Object getData() {
        return repository.get();
    }

    public static void addUpdatable(Updatable updatable) {
        repository.addUpdatable(updatable);
    }

    public static void removeUpdatable(Updatable updatable) {
        repository.removeUpdatable(updatable);
    }

}
