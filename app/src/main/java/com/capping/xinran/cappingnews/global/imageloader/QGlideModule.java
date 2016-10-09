package com.capping.xinran.cappingnews.global.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;
import com.capping.xinran.cappingnews.global.EngloryApplication;

import java.io.File;

/**
 * Created by qixinh on 16/9/27.
 */
public class QGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int memoryCacheSize = maxMemory / 8;
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
        File cacheDir = EngloryApplication.getAppContext().getExternalCacheDir();
        int diskCacheSize = 1024 * 1024 * 30;
        builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "glide", diskCacheSize));
        //设置图片解码格式
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//        默认格式RGB_565使用内存是ARGB_8888的一半，但是图片质量就没那么高了，而且不支持透明度
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.各种自定义的IDataModel比如jpg或者webp格式等，需要的时候再添加
    }
}