package com.capping.xinran.cappingnews.global.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by qixinh on 16/9/26.
 */
public class QFileUtils {
    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @param context    The context to use
     * @param uniqueName A unique directory name to append to the cache dir
     * @return The cache dir
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        File file = null;
        try {
            final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? QFileUtils
                    .getExternalCacheDir(context).getPath() : context.getCacheDir().getPath();
            file = new File(cachePath + File.separator + uniqueName);
        } catch (Exception e) {
        }
        return file;
    }

    public static long getCacheSize(Context context, String uniqueName) {
        File path = getDiskCacheDir(context, uniqueName);
        try {
            return getDirSize(path);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 取得文件夹大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getDirSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getDirSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false otherwise.
     */
    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (CompatUtil.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    public static File getExternalCacheDir(Context context) {
        if (CompatUtil.hasFroyo()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * <pre>
     * 获取外部files目录
     * 外部目录：mnt/sdcard/Android/data/com.Qunar/files/
     * </pre>
     *
     * @param context The context to use
     * @return The external files dir
     */
    @TargetApi(8)
    public static File getExternalFilesDir(Context context) {
        if (CompatUtil.hasFroyo()) {
            return context.getExternalFilesDir(null);
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String dir = "/Android/data/" + context.getPackageName() + "/files/";
        return new File(Environment.getExternalStorageDirectory().getPath() + dir);
    }

    /**
     * <pre>
     * 获取Files目录，如果有sdcard，返回sdcard，否则返回机身自带存储目录。
     * 外部目录：mnt/sdcard/Android/data/com.Qunar/files/
     * 机身自带目录:data/data/com.Qunar/files/
     * </pre>
     *
     * @param context The context to use
     * @return The files dir
     */
    public static File getFilesDir(Context context) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        try {
            final File cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? QFileUtils
                    .getExternalFilesDir(context) : context.getFilesDir();
            return cachePath;
        } catch (Exception e) {
        }
        return null;
    }
}
