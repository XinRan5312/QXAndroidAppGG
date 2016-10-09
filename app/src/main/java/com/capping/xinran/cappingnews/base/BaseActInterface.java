package com.capping.xinran.cappingnews.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * Created by qixinh on 16/9/19.
 */
public interface BaseActInterface {

    public static final String MERGED_TAG = "MERGED_DIALOG_TAG";

    void qStartActivity(Intent intent);

    void qStartActivity(Class<? extends Activity> cls);

    /**
     * 打开新的Activity
     */
    void qStartActivity(Class<? extends Activity> cls, Bundle bundle);

    void qStartShareActivity(String title, String shareContent);

    void qStartShare(String shareTitle, String shareContent, String shareUrl, Bitmap shareBmp);

    void qStartImageShare(String shareContent, Uri uri);

    /**
     * 打开新的Activity for result
     */
    void qStartActivityForResult(Class<? extends Activity> cls, Bundle bundle, int requestCode);

    /**
     * 带结果返回上一个activity， 配合qStartActivityForResult使用
     */
    void qBackForResult(int resultCode, Bundle bundle);

    /**
     * 回到之前的Activity
     */
    void qBackToActivity(Class<? extends Activity> cls, Bundle bundle);

    void qShowAlertMessage(String title, String message);

    void showErrorTip(EditText editText, String message);

    void qOpenWebView(String url);

    void qOpenWebView(String url, String postQuery, int webViewType, boolean isPost);

    void showToast(String message);

    PopupWindow showTipView(View view);

    void processAgentPhoneCall(String phoneNum);

    Context getContext();

    FragmentManager getV4FragmentManager();

    Handler getHandler();

    void onShowProgress(String message, boolean cancelAble, DialogInterface.OnCancelListener cancelListener);

    void onCloseProgress(String message);

    <V extends View> V $(@IdRes int id);

}
