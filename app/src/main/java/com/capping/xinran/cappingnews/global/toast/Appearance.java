package com.capping.xinran.cappingnews.global.toast;

import android.content.Context;
import android.view.Gravity;

import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.global.EngloryApplication;

/**
 * Created by qixinh on 16/9/19.
 */
public class Appearance {
    public static final Appearance DEFAULT_BOTTOM;
    public static final Appearance DEFAULT_TOP;
    public static final Appearance BOTTOM_LONG;

    static {
        DEFAULT_BOTTOM = new Builder(EngloryApplication.getAppContext()).build();
        BOTTOM_LONG = new Builder(EngloryApplication.getAppContext()).setDuration(QToastInterface.DURATION_LONG).build();
        DEFAULT_TOP = new Builder(EngloryApplication.getAppContext()).setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL).build();
    }

    final long duration;
    final int backgroundResourceId;
    final int textColor;
    final int yOffset;
    final int xOffset;
    final int gravity;
    /**
     * The text size in dp
     * <p/>
     * 0 sets the text size to the system theme default
     */
    final int textSize;

    private Appearance(final Builder builder) {
        this.duration = builder.duration;
        this.backgroundResourceId = builder.backgroundResourceId;
        this.textColor = builder.textColor;
        this.yOffset = builder.yOffset;
        this.xOffset = builder.xOffset;
        this.gravity = builder.gravity;
        this.textSize = builder.textSize;
    }

    public QToast createTuski(Context context) {
        QToast tuski = new QToast(context);
        apply(tuski);
        return tuski;
    }

    public WithActToast createTumosonov(Context context) {
        WithActToast tumosonov = new WithActToast(context);
        apply(tumosonov);
        return tumosonov;
    }

    public void apply(QToastInterface tuski) {
        tuski.setDuration(duration);
        tuski.setBackgroundResource(backgroundResourceId);
        tuski.setTextColor(textColor);
        tuski.setXYCoordinates(xOffset, yOffset);
        tuski.setGravity(gravity);
        tuski.setTextSize(textSize);
    }

    /**
     * Builder for the {@link Appearance} object.
     */
    public static class Builder {
        private long duration;
        private int backgroundResourceId;
        private int textColor;
        private int yOffset;
        private int xOffset;
        private int gravity;
        private int textSize;
        private final Context mContext;

        public Builder(Context context) {
            this.mContext = context;
            duration = QToastInterface.DURATION_SHORT;
            backgroundResourceId = android.R.drawable.toast_frame;
            textColor = mContext.getResources().getColor(android.R.color.white);
            yOffset = context.getResources().getDimensionPixelSize(R.dimen.toast_yoffset);
            xOffset = 0;
            gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
            textSize = 12;

        }

        public Builder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        public Builder setBackgroundResource(int backgroundDrawableResourceId) {
            this.backgroundResourceId = backgroundDrawableResourceId;
            return this;
        }

        public Builder setYOffset(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }

        public Builder setXOffset(int xOffset) {
            this.xOffset = xOffset;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setTextColorResource(int textColor) {
            this.textColor = mContext.getResources().getColor(textColor);
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * The text size in dp
         */
        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Appearance build() {
            return new Appearance(this);
        }
    }
}
