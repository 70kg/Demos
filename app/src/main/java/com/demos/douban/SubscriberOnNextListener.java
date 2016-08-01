package com.demos.douban;

/**
 * Created by Mr_Wrong on 16/3/17.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}