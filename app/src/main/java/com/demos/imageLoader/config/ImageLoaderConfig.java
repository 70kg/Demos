package com.demos.imageLoader.config;

import com.demos.imageLoader.cache.BitmapCache;
import com.demos.imageLoader.cache.MemoryCache;
import com.demos.imageLoader.policy.LoadPolicy;
import com.demos.imageLoader.policy.SerialPolicy;

/**
 * Created by Mr_Wrong on 16/3/21.
 * 加载器的配置
 */
public class ImageLoaderConfig {

    public BitmapCache bitmapCache = new MemoryCache();

    public DisplayConfig displayConfig = new DisplayConfig();

    public int threadCount = Runtime.getRuntime().availableProcessors() + 1;

    public LoadPolicy loadPolicy = new SerialPolicy();

    public ImageLoaderConfig setBitmapCache(BitmapCache bitmapCache) {
        this.bitmapCache = bitmapCache;
        return this;
    }

    public ImageLoaderConfig setDisplayConfig(DisplayConfig displayConfig) {
        this.displayConfig = displayConfig;
        return this;
    }
    public ImageLoaderConfig setLoadingPlaceholder(int resId) {
        displayConfig.loadingResId = resId;
        return this;
    }

    public ImageLoaderConfig setNotFoundPlaceholder(int resId) {
        displayConfig.failedResId = resId;
        return this;
    }

    public ImageLoaderConfig setThreadCount(int threadCount) {
        this.threadCount = Math.max(1, threadCount);
        return this;
    }

    public ImageLoaderConfig setLoadPolicy(LoadPolicy loadPolicy) {
        this.loadPolicy = loadPolicy;
        return this;
    }
}
