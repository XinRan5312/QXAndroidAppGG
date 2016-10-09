package com.capping.xinran.cappingnews.global.toast;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.capping.xinran.cappingnews.R;

/**
 * 随着Activity的消失而消失
 * Created by qixinh on 16/9/19.
 */
public class WithActToast implements QToastInterface {
    private static final String TAG = WithActToast.class.getSimpleName();

    private Context mContext;
    private ViewGroup viewGroup;
    private View contentView;
    private TextView messageView;
    private Handler mHandler;

    private CharSequence text;
    private int textColor = Color.WHITE;
    private float textSize = 12;
    private int gravity = Gravity.BOTTOM | Gravity.CENTER;
    private Drawable backgroundDrawable;
    private long duration = DURATION_SHORT;
    private int xOffset = 0;
    private int yOffset = 0;

    private View.OnClickListener mOnClickListener;
    private Animation showAnimation;
    private Animation dismissAnimation;
    private boolean touchDismiss;
    private boolean touchImmediateDismiss;

    private QToastLisener mTuskiListener;

    public WithActToast(Context context) {
        if (context instanceof Activity) {
            this.mContext = context;
            yOffset = context.getResources().getDimensionPixelSize(R.dimen.toast_yoffset);
            viewGroup = (ViewGroup) ((Activity) context).findViewById(android.R.id.content);
        } else {
            throw new IllegalArgumentException("The Context that you passed was not an Activity!");
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void show() {

        hideDelayed(duration);

        if (contentView == null) {
            contentView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                    R.layout.tumosonov, viewGroup, false);
        }

        if (mOnClickListener != null) {
            contentView.setOnClickListener(mOnClickListener);
        }

        if (touchDismiss || touchImmediateDismiss) {
            if (touchDismiss) {
                contentView.setOnTouchListener(mTouchDismissListener);
            } else if (touchImmediateDismiss) {
                contentView.setOnTouchListener(mTouchImmediateDismissListener);
            }
        }
        contentView.setBackgroundDrawable(backgroundDrawable);

        messageView = (TextView) contentView.findViewById(android.R.id.message);

        if (messageView != null) {
            messageView.setText(text);
            messageView.setTextColor(textColor);
            messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }

        ViewGroup.LayoutParams contentLayoutParams = null;
        if (viewGroup instanceof FrameLayout) {
            contentLayoutParams = new FrameLayout.LayoutParams(-2, -2, gravity);
        }
        if (contentLayoutParams != null && contentLayoutParams instanceof ViewGroup.MarginLayoutParams) {
            if ((gravity & Gravity.TOP) == Gravity.TOP) {
                ((ViewGroup.MarginLayoutParams) contentLayoutParams).topMargin = yOffset;
            } else {
                ((ViewGroup.MarginLayoutParams) contentLayoutParams).bottomMargin = yOffset;
            }

            if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
                ((ViewGroup.MarginLayoutParams) contentLayoutParams).rightMargin = xOffset;
            } else {
                ((ViewGroup.MarginLayoutParams) contentLayoutParams).leftMargin = xOffset;
            }
        }

        if (contentView != null && viewGroup != null) {
            viewGroup.removeView(contentView);
        }
        if (contentLayoutParams == null) {
            viewGroup.addView(contentView);
        } else {
            viewGroup.addView(contentView, contentLayoutParams);
        }

        if (showAnimation != null) {
            contentView.startAnimation(showAnimation);
        } else {
            contentView.startAnimation(getFadeInAnimation());
        }

        if (mTuskiListener != null) {
            mTuskiListener.onShow();
        }

    }

    private void hideDelayed(long duration) {
        if (duration != DURATION_INFINITE) {
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mHandler.postDelayed(mHideRunnable, duration);
        }
    }

    public void setText(CharSequence textCharSequence) {
        this.text = textCharSequence;
        if (messageView != null) {
            messageView.setText(textCharSequence);
        }
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if (messageView != null) {
            messageView.setTextColor(textColor);
        }
    }

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

