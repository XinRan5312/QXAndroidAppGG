package com.capping.xinran.cappingnews.global.imageloader;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by qixinh on 16/9/23.
 */
public interface ImageLoaderInterface {
    public static final String ANDROID_RESOURCE = "android.resource://";
    void loaderIdimg(@IdRes int resId,ImageView desImg);
    void loaderFileImg(String filepath,ImageView desImg);
    void loaderAndroidResImg(String path,ImageView desImg);
    void loaderNetImg(String url,ImageView desImg);
    void pauseReq();
    void resumeReq();
    void cleanReq(View view);
    void preLoader();
    void setImgageLoaderConfig(ImgageLoaderConfig imgageLoaderConfig);

}
