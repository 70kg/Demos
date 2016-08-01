package com.demos.imageLoader.loader;

import com.demos.imageLoader.core.BitmapRequest;

/**
 * Created by Mr_Wrong on 16/3/21.
 * 各种加载方式的抽象
 */
public interface Loader {
    public void loadImage(BitmapRequest result);
}