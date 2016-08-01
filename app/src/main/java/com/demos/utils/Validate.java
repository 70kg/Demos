package com.demos.utils;

/**
 * Created by wangpeng on 16/7/10.
 */
public class Validate {

    static void isTrue(final boolean expression, final String message, final Object... values) {
        if (expression == false) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }
}