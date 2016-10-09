package com.capping.xinran.cappingnews.base.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import java.util.Map;

/**
 * Created by qixinh on 16/9/20.
 */
public class BaseWebView extends WebView {
    private boolean isDestroy = false;

    public BaseWebView(Context context) {
        super(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void loadUrl(String url) {
        if (isDestroy) {
            return;
        }
        if (!url.toLowerCase().startsWith("javascript:")) {
            synCookie();
        }
        super.loadUrl(url);
    }

    public void loadJs(String js) {
        if (isDestroy) {
            return;
        }
        super.loadUrl(js);
    }

    public void postUrl(String url, byte[] postData) {
        synCookie();
        super.postUrl(url, postData);
    }

    /**
     * 同步webview和登录用户的cookie
     */
    public void synCookie() {
        CookieSyncManager.createInstance(this.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        //cookieManager.removeAllCookie();
        cookieManager.setCookie(BaseWebviewActivity.DOMAIN, "_v=null; domain=" + BaseWebviewActivity.DOMAIN);
        cookieManager.setCookie(BaseWebviewActivity.DOMAIN, "_t=null; domain=" + BaseWebviewActivity.DOMAIN);
        cookieManager.setCookie(BaseWebviewActivity.DOMAIN, "_q=null; domain=" + BaseWebviewActivity.DOMAIN);
        CookieSyncManager.getInstance().sync();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (isDestroy) {
            return;
        }
        super.onDetachedFromWindow();
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        if (isDestroy) {
            return;
        }
        synCookie();
        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void destroy() {
        isDestroy = true;
        super.destroy();
    }
}
