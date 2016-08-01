package com.demos.imageLoader.core;

import com.demos.imageLoader.loader.Loader;
import com.demos.imageLoader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Mr_Wrong on 16/3/21.
 */
public class RequestDispatcher extends Thread {

    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> requestQueue) {
        mRequestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                final BitmapRequest request = mRequestQueue.take();
                if (request.isCancel) {
                    continue;
                }
                final String schema = parseSchema(request.imageUri);
                //把uri的类型信息传进去  然后选择对应的loader进行加载
                Loader imageLoader = LoaderManager.getInstance().getLoader(schema);
                imageLoader.loadImage(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        }
        return "";
    }
}
