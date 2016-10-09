package com.capping.xinran.cappingnews.global.imageloader;


import android.net.Uri;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.capping.xinran.cappingnews.global.EngloryApplication;

import java.io.File;


/**
 * Created by qixinh on 16/9/23.
 */
public class QImageLoaderApi implements ImageLoaderInterface {
    public final static String TAG=QImageLoaderApi.class.getSimpleName();
    private ImgageLoaderConfig imgageLoaderConfig;
    private RequestManager glide;

    public QImageLoaderApi() {
        initGlide();
    }

    private void initGlide() {
        imgageLoaderConfig = new ImgageLoaderConfig();
        glide = Glide.with(EngloryApplication.getAppContext());


    }

    @Override
    public void setImgageLoaderConfig(ImgageLoaderConfig imgageLoaderConfig) {
        this.imgageLoaderConfig = imgageLoaderConfig;
    }

    /**
     * 加载本地资源中的图片
     *
     * @param resId
     * @param desImg
     */
    @Override
    public void loaderIdimg(@IdRes int resId, ImageView desImg) {
        loaderIdImgWithGlid(resId, desImg);
    }

    /**
     * 加载文件中的图片
     *
     * @param filepath 文件中图片路径
     * @param desImg
     */
    @Override
    public void loaderFileImg(String filepath, ImageView desImg) {

        loaderImgFile(filepath, desImg);
    }

    /**
     * 从android系统中加载图片
     *
     * @param androidResPath
     * @param desImg
     */
    @Override
    public void loaderAndroidResImg(String androidResPath, ImageView desImg) {
        loaderImgUri(androidResPath, desImg);
    }

    /**
     * 从网络中加载图片
     *
     * @param url
     * @param desImg
     */
    @Override
    public void loaderNetImg(String url, ImageView desImg) {
        loaderImgUrl(desImg, url);
    }

    /**
     *当列表在滑动的时候，取消请求
     */
    @Override
    public void pauseReq() {
        if(glide!=null)
        glide.pauseRequests();
    }

    /**
     * 滑动停止时，恢复请求
     */
    @Override
    public void resumeReq() {
        if(glide!=null)
            glide.resumeRequests();
    }

    /**
     * 清除掉所有的图片加载请求时
     */
    @Override
    public void cleanReq(View view) {
       if(glide!=null&&view!=null)
           Glide.clear(view);
    }

    /**
     * ListPreloader这个类让列表预加载
     */
    @Override
    public void preLoader() {

    }

    private void loaderImgUri(String androidResPath, ImageView desImg) {
        if (androidResPath != null && !androidResPath.equalsIgnoreCase("") && desImg != null) {
            Uri uri = Uri.parse(androidResPath);
            loaderImg(glide.load(uri), desImg);
        }else{
            throw new NullPointerException(TAG+".loaderImgUri(androidResPath, desImg)参数不能为空!");
        }

    }

    private void loaderImgFile(String filepath, ImageView desImg) {

        if (filepath != null && filepath.equalsIgnoreCase("") && desImg != null) {
            File file = new File(filepath);
            loaderImg(glide.load(file), desImg);
        }else{
            throw new NullPointerException(TAG+".loaderImgFile()参数不能为空!");
        }


    }

    private void loaderIdImgWithGlid(int resId, ImageView desImg) {
        if (desImg != null) {
            loaderImg(glide.load(resId), desImg);
        }else{
            throw new NullPointerException(TAG+".loaderIdImgWithGlid()参数不能为空!");
        }
    }

