package com.demos.RequestQueue;

/**
 * Created by Mr_Wrong on 16/3/28.
 */
public class Resquestfor70kg<T> implements Comparable<Resquestfor70kg>{
    T data;
    Result<T> callBack;

    public Resquestfor70kg(T data, Result<T> callBack) {
        this.data = data;
        this.callBack = callBack;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public int compareTo(Resquestfor70kg another) {
        return 0;
    }
}
