package com.demos.imageLoader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.demos.imageLoader.ImageLoaderFor70kg;
import com.demos.imageLoader.cache.BitmapCache;
import com.demos.imageLoader.config.DisplayConfig;
import com.demos.imageLoader.core.BitmapRequest;

/**
 * Created by Mr_Wrong on 16/3/21.
 * 抽象的loader
 */
public abstract class AbsLoader implements Loader {
    Bitmap resultBitmap;
    private static BitmapCache mCache = ImageLoaderFor70kg.newInstance().getLoaderConfig().bitmapCache;

    @Override
    public void loadImage(BitmapRequest request) {
        Bitmap bitmap = mCache.get(request);
        if (bitmap == null) {
            showLoading(request);
            resultBitmap = onLoadImage(request);
            cacheBitmap(request, resultBitmap);
        } else {
            request.justCacheInMem = true;
        }

        deliveryToUIThread(request, resultBitmap);
    }

    private void cacheBitmap(BitmapRequest request, Bitmap resultBitmap) {
        if (request != null && mCache != null) {
            synchronized (mCache) {
                mCache.put(request, resultBitmap);
            }
        }
    }

    /**
     * 获取图片成功 分发到主线程
     *
     * @param request
     * @param resultBitmap
     */
    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap resultBitmap) {
        final ImageView imageView = request.getImageView();
        if (imageView == null) {
            return;
        }
        imageView.post(new Runnable() {
            @Override
            public void run() {
                updateImageView(request, resultBitmap);
            }
        });
    }

    private void updateImageView(BitmapRequest request, Bitmap resultBitmap) {
        final ImageView imageView = request.getImageView();
        final String uri = request.imageUri;
        if (request != null && imageView.getTag().equals(uri)) {
            imageView.setImageBitmap(resultBitmap);
        }

        // 加载失败
        if (request == null && hasFaildPlaceholder(request.displayConfig)) {
            imageView.setImageResource(request.displayConfig.failedResId);
        }

        // 回调接口
        if (request.imageListener != null) {
            request.imageListener.onComplete(imageView, resultBitmap, uri);
        }
    }


    /**
     * 各种不同的loader去实现不同的加载方式
     *
     * @param request
     * @return
     */
    public abstract Bitmap onLoadImage(BitmapRequest request);

    /**
     * 显示正在加载的占位图片
     *
     * @param request
     */
    private void showLoading(final BitmapRequest request) {
        final ImageView imageView = request.getImageView();
        if (request.isImageViewTagValid()
                && hasLoadingPlaceholder(request.displayConfig)) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(request.displayConfig.loadingResId);
                }
            });
        }
    }

    private boolean hasLoadingPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.loadingResId > 0;
    }

    private boolean hasFaildPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.failedResId > 0;
    }
}
