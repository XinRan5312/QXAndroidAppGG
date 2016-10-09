package com.capping.xinran.cappingnews.base.webview;

import android.os.SystemClock;
import android.view.View;

/**
 * Created by qixinh on 16/9/21.
 */
public class BaseOnLongClickListener implements View.OnLongClickListener {
   private View.OnLongClickListener longClickListener;
    private long clickTime;

    private boolean ignoreClick = true;
    @Override
    public boolean onLongClick(View view) {
        long curClickTime = SystemClock.elapsedRealtime();
        if (isIgnoreClick() && curClickTime - clickTime < 500) {
            // 忽略小于500ms内的重复点击
            return true;
        }
        clickTime = curClickTime;
        /**
         * 下面一行代码是事件打点的时候用，留着以后需要的时候用
         */
//        UELogUtils.UElog(QLog.getSecond(), v.getContext().getClass().getSimpleName(), "onClick", v);
        if (longClickListener != null) {
           return longClickListener.onLongClick(view);
        }
        return false;
    }
    public BaseOnLongClickListener() {
        this(null);
    }

    public BaseOnLongClickListener(View.OnLongClickListener listener) {
        this.longClickListener = listener;
    }

    public BaseOnLongClickListener(View.OnLongClickListener listener, boolean ignoreClick) {
        this.longClickListener = listener;
        this.ignoreClick = ignoreClick;
    }


    public boolean isIgnoreClick() {
        return ignoreClick;
    }

    public void setIgnoreClick(boolean ignoreClick) {
        this.ignoreClick = ignoreClick;
    }
}
