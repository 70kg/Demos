package com.demos.flux.actions;

/**
 * Created by Mr_Wrong on 16/3/25.
 */
public class Action<T> {
    private String type;
    private T data;

    public Action(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
