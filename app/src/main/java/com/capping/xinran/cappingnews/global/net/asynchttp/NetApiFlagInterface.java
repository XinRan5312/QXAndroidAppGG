package com.capping.xinran.cappingnews.global.net.asynchttp;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;

import java.util.List;

/**
 * 所有网络请求接口的定义接口
 * Created by qixinh on 16/9/29.
 */
public interface NetApiFlagInterface {

    /**
     * 在AsyncHttpClient中取消全部请求
     *
     * @param mayInterruptIfRunning
     */
    void cancelRequests(Context context, boolean mayInterruptIfRunning);

    /**
     * 在AsyncHttpClient中取消全部请求
     *
     * @param mayInterruptIfRunning
     */
    void cancelAllRequests(boolean mayInterruptIfRunning);

    /**
     * 指定RequestHandle取消请求
     *
     * @param requestList
     * @param mayInterruptIfRunning
     */
    void cancelRequests(final List<RequestHandle> requestList, final boolean mayInterruptIfRunning);

    /**
     * 在RequestHandle中用来取消单个请求的
     *
     * @param mayInterruptIfRunning
     */
    void cancelOneRequest(RequestHandle requestHandle, boolean mayInterruptIfRunning);

    /**
     * 根据tag取消请求
     *
     * @param mayInterruptIfRunning
     */
    void cancelOneRequestByTag(Object tag, boolean mayInterruptIfRunning);

    /**
     * 把一个RequestHandle放到请求队列里
     *
     * @param key
     * @param requestHandle
     */
    void putRequestHandle(String key, RequestHandle requestHandle);

    /**
     * 取消所有保存的请求
     *
     * @param mayInterruptIfRunning
     */
    void cancelAllStorageRequest(boolean mayInterruptIfRunning);

    /**
     * 取消指定保存的请求
     *
     * @param key
     * @param mayInterruptIfRunning
     */
    void cancelOneStorageRequest(String key, boolean mayInterruptIfRunning);

    void setHttpClient(AsyncHttpClient httpClient);

}
