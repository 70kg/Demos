package com.demos.hook.simpleHook.classLoad_hook;

import android.content.pm.ApplicationInfo;
import android.os.UserHandle;

import com.demos.utils.MethodUtils;
import com.demos.utils.Utils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangpeng on 16/7/16.
 * <p/>
 * 在ActivityThread中有个mPackages:HashMap 保存了key ->包名:String ,value ->apk文件在内存中的表示:LoadedApk
 * 因为classloader无法识别加载外部的类，所以要去让classloader认识外面的类，第一种是我们自己去接管整个的classloader加载
 * 让它去认识外部的类
 * 在这里主要是在mPackages这个map中加入LoadedApk为我们自己构造的外部的的LoadedApk
 */
public class LoadedApkClassLoaderHookHelper {

    public static Map<String, Object> sLoadedApk = new HashMap<String, Object>();

    /**
     * 1，获取mPackages
     * 2，构造生成自己的loadedapk 加入自定义的classloader 关键是构造applicationInfo
     * 3,将生成的loadedApk加入mPackages中
     *
     * @param apkFile
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     */
    public static void hookLoadedApkInActivityThread(File apkFile) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, InstantiationException {

        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        // 获取到 mPackages 这个静态成员变量, 这里缓存了dex包的信息
        Field mPackagesField = activityThreadClass.getDeclaredField("mPackages");
        mPackagesField.setAccessible(true);
        Map mPackages = (Map) mPackagesField.get(currentActivityThread);

        // android.content.res.CompatibilityInfo
        Class<?> compatibilityInfoClass = Class.forName("android.content.res.CompatibilityInfo");
        Method getPackageInfoNoCheckMethod = activityThreadClass.getDeclaredMethod("getPackageInfoNoCheck", ApplicationInfo.class, compatibilityInfoClass);

        Field defaultCompatibilityInfoField = compatibilityInfoClass.getDeclaredField("DEFAULT_COMPATIBILITY_INFO");
        defaultCompatibilityInfoField.setAccessible(true);

        Object defaultCompatibilityInfo = defaultCompatibilityInfoField.get(null);
        ApplicationInfo applicationInfo = generateApplicationInfo(apkFile);

        //最终是构造出自己的支持加载外部class的loadedApk对象，然后加入到mPackages中
        Object loadedApk = getPackageInfoNoCheckMethod.invoke(currentActivityThread, applicationInfo, defaultCompatibilityInfo);

        String odexPath = Utils.getPluginOptDexDir(applicationInfo.packageName).getPath();
        String libDir = Utils.getPluginLibDir(applicationInfo.packageName).getPath();
        //注入自己定义的classloader 这个classloader已经知道dex的位置  可以加载外部class
        ClassLoader classLoader = new CustomClassLoader(apkFile.getPath(), odexPath, libDir, ClassLoader.getSystemClassLoader());
        Field mClassLoaderField = loadedApk.getClass().getDeclaredField("mClassLoader");
        mClassLoaderField.setAccessible(true);
        mClassLoaderField.set(loadedApk, classLoader);

        // 由于是弱引用, 因此我们必须在某个地方存一份, 不然容易被GC; 那么就前功尽弃了.
        sLoadedApk.put(applicationInfo.packageName, loadedApk);

        WeakReference weakReference = new WeakReference(loadedApk);
        mPackages.put(applicationInfo.packageName, weakReference);
    }

    /**
     * 这个方法的最终目的是调用
     * android.content.pm.PackageParser#generateActivityInfo(android.content.pm.PackageParser.Activity, int, android.content.pm.PackageUserState, int)
     */
    public static ApplicationInfo generateApplicationInfo(File apkFile)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {

        // 找出需要反射的核心类: android.content.pm.PackageParser
        Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");

        // android.content.pm.PackageParser#generateApplicationInfo(android.content.pm.PackageParser.Package,
        // int, android.content.pm.PackageUserState)
        Class<?> packageParser$PackageClass = Class.forName("android.content.pm.PackageParser$Package");
        Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
        Method generateApplicationInfoMethod = packageParserClass.getDeclaredMethod("generateApplicationInfo",
                packageParser$PackageClass,
                int.class,
                packageUserStateClass);

        // 接下来构建需要得参数
        //创建出一个PackageParser对象供使用
        Object packageParser = packageParserClass.newInstance();
        // 调用 PackageParser.parsePackage 解析apk的信息
        Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);

        //1实际上是一个 android.content.pm.PackageParser.Package 对象 flag 0是全部解析
        Object packageObj = parsePackageMethod.invoke(packageParser, apkFile, 0);

        // 第三个参数 mDefaultPackageUserState 我们直接使用默认构造函数构造一个出来即可
        Object defaultPackageUserState = packageUserStateClass.newInstance();

        ApplicationInfo applicationInfo = (ApplicationInfo) generateApplicationInfoMethod.invoke(packageParser,
                packageObj, 0, defaultPackageUserState);
        String apkPath = apkFile.getPath();

        applicationInfo.sourceDir = apkPath;
        applicationInfo.publicSourceDir = apkPath;

        return applicationInfo;
    }

    //这个方法有兼容性问题
    public static ApplicationInfo generateApplicationInfo1(File apkFile)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {
        Class<?> sPackageParserClass = Class.forName("android.content.pm.PackageParser");
        Object mPackageParser = sPackageParserClass.newInstance();
        Object mPackage = MethodUtils.invokeMethod(mPackageParser, "parsePackage", apkFile, 0);
        Class<?> sPackageUserStateClass = Class.forName("android.content.pm.PackageUserState");
        Object mDefaultPackageUserState = sPackageUserStateClass.newInstance();
        Object mUserId = getCallingUserId();
        Method method = MethodUtils.getAccessibleMethod(sPackageParserClass, "generateApplicationInfo",
                mPackage.getClass(), int.class, sPackageUserStateClass, int.class);
        return (ApplicationInfo) method.invoke(null, mPackage, 0, mDefaultPackageUserState, mUserId);
    }

    public static int getCallingUserId() {
        try {
            return (int) MethodUtils.invokeStaticMethod(UserHandle.class, "getCallingUserId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