    public void setOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;

    }

    public void setBackgroundResource(int backgroundResource) {
        setBackgroundDrawable(mContext.getResources().getDrawable(backgroundResource));
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setShowAnimation(Animation showAnimation) {
        this.showAnimation = showAnimation;
    }

    public void setDismissAnimation(Animation dismissAnimation) {
        this.dismissAnimation = dismissAnimation;
    }

    public void setTouchToDismiss(boolean touchDismiss) {
        this.touchDismiss = touchDismiss;
    }

    public void setTouchToImmediateDismiss(boolean touchImmediateDismiss) {
        this.touchImmediateDismiss = touchImmediateDismiss;
    }

    public void setOnDismissListener(QToastLisener mOnDismissListener) {
        this.mTuskiListener = mOnDismissListener;
    }

    @Override
    public void setGravity(int gravity) {
        this.gravity = gravity;

    }

    @Override
    public void setXYCoordinates(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void cancel() {
        dismissWithAnimation();
    }

    public void dismissImmediately() {

        if (mHandler != null) {
            mHandler.removeCallbacks(mHideRunnable);
            mHandler = null;
        }

        if (contentView != null && viewGroup != null) {
            viewGroup.removeView(contentView);
            // contentView = null;
        } else {

        }
        if (mTuskiListener != null) {
            mTuskiListener.onDismiss();
        }
    }

    public TextView getTextView() {
        return messageView;
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

    private final Runnable mHideImmediateRunnable = new Runnable() {

        public void run() {
            dismissImmediately();
        }

    };

    private Animation getFadeInAnimation() {
        AlphaAnimation mAlphaAnimation = new AlphaAnimation(0f, 1f);
        mAlphaAnimation.setDuration(500);
        mAlphaAnimation.setInterpolator(new AccelerateInterpolator());
        return mAlphaAnimation;

    }

    private Animation getFadeOutAnimation() {
        AlphaAnimation mAlphaAnimation = new AlphaAnimation(1f, 0f);
        mAlphaAnimation.setDuration(500);
        mAlphaAnimation.setInterpolator(new AccelerateInterpolator());
        return mAlphaAnimation;

    }

    private final View.OnTouchListener mTouchDismissListener = new View.OnTouchListener() {

        int timesTouched;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (timesTouched == 0) {
                cancel();
            }
            timesTouched++;
            return false;
        }

    };

    private final View.OnTouchListener mTouchImmediateDismissListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            dismissImmediately();
            return false;
        }

    };

    private void dismissWithAnimation() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mHideRunnable);
            mHandler = null;
        }
        if (dismissAnimation != null) {
            dismissAnimation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationEnd(Animation animation) {
                    /** Must use Handler to modify ViewGroup in onAnimationEnd() **/
                    Handler mHandler = new Handler();
                    mHandler.post(mHideImmediateRunnable);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

            });
            contentView.startAnimation(dismissAnimation);
        } else {
            Animation mAnimation = getFadeOutAnimation();
            mAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    /** Must use Handler to modify ViewGroup in onAnimationEnd() **/
                    Handler mHandler = new Handler();
                    mHandler.post(mHideImmediateRunnable);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

            });
            contentView.startAnimation(mAnimation);
        }

    }

    public static WithActToast makeText(Context context, CharSequence text, long duration) {
        WithActToast tumosonov = new WithActToast(context);
        tumosonov.setText(text);
        tumosonov.setDuration(duration);
        tumosonov.setBackgroundResource(android.R.drawable.toast_frame);
        return tumosonov;

    }

    public static WithActToast makeText(Context context, CharSequence text, int duration, Appearance appearance) {
        final WithActToast tomosonov = new WithActToast(context);
        appearance.apply(tomosonov);
        tomosonov.setText(text);
        tomosonov.setDuration(duration);
        tomosonov.setBackgroundResource(android.R.drawable.toast_frame);
        return tomosonov;

    }
}
