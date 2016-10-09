package com.capping.xinran.cappingnews.global;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.capping.xinran.cappingnews.BuildConfig;
import com.capping.xinran.cappingnews.base.BaseActivity;
import com.capping.xinran.cappingnews.third.EgBockCanaryContext;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import io.rong.imkit.RongIM;

/**
 * Created by qixinh on 16/9/18.
 */
public class EngloryApplication extends MultiDexApplication implements LoginInterface, Thread.UncaughtExceptionHandler {
    private int clientLoginStatus = CLIENT_UNLOGIN;
    private final LinkedHashMap<Class<? extends BaseActivity>, WeakReference<Context>> contextObjects = new
            LinkedHashMap<Class<? extends BaseActivity>, WeakReference<Context>>();
    private final LinkedList<WeakReference<Context>> openedActivities = new LinkedList<WeakReference<Context>>();
    private static SoftReference<EngloryApplication> application;
    private Thread.UncaughtExceptionHandler originHandler;
    private final static String TAG = "EngloryApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        /**
         * 初始化融云
         */
        RongIM.init(this);
        originHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        String currentProcess = getCurrentProcessName(this);
        if (currentProcess != null && !currentProcess.equalsIgnoreCase(this.getPackageName())) {
            return;
        }
        if (BuildConfig.DEBUG && BuildConfig.BLOCKCANARY_ENABLED) {
            BlockCanary.install(this, new EgBockCanaryContext()).start();
        }
        if (BuildConfig.DEBUG && BuildConfig.LEAKCANARY_ENABLED) {
            LeakCanary.install(this);
        }
        application = new SoftReference<EngloryApplication>(this);
    }

    public static EngloryApplication getAppContext() {
       Log.e("Franky",application.toString());
        return application.get();

    }

    public synchronized void setActiveContext(Class<? extends BaseActivity> className, Context context) {
        WeakReference<Context> ref = new WeakReference<Context>(context);
        this.contextObjects.put(className, ref);
        this.openedActivities.add(ref);
    }

    private String getCurrentProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
            return null;
        } catch (Exception ex) {
            return null;
        }

    }

    public synchronized Context getLastContext() {
        ArrayList<Class<? extends BaseActivity>> templList = new ArrayList<Class<? extends BaseActivity>>
                (contextObjects.keySet());
        for (ListIterator<Class<? extends BaseActivity>> it = templList.listIterator(templList.size()); it.hasPrevious
                (); ) {
            Object key = it.previous();
            WeakReference<Context> ref = contextObjects.get(key);
            if (ref == null) {
                return null;
            }
            final Context c = ref.get();
            if (c == null) {
                contextObjects.remove(key);
                continue;
            }
            return c;
        }
        return null;
    }

    public synchronized Context getActiveContext(Class<? extends BaseActivity> className) {
        WeakReference<Context> ref = contextObjects.get(className);
        if (ref == null) {
            return null;
        }
        final Context c = ref.get();
        if (c == null) {
            contextObjects.remove(className);
        }
        return c;
    }

    public synchronized void resetActiveContext(Class<? extends BaseActivity> className) {
        contextObjects.remove(className);
    }

    public Iterator<WeakReference<Context>> getOpenedActivitiesIterator() {
        return openedActivities.iterator();
    }

    @Override
    public void setClientLoginStatus(int status) {
        this.clientLoginStatus = status;
    }

    @Override
    public int getClientLoginStatus() {
        return clientLoginStatus;
    }

    @Override
    public Handler getHandler() {
        return null;
    }

    @Override
    public String getNetworkInfo() {
        return null;
    }

    /**
     * 程序关闭回调
     */
    public void onClose() {
        setClientLoginStatus(CLIENT_UNLOGIN);
        for (WeakReference<Context> reference : openedActivities) {
            if (reference.get() != null && reference.get() instanceof Activity) {
                ((Activity) reference.get()).finish();
            }
        }
        //如果有网络请求关闭之

    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        boolean ishandleEx = handleException(throwable);
        if (!ishandleEx && originHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            originHandler.uncaughtException(thread, throwable);

        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ex.printStackTrace();
                String err = "[" + ex.getMessage() + "]";
                //此处自定义一个Dialog或者是Toast提示错误

                Looper.loop();
            }

        }.start();


        String errInfo = collectDeviceInfo(ex);
        saveCrashInfo2File(errInfo);
        return true;
    }

    /**
     * 保存日志文件
     *
     * @param errInfo
     */
    private void saveCrashInfo2File(String errInfo) {

    }

    /**
     * 收集设备参数信息 \日志信息
     *
     * @param ex
     * @return
     */
    private String collectDeviceInfo(Throwable ex) {
        return "";
    }
}


