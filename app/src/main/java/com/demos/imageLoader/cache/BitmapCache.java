package com.demos.imageLoader.cache;

import android.graphics.Bitmap;

import com.demos.imageLoader.core.BitmapRequest;

/**
 * Created by Mr_Wrong on 16/3/21.
 */
public interface BitmapCache {
    public Bitmap get(BitmapRequest key);

    public void put(BitmapRequest key, Bitmap value);

    public void remove(BitmapRequest key);

}
