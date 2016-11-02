package com.capping.xinran.cappingnews.global.net.asynchttp;

import android.annotation.SuppressLint;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 真正的网络请求类
 * Created by qixinh on 16/9/29.
 */
public class AsyncHttpNetApi implements NetApiFlagInterface {
    private Map<String, SoftReference<RequestHandle>> mRequestHandleMap;
    private AsyncHttpClient httpClient;

    public AsyncHttpNetApi(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }
    @Override
    public void setHttpClient(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        httpClient.cancelRequests(context, mayInterruptIfRunning);

    }

    @Override
    public void cancelAllRequests(boolean mayInterruptIfRunning) {
        httpClient.cancelAllRequests(mayInterruptIfRunning);
    }

    @Override
    public void cancelOneRequestByTag(Object tag, boolean mayInterruptIfRunning) {
        httpClient.cancelRequestsByTAG(tag, mayInterruptIfRunning);
    }

    @Override
    public void cancelRequests(List<RequestHandle> requestList, boolean mayInterruptIfRunning) {
        if (requestList != null && !requestList.isEmpty()) {
            for (RequestHandle requestHandle : requestList) {
                cancelOneRequest(requestHandle, mayInterruptIfRunning);
            }
        }
    }

    @Override
    public void cancelOneRequest(RequestHandle requestHandle, boolean mayInterruptIfRunning) {
        if (requestHandle != null && !requestHandle.isCancelled()) {
            requestHandle.cancel(mayInterruptIfRunning);
        }
    }

    @Override
    public void putRequestHandle(String key, RequestHandle requestHandle) {
        if (mRequestHandleMap == null) {
            mRequestHandleMap = new HashMap<String, SoftReference<RequestHandle>>();
        }
        mRequestHandleMap.put(key, new SoftReference<RequestHandle>(requestHandle));
    }

    @Override
    public void cancelAllStorageRequest(boolean mayInterruptIfRunning) {
        if (mRequestHandleMap == null || mRequestHandleMap.isEmpty()) return;
        Set<Map.Entry<String, SoftReference<RequestHandle>>> enSet = mRequestHandleMap.entrySet();
        for (Map.Entry<String, SoftReference<RequestHandle>> entry : enSet) {
            RequestHandle requestHandle = entry.getValue().get();
            if (requestHandle != null && !requestHandle.isCancelled()) {
                requestHandle.cancel(mayInterruptIfRunning);
            }
        }
        cleanAllStorageRequest();
    }

    private void cleanAllStorageRequest() {
        if (mRequestHandleMap == null || mRequestHandleMap.isEmpty()) return;
        mRequestHandleMap.clear();
    }

    @SuppressLint("NewApi")
    @Override
    public void cancelOneStorageRequest(String key, boolean mayInterruptIfRunning) {
        if (mRequestHandleMap == null || mRequestHandleMap.isEmpty()) return;

        if (mRequestHandleMap.containsKey(key)) {
            RequestHandle requestHandle = mRequestHandleMap.get(key).get();
            if (requestHandle != null && !requestHandle.isCancelled()) {
                requestHandle.cancel(mayInterruptIfRunning);
                mRequestHandleMap.remove(key);
            }
        }
    }

}
