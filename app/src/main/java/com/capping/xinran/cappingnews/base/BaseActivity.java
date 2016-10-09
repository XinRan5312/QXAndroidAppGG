package com.capping.xinran.cappingnews.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.base.webview.BaseWebviewActivity;
import com.capping.xinran.cappingnews.base.webview.WebActivity;
import com.capping.xinran.cappingnews.global.EngloryApplication;
import com.capping.xinran.cappingnews.global.GlobalContants;
import com.capping.xinran.cappingnews.global.findview.InjectFindView;
import com.capping.xinran.cappingnews.global.toast.QToast;
import com.capping.xinran.cappingnews.global.toast.QToastInterface;

/**
 * Created by qixinh on 16/9/19.
 */
public abstract class BaseActivity extends FragmentActivity implements BaseActInterface {
    protected Bundle myBundle;
    protected ViewGroup mRoot;
    private FrameLayout mAndroidContent;
    private boolean blockTouch;
    protected boolean fromRestore = false;
    private boolean mIsFirstResume = true;
    public static final String INTENT_TO_ACTIVITY = "intent_to";
    public static final String EXTRA_FROM_ACTIVITY = "__FROM_ACTIVITY__";
    private ViewGroup mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            fromRestore = true;
        }
        myBundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        if (myBundle == null) {
            myBundle = new Bundle();
        }
        mIsFirstResume = myBundle.getBoolean("mIsFirstResume", true);
        blockTouch = myBundle.getBoolean("blockTouch");
        Application application = getApplication();
        if (application instanceof EngloryApplication) {
            ((EngloryApplication) application).setActiveContext(getClass(), this);
        }
//        UELogUtils.UElogIntent(fromActivityName(),getClass().getSimpleName());页面打点的时候会用到
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    /**
     * Return if the activity is that invoked this activity. This is who the data in setResult() will be sent to.
     * Note: if the calling activity is not expecting a result (that is it did not use the startActivityForResult form
     * that includes a request code), then this method will return false
     *
     * @param clazz
     * @return
     */
    public boolean fromActivity(Class<? extends Activity> clazz) {
        try {
            return clazz.getName().equals(((Activity) getContext()).getCallingActivity().getClassName());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得来源Activity的名字，有可能为null
     *
     * @return
     */
    public String fromActivityName() {
        return myBundle.getString(EXTRA_FROM_ACTIVITY);
    }

    public void setContentView(View view, boolean autoInject) {
        final ViewGroup realRoot = genRealRootView();
        mRoot = genRootView();
        mTitleBar = new LinearLayout(this);
        mRoot.addView(mTitleBar, -1, -2);//自定义主题所有activity的mTitleBar
        mRoot.addView(view, -1, -1);
        realRoot.addView(mRoot, -1, -1);
        super.setContentView(realRoot);
        mTitleBar.setVisibility(View.GONE);
        if (autoInject) {
            InjectFindView.inject(this);
        }
    }

    public void setContentView(int layoutResID, boolean autoInject) {
        final View content = getLayoutInflater().inflate(layoutResID, null);
        setContentView(content, autoInject);
    }

    public void setContentViewStandard(int layoutResID) {
        super.setContentView(layoutResID);
        InjectFindView.inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, true);
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, true);
    }

    /**
     * 设置rootview，默认是linearlayout，子类可以自定义类型，使titlebar可以显示不同的样式（如浮动）
     *
     * @return
     */
    public ViewGroup genRootView() {
        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
//		linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.common_color_white));
        return linearLayout;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (myBundle != null) {
            outState.putAll(myBundle);
        }
        super.onSaveInstanceState(outState);
        outState.putBoolean("blockTouch", blockTouch);
        outState.putBoolean("mIsFirstResume", mIsFirstResume);
    }

    /**
     * 设置realroot，默认是FrameLayout，子类可以自定义类型
     *
     * @return
     */
    public ViewGroup genRealRootView() {
        final FrameLayout frameLayout = new FrameLayout(this);
        return frameLayout;
    }

    @Override
    public void qStartActivity(Intent intent) {
        intent.putExtra(EXTRA_FROM_ACTIVITY, getClass().getSimpleName());
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
        intent.setClass(this, cls);
        intent.putExtra(EXTRA_FROM_ACTIVITY, getClass().getSimpleName());
        startActivity(intent);
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
//        startActivity(Intent.createChooser(intent, getString(R.string.share_message)));
    }

    /* 打开新的Activity for result */
    @Override
    public void qStartActivityForResult(Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /* 带结果返回上一个activity， 配合qStartActivityForResult使用 */
    @Override
    public void qBackForResult(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(resultCode, intent);
        finish();
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
    public void startActivityForResult(Intent intent, int requestCode) {
        blockTouch = true;
        intent.putExtra(EXTRA_FROM_ACTIVITY, getClass().getSimpleName());
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        blockTouch = true;
        intent.putExtra(EXTRA_FROM_ACTIVITY, getClass().getSimpleName());
        super.startActivityFromFragment(fragment, intent, requestCode);
    }

    @Override
    public void qShowAlertMessage(String title, String message) {
        try {
            new AlertDialog.Builder(this).setTitle(title).setMessage(message).setNegativeButton("Yes", null)
                    .show();
        } catch (Exception e) {
        }
    }

    @Override
    public void showErrorTip(EditText editText, String message) {

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
        intent.setClass(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void qOpenWebView(String url) {
        qOpenWebView(url, null);
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
     * @param hideType    //0什么都不隐藏 1是隐藏差和底部黑框
     */
    public void qOpenWebView(String url, String postQuery, int webViewType, boolean isPost, int hideType) {
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
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public PopupWindow showTipView(View view) {
        return null;
    }

    @Override
    public void processAgentPhoneCall(String phoneNum) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public FragmentManager getV4FragmentManager() {
        return null;
    }

    @Override
    public Handler getHandler() {
        return null;
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
        return (V) findViewById(id);
    }

    /**
     * 尝试做finish操作,先检查当前Activity是否有添加的Fragment到back栈,如果有则执行弹栈操作
     * 之类若想处理onBackPressed事件,应在onBackPressed方法内调用此方法做一次检查
     *
     * @return true 表示back栈中没有添加过Fragment,back响应事件交给当前Activity false 执行弹栈操作,当前Activity不响应back事件
     */
    public boolean tryDoBack() {
        boolean canFinish = true;
        try {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                //判断当前back栈中有Fragment才进行弹栈操作,避免异常发生,fuck
                canFinish = !fm.popBackStackImmediate();
            }
        } catch (IllegalStateException ise) {

        }
        return canFinish;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //返回键
            if (isEqualsValue(myBundle, GlobalContants.BEHAVIOR_KEY_BACK, "1")) {
                quitApp();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void quitApp() {
        //撤销网络请求，注销所有的注册和三方以及广播，推送等
        //最后关闭kill app
    }

    /**
     * 查看Bundle数据中，某字段的数据是否为指定值
     *
     * @param bundle Bundle数据
     * @param key    某字段
     * @param value  指定值
     * @return
     */
    protected boolean isEqualsValue(Bundle bundle, String key, String value) {
        if (bundle != null && key != null && value != null) {
            if (bundle.containsKey(key) && bundle.getString(key).equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从网络下载图片
     * @param url
     * @param desImg
     */
    public void loaderNetImg(String url, ImageView desImg) {
        
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(desImg);
    }

}
