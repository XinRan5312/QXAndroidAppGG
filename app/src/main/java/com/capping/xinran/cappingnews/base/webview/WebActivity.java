package com.capping.xinran.cappingnews.base.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.base.BaseActInterface;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by qixinh on 16/9/20.
 */
public class WebActivity extends BaseWebviewActivity implements View.OnClickListener {
    public int openType = 0;
    public static final String LOCALIFE_URL = "local_url";
    public static final String OPEN_TYPE_KEY = "open_type_key";
    public static final int OPEN_TYPE_GET = 0; // GET请求
    public static final int OPEN_TYPE_POST = OPEN_TYPE_GET + 1; // POST请求
    public String localifeUrl = "";
    private Timer refWebViewTimer;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_test);
        if (myBundle != null) {
            openType = myBundle.getInt(OPEN_TYPE_KEY);
            localifeUrl = myBundle.getString(LOCALIFE_URL);
        }
        webview.addJavascriptInterface(new JsToNative(this), "toNative");
    }

    @Override
    protected void doOpenUrl() {
        openAndInitUrl("http://www.baidu.com", "");
    }

    private void openAndInitUrl(String webUrl, String webQuery) {
        url = webUrl;
        if (url == null || url.length() == 0) {
            url = DEFAULT_URL;
        }
        if (openType == OPEN_TYPE_GET) {
            // get方式打开
            webview.loadUrl(url);
        } else if (openType == OPEN_TYPE_POST) {
            // post方式打开
            try {
                webview.postUrl(webUrl, webQuery != null ? webQuery.getBytes("utf-8") : "".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
        if (refWebViewTimer == null) {
            refWebViewTimer = new Timer();
            refWebViewTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (webview != null && webview.getVisibility() == View.VISIBLE) {
                        webview.postInvalidate();
                    }
                }
            }, 200, 140);
        }
    }

    @Override
    public void onClick(View v) {
        // 根据崩溃日志，添加非空判断
        if (url == null || url.length() == 0) {
            url = DEFAULT_URL;
        }

//            webview.goBack();
//
//            webview.reload();
//
//            webview.goForward();
//
//            webview.stopLoading();
//


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        }
    }

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (refWebViewTimer != null) {
                refWebViewTimer.cancel();
                refWebViewTimer = null;
            }
        } catch (Throwable e) {
        }
    }

    public static void startActivity(BaseActInterface context, String url, String postQuery, int webViewType,
                                     boolean isPost) {
        Bundle bundle = new Bundle();
        if (isPost) {
            bundle.putString(BaseWebviewActivity.WEBVIEW_URL, url);
            bundle.putString(BaseWebviewActivity.WEBVIEW_POST_QUERY, postQuery);
            bundle.putInt(WebActivity.OPEN_TYPE_KEY, WebActivity.OPEN_TYPE_POST);
        } else {
            bundle.putString(BaseWebviewActivity.WEBVIEW_URL, url);
            bundle.putInt(WebActivity.OPEN_TYPE_KEY, WebActivity.OPEN_TYPE_GET);
        }

        context.qStartActivity(WebActivity.class, bundle);
    }

    public static void startActivity(BaseActInterface  context, Serializable paramObject) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("paramObject", paramObject);
        context.qStartActivity(WebActivity.class, bundle);
    }

    public static void startActivity(BaseActInterface context, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(WebActivity.WEBVIEW_URL, url);
        context.qStartActivity(WebActivity.class, bundle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }
}
