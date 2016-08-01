package com.demos.imageLoader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr_Wrong on 16/3/21.
 */
public class LoaderManager {

    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String FILE = "file";

    private static LoaderManager instance;

    public static LoaderManager getInstance() {

        if (instance == null) {
            synchronized (LoaderManager.class) {
                instance = new LoaderManager();
            }
        }
        return instance;
    }

    private Map<String, Loader> mLoaderMap = new HashMap<>();

    public LoaderManager() {
        addLoader(HTTP, new UrlLoader());
        addLoader(HTTPS, new UrlLoader());
//        addLoader(FILE,new UrlLoader());
    }

    private synchronized void addLoader(String schema, Loader loader) {
        mLoaderMap.put(schema, loader);
    }

    public Loader getLoader(String schema) {
        return mLoaderMap.get(schema);
    }
}
