package com.demos.RequestQueue;

/**
 * Created by Mr_Wrong on 16/3/28.
 */
public abstract class Result<T> {
    public abstract void onResponse(T response);
}
