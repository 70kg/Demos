package com.demos.imageLoader.policy;

import com.demos.imageLoader.core.BitmapRequest;

/**
 * Created by Mr_Wrong on 16/3/21.
 * 加载策略
 */
public interface LoadPolicy {
    public int compare(BitmapRequest request1, BitmapRequest request2);
}