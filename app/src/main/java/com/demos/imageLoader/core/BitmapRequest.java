package com.demos.imageLoader.core;

import android.widget.ImageView;

import com.demos.imageLoader.ImageLoaderFor70kg;
import com.demos.imageLoader.config.DisplayConfig;
import com.demos.imageLoader.loader.Loader;
import com.demos.imageLoader.loader.LoaderManager;
import com.demos.imageLoader.policy.LoadPolicy;
import com.demos.imageLoader.utils.ImageViewHelper;
import com.demos.imageLoader.utils.Md5Helper;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Mr_Wrong on 16/3/21.
 */
public class BitmapRequest implements Comparable<BitmapRequest>,Runnable {
    Reference<ImageView> mImageViewRef;
    public DisplayConfig displayConfig;
    public ImageLoaderFor70kg.ImageListener imageListener;
    public String imageUri = "";
    public String imageUriMd5 = "";
    /**
     * 请求序列号
     */
    public int serialNum = 0;
    /**
     * 是否取消该请求
     */
    public boolean isCancel = false;
    public boolean justCacheInMem = false;

    LoadPolicy mLoadPolicy = ImageLoaderFor70kg.newInstance().getLoaderConfig().loadPolicy;

    public BitmapRequest(ImageView imageView, String uri, DisplayConfig config,
                         ImageLoaderFor70kg.ImageListener listener) {
        mImageViewRef = new WeakReference<ImageView>(imageView);
        displayConfig = config;
        imageListener = listener;
        imageUri = uri;
        imageView.setTag(uri);
        imageUriMd5 = Md5Helper.toMD5(imageUri);
    }

    public void setLoadPolicy(LoadPolicy policy) {
        if (policy != null) {
            mLoadPolicy = policy;
        }
    }

    /**
     * 判断imageview的tag与uri是否相等
     *
     * @return
     */
    public boolean isImageViewTagValid() {
        return mImageViewRef.get() != null ? mImageViewRef.get().getTag().equals(imageUri) : false;
    }

    public ImageView getImageView() {
        return mImageViewRef.get();
    }

    public int getImageViewWidth() {
        return ImageViewHelper.getImageViewWidth(mImageViewRef.get());
    }

    public int getImageViewHeight() {
        return ImageViewHelper.getImageViewHeight(mImageViewRef.get());
    }

    @Override
    public int compareTo(BitmapRequest another) {
        return mLoadPolicy.compare(this, another);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imageUri == null) ? 0 : imageUri.hashCode());
        result = prime * result + ((mImageViewRef == null) ? 0 : mImageViewRef.get().hashCode());
        result = prime * result + serialNum;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitmapRequest other = (BitmapRequest) obj;
        if (imageUri == null) {
            if (other.imageUri != null)
                return false;
        } else if (!imageUri.equals(other.imageUri))
            return false;
        if (mImageViewRef == null) {
            if (other.mImageViewRef != null)
                return false;
        } else if (!mImageViewRef.get().equals(other.mImageViewRef.get()))
            return false;
        if (serialNum != other.serialNum)
            return false;
        return true;
    }

    @Override
    public void run() {
        final String schema = parseSchema(this.imageUri);
        Loader imageLoader = LoaderManager.getInstance().getLoader(schema);
        imageLoader.loadImage(this);
    }
    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        }
        return "";
    }
}