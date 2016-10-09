package com.capping.xinran.cappingnews.global.imageloader;

/**
 * Created by qixinh on 16/9/29.
 */
public class QImgLoaderManager {
    private static QImgLoaderManager instance;
    private ImageLoaderInterface imgloaderManager;
    private static  Object clock=new Object();
    private QImgLoaderManager(){
        imgloaderManager=new QImageLoaderApi();
    }
    public static QImgLoaderManager newInstance(){
        if(instance==null){
            synchronized (clock){
                instance=new QImgLoaderManager();
            }
        }
        return instance;
    }

    public ImageLoaderInterface getImgloaderManager() {
        return imgloaderManager;
    }
}
