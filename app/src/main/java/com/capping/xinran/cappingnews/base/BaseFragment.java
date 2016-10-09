package com.capping.xinran.cappingnews.base;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import android.view.Gravity;
import android.view.View;

import android.widget.Toast;

import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.base.webview.BaseWebviewActivity;
import com.capping.xinran.cappingnews.base.webview.WebActivity;
import com.capping.xinran.cappingnews.global.findview.InjectFindView;
import com.capping.xinran.cappingnews.global.toast.QToast;
import com.capping.xinran.cappingnews.global.toast.QToastInterface;

/**
 * Created by qixinh on 16/9/19.
 */
public abstract class BaseFragment extends Fragment implements BaseActInterface{

    protected Bundle myBundle;
    private boolean mIsFirstResume = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBundle = savedInstanceState == null ? getArguments() : savedInstanceState;
        if (myBundle == null) {
            myBundle = new Bundle();
        }
        mIsFirstResume = myBundle.getBoolean("mIsFirstResume", true);
//        UELogUtils.UElogIntent(fromActivityName(),getClass().getSimpleName());页面打点的时候会用到
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (myBundle != null) {
            outState.putAll(myBundle);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InjectFindView.inject(this);
    }
    @Override
    public final void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onHide();
        } else {
            onShow();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mIsFirstResume) {
            onFirstResume();
        } else {
            onRegularResume();
        }
    }

    public abstract void onRegularResume();

    public abstract void onFirstResume();

    public abstract void onHide();
    public abstract void onShow();
    @Override
    public void qStartActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void qStartActivity(Class<? extends Activity> cls) {
        qStartActivity(cls, null);
    }

    /* 打开新的Activity */
    @Override
    public void qStartActivity(Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * 打开在WebActivity中打开web
     * @param url url
     * @param postQuery
     * @param webViewType WebActivity的布局页面类型，比如带不带刷新，带不带某个icon等
     * @param isPost 是否是post请求
     */
    public void qOpenWebView(String url, String postQuery, int webViewType, boolean isPost) {
        Bundle bundle = new Bundle();
        if (isPost) {
            bundle.putString(BaseWebviewActivity.WEBVIEW_URL, url);
            bundle.putString(WebActivity.WEBVIEW_POST_QUERY, postQuery);
            bundle.putInt(WebActivity.OPEN_TYPE_KEY, WebActivity.OPEN_TYPE_POST);
            qStartActivity(WebActivity.class, bundle);
        } else {
            bundle.putString(BaseWebviewActivity.WEBVIEW_URL, url);
            bundle.putInt(WebActivity.OPEN_TYPE_KEY, WebActivity.OPEN_TYPE_GET);
            qStartActivity(WebActivity.class, bundle);
        }

    }
    @Override
    public void qStartShareActivity(String title, String shareContent) {
        qStartShare(title, shareContent, null, null);
    }

    @Override
    public void qStartShare(String shareTitle, String shareContent, String shareUrl,
                            Bitmap shareBmp) {//TODO 更改方法处理url和图片
//        new ShareDialog(this, new ShareDialog.ShareData(shareTitle, shareContent, shareUrl, shareBmp)).show();
    }

    @Override
    public void qStartImageShare(String shareContent, Uri shareUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        if (shareUri == null) {
            qStartShareActivity(null, shareContent);
            return;
        }
        intent.putExtra(Intent.EXTRA_STREAM, shareUri);
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        intent.putExtra("sms_body", shareContent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(Intent.createChooser(intent, getString(R.string.share_message)));
    }

    /* 打开新的Activity for result */
    @Override
    public void qStartActivityForResult(Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }

    /* 带结果返回上一个activity， 配合qStartActivityForResult使用 */
    @Override
    public void qBackForResult(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().setResult(resultCode, intent);
        getActivity().finish();
    }

    /* 根据url跳转Activity */
    public void qStartActivity(String url, Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putBoolean("noQuitConfirm", false);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    @Override
    public void qShowAlertMessage(String title, String message) {
        try {
            new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(message).setNegativeButton("Yes", null)
                    .show();
        } catch (Exception e) {
        }
    }


    public void qShowAlertMessage(int titleResId, String message) {
        qShowAlertMessage(getString(titleResId), message);
    }

    public void qShowAlertMessage(int titleResId, int msgResId) {
        qShowAlertMessage(getString(titleResId), getString(msgResId));
    }

    /* 回到之前的Activity */
    @Override
    public void qBackToActivity(Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(getActivity(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void qOpenWebView(String url) {
        qOpenWebView(url, null);
    }

    public void qOpenWebView(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BaseWebviewActivity.WEBVIEW_URL, url);
        bundle.putInt(WebActivity.OPEN_TYPE_KEY, WebActivity.OPEN_TYPE_GET);
        bundle.putString(BaseWebviewActivity.WEBVIEW_TITLE, title);
        qStartActivity(WebActivity.class, bundle);
    }

    /**
     * @param url
     * @param postQuery
     * @param webViewType
     * @param isPost
     * @param hideType  //0什么都不隐藏 1是隐藏差和底部黑框
     */
    public void qOpenWebView(String url, String postQuery, int webViewType, boolean isPost,int hideType) {
        Bundle bundle = new Bundle();
        if (isPost) {
            bundle.putString(BaseWebviewActivity.WEBVIEW_URL, url);
            bundle.putString(WebActivity.WEBVIEW_POST_QUERY, postQuery);
            bundle.putInt(WebActivity.OPEN_TYPE_KEY, WebActivity.OPEN_TYPE_POST);
            qStartActivity(WebActivity.class, bundle);
        } else {
            bundle.putString(BaseWebviewActivity.WEBVIEW_URL, url);
            bundle.putInt(WebActivity.OPEN_TYPE_KEY, WebActivity.OPEN_TYPE_GET);
            qStartActivity(WebActivity.class, bundle);
        }
    }

    @Override
    public void showToast(String message) {

        QToast.makeText(getContext(), message, QToastInterface.DURATION_LONG).show();

    }

    public void showToast(String message, long delay) {
        QToast.makeText(getContext(), message, delay).show();
    }

    public void showToastCenter(String message) {

        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public Context getContext() {
        return getActivity();
    }
    private Fragment mCalledFragment;

    private void startActivityFromChildFragment(BaseFragment child, Intent intent, int requestCode) {
        mCalledFragment = child;
        requestCode = requestCode & 0xffff;
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (getActivity() == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null) {
            if (parentFragment instanceof BaseFragment) {
                ((BaseFragment) parentFragment).startActivityFromChildFragment(this, intent, requestCode);
            } else {
                getActivity().startActivityFromFragment(getParentFragment(), intent, requestCode);
            }
        } else {
            getActivity().startActivityFromFragment(this, intent, requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCalledFragment != null) {
            mCalledFragment.onActivityResult(requestCode & 0xffff, resultCode, data);
            mCalledFragment = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    /**
     * 创建一个自定义的dialog然后在网络请求或者是其它情况下显示
     * @param message
     * @param cancelAble
     * @param cancelListener
     */
    @Override
    public void onShowProgress(String message, boolean cancelAble, DialogInterface.OnCancelListener cancelListener) {

    }

    /**
     * Close先前创建的dialog
     * @param message
     */
    @Override
    public void onCloseProgress(String message) {

    }

    @Override
    public <V extends View> V $(@IdRes int id) {
        return (V) getActivity().findViewById(id);
    }
}
