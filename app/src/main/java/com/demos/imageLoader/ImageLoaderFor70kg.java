package com.demos.imageLoader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.demos.imageLoader.cache.BitmapCache;
import com.demos.imageLoader.cache.MemoryCache;
import com.demos.imageLoader.config.DisplayConfig;
import com.demos.imageLoader.config.ImageLoaderConfig;
import com.demos.imageLoader.core.BitmapRequest;
import com.demos.imageLoader.core.BitmapRequestQueue;
import com.demos.imageLoader.policy.SerialPolicy;

/**
 * Created by Mr_Wrong on 16/3/21.
 */
public class ImageLoaderFor70kg {
    BitmapRequestQueue mImageQueue;
    static ImageLoaderFor70kg mImageLoaderFor70kg;
    ImageLoaderConfig mLoaderConfig;
    private volatile BitmapCache mCache = new MemoryCache();

    private ImageLoaderFor70kg() {
    }

    public static ImageLoaderFor70kg newInstance() {
        if (mImageLoaderFor70kg == null) {
            synchronized (ImageLoaderFor70kg.class) {
                mImageLoaderFor70kg = new ImageLoaderFor70kg();
            }
        }
        return mImageLoaderFor70kg;
    }


    public void init(ImageLoaderConfig config) {
        mLoaderConfig = config;
        mCache = mLoaderConfig.bitmapCache;
        checkConfig();
        mImageQueue = new BitmapRequestQueue(mLoaderConfig.threadCount);
        mImageQueue.start();
    }

    private boolean checkConfig() {
        boolean hasConfig;
        if (mLoaderConfig == null) {
            hasConfig = false;
            throw new RuntimeException(
                    "The config of ImageLoaderFor70kg is Null, please call the init(ImageLoaderConfig config) method to initialize");
        }
        if (mLoaderConfig.loadPolicy == null) {
            mLoaderConfig.loadPolicy = new SerialPolicy();
        }
        if (mCache == null) {
            mCache = new MemoryCache();
        }
        hasConfig = true;
        return hasConfig;
    }


    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null, null);
    }

    public void displayImage(ImageView imageView, String uri, DisplayConfig config) {
        displayImage(imageView, uri, config, null);
    }

    public void displayImage(ImageView imageView, String uri, ImageListener listener) {
        displayImage(imageView, uri, null, listener);
    }

    public void displayImage(final ImageView imageView, final String uri,
                             final DisplayConfig config, final ImageListener listener) {
        BitmapRequest request = new BitmapRequest(imageView, uri, config, listener);
        request.displayConfig = request.displayConfig != null ? request.displayConfig
                : mLoaderConfig.displayConfig;
        mImageQueue.addRequest(request);
    }

    public ImageLoaderConfig getLoaderConfig() {
        return mLoaderConfig;
    }

    public void stop() {
        mImageQueue.stop();
    }

    public static interface ImageListener {
        public void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

}
