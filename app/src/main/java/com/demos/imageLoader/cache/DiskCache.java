package com.demos.imageLoader.cache;

import android.graphics.Bitmap;

import com.demos.imageLoader.core.BitmapRequest;

/**
 * Created by Mr_Wrong on 16/3/21.
 * 硬盘缓存
 */
public class DiskCache implements BitmapCache {
    @Override
    public Bitmap get(BitmapRequest key) {
        return null;
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {

    }

    @Override
    public void remove(BitmapRequest key) {

    }
}
