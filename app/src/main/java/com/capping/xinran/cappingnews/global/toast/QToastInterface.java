package com.capping.xinran.cappingnews.global.toast;

import android.graphics.drawable.Drawable;

/**
 * Created by qixinh on 16/9/19.
 */
public interface QToastInterface {
    public static final long DURATION_SHORT = 2000;
    public static final long DURATION_MEDIUM = 2750;
    public static final long DURATION_LONG = 3500;
    public static final long DURATION_XLONG = 4500;
    public static final long DURATION_INFINITE = -1;

    void setText(CharSequence text);

    void setTextColor(int textColor);

    void setDuration(long duration);

    void setGravity(int gravity);

    void setBackgroundResource(int res);

    void setBackgroundDrawable(Drawable backgroundDrawable);

    /**
     * set text size in dip
     *
     * @param textSize
     */
    void setTextSize(int textSize);

    void setXYCoordinates(int xOffset, int yOffset);

    void show();

}
