package com.capping.xinran.cappingnews.base;

import android.os.SystemClock;
import android.view.View;

/**
 * 所有的控件在setOnClickListener时传入这个，为以后事件打点使用
 * Created by qixinh on 16/9/19.
 */
public class BaseOnClickLisener implements View.OnClickListener {
    private final View.OnClickListener mListener;
    private long clickTime;

    private boolean ignoreClick = true;

    public BaseOnClickLisener() {
        this(null);
    }

    public BaseOnClickLisener(View.OnClickListener listener) {
        this.mListener = listener;
    }

    public BaseOnClickLisener(View.OnClickListener listener, boolean ignoreClick) {
        this.mListener = listener;
        this.ignoreClick = ignoreClick;
    }

    @Override
    public void onClick(View v) {
        long curClickTime = SystemClock.elapsedRealtime();
        if (isIgnoreClick() && curClickTime - clickTime < 500) {
            // 忽略小于500ms内的重复点击
            return;
        }
        clickTime = curClickTime;
        /**
         * 下面一行代码是事件打点的时候用，留着以后需要的时候用
          */
//        UELogUtils.UElog(QLog.getSecond(), v.getContext().getClass().getSimpleName(), "onClick", v);
        if (mListener != null) {
            mListener.onClick(v);
        }
    }

    public boolean isIgnoreClick() {
        return ignoreClick;
    }

    public void setIgnoreClick(boolean ignoreClick) {
        this.ignoreClick = ignoreClick;
    }
}
