package com.capping.xinran.cappingnews.global.net.asynchttp;

import com.capping.xinran.cappingnews.global.EngloryApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * AsyncHttp 配置类 包括生成默认HttpClient，Header和Cookie等
 * Created by qixinh on 16/9/29.
 */
public class AsyncHttpConfig {
    public static AsyncHttpClient getDefaultHttClient(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        addHeaders(client);
        addCookie(client);
        return client;
    }

    private static void addCookie(AsyncHttpClient client) {
        PersistentCookieStore mCookieStore = new PersistentCookieStore(EngloryApplication.getAppContext());
        BasicClientCookie newCookie = new BasicClientCookie("cookiesare", "cappingcookie");
        newCookie.setVersion(1);
        newCookie.setDomain("capping.android.com");
        newCookie.setPath("/");
        mCookieStore.addCookie(newCookie);
        client.setCookieStore(mCookieStore);
    }

    private static void addHeaders(AsyncHttpClient client) {


    }

}
