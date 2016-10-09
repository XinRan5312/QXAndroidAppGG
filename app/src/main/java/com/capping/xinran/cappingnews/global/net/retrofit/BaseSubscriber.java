package com.capping.xinran.cappingnews.global.net.retrofit;


import rx.Subscriber;

/**
 * Created by qixinh on 16/9/22.
 */
public abstract class BaseSubscriber <T> extends Subscriber<T> {
    @Override
    public void onError(Throwable e) {
        if(e instanceof ApiException){
            onError((ApiException)e);
        }else{
            onError(new ApiException(e,123));
        }
    }
    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);
}
