package com.capping.xinran.cappingnews.global.net.onlyok3;

import com.capping.xinran.cappingnews.global.EngloryApplication;
import com.capping.xinran.cappingnews.global.net.onlyok3.callback.Callback;
import com.capping.xinran.cappingnews.global.net.onlyok3.utils.AppUtils;
import com.capping.xinran.cappingnews.global.net.onlyok3.utils.OkHttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by houqixin on 2016/11/3.
 */
public class OkHttpManager {
    private static OkHttpManager mInstance;
    private final static String TAG=OkHttpManager.class.getSimpleName();
    private static Object clock=new Object();
    private static String mTAG;

    private OkHttpManager(){

    }
    public static OkHttpManager newInstance(String tag){
        mTAG=tag;

        if(mInstance==null){
            synchronized (clock){
                initInstance();
            }
        }
        return mInstance;
    }

    private static void initInstance() {
        if(mInstance==null){
            synchronized (clock){
                mInstance=new OkHttpManager();
            }
        }
    }

    /**
     * get 请求  无参数
     * @param url url中的path
     * @param requestTag 表示request的名称
     * @param callback   请求回调
     */
    public void get(String url, String requestTag, Callback callback) {
        OkHttpUtils.get(mTAG).url(url).tag(requestTag)
                .headers(getHeaders()).build().execute(callback);
    }

    /**
     * get 请求  需参数
     * @param url url中的path
     * @param params 请求中query中的内容
     * @param requestTag 表示request的名称
     * @param callback 请求回调
     */
    public void get(String url, Map<String, Object> params, String requestTag, Callback callback) {
        OkHttpUtils.get(mTAG).url(url).params(params).tag(requestTag)
                .headers(getHeaders())
                .build().execute(callback);
    }

    /**
     * post 请求 无参数
     */
    public void post(String url, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .build()
                .execute(callback);
    }

    /**
     * post 请求 单个数
     */
    public void post(String url, String key, String value, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .addParams(key, value).build()
                .execute(callback);
    }

    /**
     * post 请求 多个参数
     */
    public void pos(String url, Map<String, Object> params, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .params(params).build()
                .execute(callback);
    }

    /**
     * 上传文件的 无参数
     */
    public void upLoadFile(String url, String fileKey, Map<String, File> files, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .files(fileKey, files).build()
                .execute(callback);
    }

    /**
     * 上传文件
     */
    public void upLoadFileWithParams(String url, String fileKey, Map<String, File> files,
                                     Map<String, Object> params, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .params(params).files(fileKey, files).build()
                .execute(callback);
    }
    /**
     * 请求头
     */
    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("NETWORKSTATE", AppUtils.getNetWorkSate());
        headers.put("User-Agent", AppUtils.getUserAgentInfo());
        String cookie = "u=UserID" + ";"
                + "n=" + AppUtils.getID(EngloryApplication.getAppContext()) + ";"
                + "k=UserToken";
        headers.put("cookie", cookie);
        headers.put("Market", "APP_");
        return headers;
    }

    /**
     * get 请求 无参数
     */
    public void get(String url, String requestTag, Callback callback,Map<String, String> headers) {
        OkHttpUtils.get(mTAG).url(url).tag(requestTag)
                .headers(getHeaders()).build().execute(callback);
    }

    /**
     * get 请求  需参数
     */
    public void get(String url, Map<String, Object> params, String requestTag, Callback callback,Map<String, String> headers) {
        OkHttpUtils.get(mTAG).url(url).params(params).tag(requestTag)
                .headers(getHeaders())
                .build().execute(callback);
    }

    /**
     * post 请求 无参数
     */
    public void post(String url, String requestTag, Callback callback,Map<String, String> headers) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .build()
                .execute(callback);
    }

    /**
     * post 请求 单个数
     */
    public void post(String url, String key, String value, String requestTag, Callback callback,Map<String, String> headers) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .addParams(key, value).build()
                .execute(callback);
    }

    /**
     * post 请求 多个参数
     */
    public void pos(String url, Map<String, Object> params, String requestTag, Callback callback,Map<String, String> headers) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .params(params).build()
                .execute(callback);
    }

    /**
     * 上传文件的 无参数
     */
    public void upLoadFile(String url, String fileKey, Map<String, File> files, String requestTag, Callback callback,Map<String, String> headers) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .files(fileKey, files).build()
                .execute(callback);
    }

    /**
     * 上传文件
     */
    public void upLoadFileWithParams(String url, String fileKey, Map<String, File> files,
                                     Map<String, Object> params, String requestTag, Callback callback,Map<String, String> headers) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .params(params).files(fileKey, files).build()
                .execute(callback);
    }



}

