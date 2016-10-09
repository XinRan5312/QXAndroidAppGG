package com.capping.xinran.cappingnews.global.imageloader;

import android.support.annotation.IdRes;


import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.capping.xinran.cappingnews.R;

/**
 * Created by qixinh on 16/9/23.
 */
public class ImgageLoaderConfig {
    private boolean asGif=true;
    private boolean asBitmap;
    private DiskCacheStrategy diskType = DiskCacheStrategy.RESULT;
    private int placeholderImgId= R.mipmap.ic_launcher;
    private float thumbnailValue;
    private int errorImgId=R.mipmap.ic_launcher;
    private BitmapTransformation bitmapTransformation;//定义图片圆角或者圆形显示
    private int layType;//centerCrop()=1  fitCenter()=2
    private Priority priority;
    private ViewPropertyAnimation.Animator animate;
    private boolean isSkipCache=false;
    private int width=-1;
    private int height=-1;

    public static class Builder {
        private ImgageLoaderConfig imgageLoaderConfig;
        public Builder(){
            imgageLoaderConfig=new ImgageLoaderConfig();
        }
        public Builder asGif(boolean asGif) {
            imgageLoaderConfig.asGif = asGif;
            return this;
        }
        public Builder isSkipCache(boolean isSkipCache) {
            imgageLoaderConfig.isSkipCache= isSkipCache;
            return this;
        }
        public Builder asBitmap(boolean asBitmap) {
            imgageLoaderConfig.asBitmap = asBitmap;
            return this;
        }
        public Builder animate(ViewPropertyAnimation.Animator animate) {
            imgageLoaderConfig.animate = animate;
            return this;
        }
        public Builder diskCacheStrategy(DiskCacheStrategy diskType) {
            imgageLoaderConfig.diskType = diskType;
            return this;
        }

        public Builder placeholderImgId(@IdRes int placeholderImgId) {
            imgageLoaderConfig.placeholderImgId = placeholderImgId;
            return this;
        }

        public Builder thumbnailValue(float thumbnailValue) {
            imgageLoaderConfig.thumbnailValue = thumbnailValue;
            return this;
        }

        public Builder errorImgId(@IdRes int errorImgId) {
            imgageLoaderConfig.errorImgId = errorImgId;
            return this;
        }

        public Builder bitmapTransformation(BitmapTransformation bitmapTransformation) {
            imgageLoaderConfig.bitmapTransformation = bitmapTransformation;
            return this;
        }
        public Builder widthAndHeight(int width,int height){
            imgageLoaderConfig.width=width;
            imgageLoaderConfig.height=height;
            return this;
        }
        public Builder layType(int layType) {
            imgageLoaderConfig.layType = layType;
            return this;
        }

        public Builder priority(Priority priority) {
            imgageLoaderConfig.priority = priority;
            return this;
        }

        public ImgageLoaderConfig build() {

            return imgageLoaderConfig;
        }
    }

    public ImgageLoaderConfig() {
//        Context context=null;
//        Glide.with(context)
//                .load("")
//                .skipMemoryCache();
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public ImgageLoaderConfig(int errorImgId, int placeholderImgId) {
        this.errorImgId = errorImgId;
        this.placeholderImgId = placeholderImgId;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public DiskCacheStrategy getDiskType() {
        return diskType;
    }

    public int getPlaceholderImgId() {
        return placeholderImgId;
    }

    public float getThumbnailValue() {
        if (thumbnailValue < 0f || thumbnailValue > 1f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        return thumbnailValue;
    }

    public int getErrorImgId() {
        return errorImgId;
    }

    public BitmapTransformation getBitmapTransformation() {
        return bitmapTransformation;
    }

    public int getLayType() {
        return layType;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public ViewPropertyAnimation.Animator getAnimate() {
        return animate;
    }

    public boolean isSkipCache() {
        return isSkipCache;
    }
}
