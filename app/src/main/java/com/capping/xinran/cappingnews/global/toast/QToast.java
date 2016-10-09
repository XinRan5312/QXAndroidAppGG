package com.capping.xinran.cappingnews.global.toast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.capping.xinran.cappingnews.R;

/**
 * Created by qixinh on 16/9/19.
 */
public class QToast implements QToastInterface {
    public static final int ANIMATION_FADE = android.R.style.Animation_Toast;
    public static final int ANIMATION_FLYIN = android.R.style.Animation_Translucent;
    public static final int ANIMATION_SCALE = android.R.style.Animation_Dialog;
    public static final int ANIMATION_POPUP = android.R.style.Animation_InputMethod;

    private final Context mContext;
    private final WindowManager mWindowManager;
    private View contentView;
    private TextView messageView;
    private Handler mHandler;

    private CharSequence text;
    private float textSize = 14;
    private int textColor = Color.WHITE;
    private int gravity = Gravity.BOTTOM | Gravity.CENTER;
    private Drawable backgroundDrawable;
    private long duration = QToastInterface.DURATION_SHORT;
    /**
     * A style resource defining the animations to use for this window. This must be a system resource; it can not be an
     * application resource because the window manager does not have access to applications.
     */
    private int animationStyle = ANIMATION_FADE;
    private int xOffset = 0;
    private int yOffset = 0;

    private QToastLisener mTuskiListener;

    public QToast(Context context) {
        this.mContext = context;
        yOffset = context.getResources().getDimensionPixelSize(R.dimen.toast_yoffset);
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public void show() {
        hideDelayed(duration);

        if (contentView == null) {
            contentView = new TextView(mContext);
            contentView.setId(android.R.id.message);
        }
        contentView.setBackgroundDrawable(backgroundDrawable);

        messageView = (TextView) contentView.findViewById(android.R.id.message);

        if (messageView != null) {
            messageView.setGravity(Gravity.CENTER);
            messageView.setText(text);
            messageView.setTextColor(textColor);
            messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;

        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.windowAnimations = animationStyle;
        params.gravity = gravity;
        params.x = xOffset;
        params.y = yOffset;

        mWindowManager.addView(contentView, params);
        if (mTuskiListener != null) {
            mTuskiListener.onShow();
        }
    }

    private void hideDelayed(long duration) {
        if (duration != QToastInterface.DURATION_INFINITE) {
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mHandler.postDelayed(mHideRunnable, duration);
        } else {
            throw new IllegalArgumentException("tuski must not be infinite!!!!!!");
        }
    }

    @Override
    public void setText(CharSequence text) {

        this.text = text;
        if (messageView != null) {
            messageView.setText(text);
        }

    }

    @Override
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if (messageView != null) {
            messageView.setTextColor(textColor);
        }
    }

    @Override
    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void resetDuration(long newDuration) {
        if (mHandler != null) {
            mHandler.removeCallbacks(mHideRunnable);
            mHandler = null;
        }
        hideDelayed(newDuration);
    }

    @Override
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    @Override
    public void setBackgroundResource(int res) {
        this.backgroundDrawable = mContext.getResources().getDrawable(res);
    }

    @Override
    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    /**
     * set text size in dip
     * @param textSize
     */
    @Override
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setAnimation(int animationStyle) {
        this.animationStyle = animationStyle;
    }

    @Override
    public void setXYCoordinates(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;

    }

    public void setOnDismissListener(QToastLisener mOnDismissListener) {
        this.mTuskiListener = mOnDismissListener;
    }

    public void cancel() {

        if (mHandler != null) {
            mHandler.removeCallbacks(mHideRunnable);
            mHandler = null;
        }

        if (contentView != null && mWindowManager != null) {
            mWindowManager.removeView(contentView);
            contentView = null;
        }

        if (mTuskiListener != null) {
            mTuskiListener.onDismiss();
        }
    }

    public TextView getTextView() {
        return messageView;
    }

    public int getXOffset() {
        return this.xOffset;
    }

    public int getYOffset() {
        return this.yOffset;
    }

    public void setView(View view) {
        this.contentView = view;
    }

    public View getView() {
        return contentView;
    }

    public boolean isShowing() {
        if (contentView != null) {
            return contentView.isShown();
        } else {
            return false;
        }
    }

    private final Runnable mHideRunnable = new Runnable() {
        public void run() {
            cancel();
        }
    };

    public static QToastInterface makeText(Context context, CharSequence text, Appearance appearance) {
        QToastInterface tuski = appearance.createTuski(context);
        tuski.setText(text);
        return tuski;
    }

    public static QToast makeText(Context context, CharSequence text, long duration) {
        QToast tuski = new QToast(context);
        tuski.setText(text);
        tuski.setDuration(duration);
        tuski.setBackgroundResource(android.R.drawable.toast_frame);
        return tuski;

    }

    public static QToastInterface makeText(Context context, CharSequence text, long duration, int animation) {
        QToast tuski = new QToast(context);
        tuski.setText(text);
        tuski.setDuration(duration);
        tuski.setBackgroundResource(android.R.drawable.toast_frame);
        tuski.setAnimation(animation);
        return tuski;
    }
}