    private void loaderImgUrl(ImageView desImg, String url) {

        if (url != null && !url.equalsIgnoreCase("") && desImg != null) {

            loaderImg(glide.load(url), desImg);
        }else{
            throw new NullPointerException(TAG+".loaderImgUrl()参数不能为空!");
        }
    }
    private void loaderImg(DrawableTypeRequest drawableTypeRequest, ImageView desImg) {
        if (imgageLoaderConfig.getAnimate() != null) {
            if (imgageLoaderConfig.getWidth() == -1) {
                if (imgageLoaderConfig.isAsGif()) {
                    drawableTypeRequest
                            .asGif()
                            .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                            .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                            .thumbnail(imgageLoaderConfig.getThumbnailValue())
                            .error(imgageLoaderConfig.getErrorImgId())
                            .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                            .animate(imgageLoaderConfig.getAnimate())
                            .into(desImg);
                } else if (imgageLoaderConfig.isAsBitmap()) {
                    drawableTypeRequest
                            .asBitmap()
                            .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                            .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                            .thumbnail(imgageLoaderConfig.getThumbnailValue())
                            .error(imgageLoaderConfig.getErrorImgId())
                            .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                            .animate(imgageLoaderConfig.getAnimate())
                            .into(desImg);
                } else {

                    if (imgageLoaderConfig.getBitmapTransformation() == null) {
                        drawableTypeRequest
                                .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                                .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                                .thumbnail(imgageLoaderConfig.getThumbnailValue())
                                .error(imgageLoaderConfig.getErrorImgId())
                                .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                                .animate(imgageLoaderConfig.getAnimate())
                                .into(desImg);
                    } else {
                        drawableTypeRequest
                                .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                                .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                                .thumbnail(imgageLoaderConfig.getThumbnailValue())
                                .error(imgageLoaderConfig.getErrorImgId())
                                .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                                .animate(imgageLoaderConfig.getAnimate())
                                .transform(imgageLoaderConfig.getBitmapTransformation())
                                .into(desImg);
                    }
                }
            } else {
                if (imgageLoaderConfig.isAsGif()) {
                    drawableTypeRequest
                            .asGif()
                            .override(imgageLoaderConfig.getWidth(), imgageLoaderConfig.getHeight())
                            .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                            .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                            .thumbnail(imgageLoaderConfig.getThumbnailValue())
                            .error(imgageLoaderConfig.getErrorImgId())
                            .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                            .animate(imgageLoaderConfig.getAnimate())
                            .into(desImg);
                } else if (imgageLoaderConfig.isAsBitmap()) {
                    drawableTypeRequest
                            .asBitmap()
                            .override(imgageLoaderConfig.getWidth(), imgageLoaderConfig.getHeight())
                            .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                            .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                            .thumbnail(imgageLoaderConfig.getThumbnailValue())
                            .error(imgageLoaderConfig.getErrorImgId())
                            .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                            .animate(imgageLoaderConfig.getAnimate())
                            .into(desImg);
                } else {

                    if (imgageLoaderConfig.getBitmapTransformation() == null) {
                        drawableTypeRequest
                                .override(imgageLoaderConfig.getWidth(), imgageLoaderConfig.getHeight())
                                .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                                .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                                .thumbnail(imgageLoaderConfig.getThumbnailValue())
                                .error(imgageLoaderConfig.getErrorImgId())
                                .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                                .animate(imgageLoaderConfig.getAnimate())
                                .into(desImg);
                    } else {
                        drawableTypeRequest
                                .override(imgageLoaderConfig.getWidth(), imgageLoaderConfig.getHeight())
                                .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                                .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                                .thumbnail(imgageLoaderConfig.getThumbnailValue())
                                .error(imgageLoaderConfig.getErrorImgId())
                                .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                                .animate(imgageLoaderConfig.getAnimate())
                                .transform(imgageLoaderConfig.getBitmapTransformation())
                                .into(desImg);
                    }
                }
            }

        } else {
            if (imgageLoaderConfig.getWidth() == -1) {
                if (imgageLoaderConfig.isAsGif()) {
                    drawableTypeRequest
                            .asGif()
                            .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                            .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                            .thumbnail(imgageLoaderConfig.getThumbnailValue())
                            .error(imgageLoaderConfig.getErrorImgId())
                            .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                            .into(desImg);
                } else if (imgageLoaderConfig.isAsBitmap()) {
                    drawableTypeRequest
                            .asBitmap()
                            .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                            .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                            .thumbnail(imgageLoaderConfig.getThumbnailValue())
                            .error(imgageLoaderConfig.getErrorImgId())
                            .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                            .into(desImg);
                } else {

                    if (imgageLoaderConfig.getBitmapTransformation() == null) {
                        drawableTypeRequest
                                .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                                .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                                .thumbnail(imgageLoaderConfig.getThumbnailValue())
                                .error(imgageLoaderConfig.getErrorImgId())
                                .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                                .into(desImg);
                    } else {
                        drawableTypeRequest
                                .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                                .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                                .thumbnail(imgageLoaderConfig.getThumbnailValue())
                                .error(imgageLoaderConfig.getErrorImgId())
                                .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                                .transform(imgageLoaderConfig.getBitmapTransformation())
                                .into(desImg);
                    }
                }
            } else {
                if (imgageLoaderConfig.isAsGif()) {
                    drawableTypeRequest
                            .asGif()
                            .override(imgageLoaderConfig.getWidth(), imgageLoaderConfig.getHeight())
                            .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                            .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                            .thumbnail(imgageLoaderConfig.getThumbnailValue())
                            .error(imgageLoaderConfig.getErrorImgId())
                            .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                            .into(desImg);
                } else if (imgageLoaderConfig.isAsBitmap()) {
                    drawableTypeRequest
                            .asBitmap()
                            .override(imgageLoaderConfig.getWidth(), imgageLoaderConfig.getHeight())
                            .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                            .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                            .thumbnail(imgageLoaderConfig.getThumbnailValue())
                            .error(imgageLoaderConfig.getErrorImgId())
                            .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                            .into(desImg);
                } else {

                    if (imgageLoaderConfig.getBitmapTransformation() == null) {
                        drawableTypeRequest
                                .override(imgageLoaderConfig.getWidth(), imgageLoaderConfig.getHeight())
                                .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                                .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                                .thumbnail(imgageLoaderConfig.getThumbnailValue())
                                .error(imgageLoaderConfig.getErrorImgId())
                                .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                                .into(desImg);
                    } else {
                        drawableTypeRequest
                                .override(imgageLoaderConfig.getWidth(), imgageLoaderConfig.getHeight())
                                .diskCacheStrategy(imgageLoaderConfig.getDiskType())
                                .placeholder(imgageLoaderConfig.getPlaceholderImgId())
                                .thumbnail(imgageLoaderConfig.getThumbnailValue())
                                .error(imgageLoaderConfig.getErrorImgId())
                                .skipMemoryCache(imgageLoaderConfig.isSkipCache())
                                .animate(imgageLoaderConfig.getAnimate())
                                .transform(imgageLoaderConfig.getBitmapTransformation())
                                .into(desImg);
                    }
                }
            }

        }
    }
}

