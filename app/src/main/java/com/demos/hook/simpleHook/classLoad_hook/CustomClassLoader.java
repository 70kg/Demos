package com.demos.hook.simpleHook.classLoad_hook;

import dalvik.system.DexClassLoader;

/**
 * Created by wangpeng on 16/7/17.
 * 自定义的ClassLoader, 用于加载"插件"的资源和代码
 */
public class CustomClassLoader extends DexClassLoader {
    public CustomClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }
}
