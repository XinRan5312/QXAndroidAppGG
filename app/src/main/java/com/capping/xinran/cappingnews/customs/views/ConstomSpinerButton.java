package com.capping.xinran.cappingnews.customs.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.capping.xinran.cappingnews.R;

/**
 * Created by houqixin on 2016/11/1.
 * 模拟Spinner控件下拉选择框的功能自定义一个类似功能的控件：利用Popupwindow来实现
 */
public class ConstomSpinerButton extends LinearLayout {

    private Context mContext;
    /**
     * 下拉PopupWindow
     */
    private UMSpinnerDropDownItems mPopupWindow;
    /**
     * 下拉布局文件ResourceId
     */
    private int mResId;
    /**
     * 下拉布局文件创建监听器
     */
    private ViewCreatedListener mViewCreatedListener;
    public ConstomSpinerButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initButton(context);
    }

    public ConstomSpinerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initButton(context);
    }

    public ConstomSpinerButton(Context context, final int resourceId,
                               ViewCreatedListener mViewCreatedListener) {
        super(context);
        setResIdAndViewCreatedListener(resourceId, mViewCreatedListener);
        initButton(context);
    }

    private void initButton(Context context) {
        this.mContext = context;
        // UMSpinnerButton监听事件
        setOnClickListener(new UMSpinnerButtonOnClickListener());
        Log.e("ConstomSpinerButton", "initButton");
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    public void setPopupWindow(UMSpinnerDropDownItems mPopupWindow) {
        this.mPopupWindow = mPopupWindow;
    }

    public int getResId() {
        return mResId;
    }

    /**
     * @Description: TODO   隐藏下拉布局
     */
    public void dismiss() {
        mPopupWindow.dismiss();
    }

    /**
     * @param @param mResId 下拉布局文件ID
     * @param @param mViewCreatedListener  布局文件创建监听器
     * @Description: TODO  设置下拉布局文件,及布局文件创建监听器
     */
    public void setResIdAndViewCreatedListener(int mResId, ViewCreatedListener mViewCreatedListener) {
        this.mViewCreatedListener = mViewCreatedListener;
        // 下拉布局文件id
        this.mResId = mResId;
        // 初始化PopupWindow
        mPopupWindow = new UMSpinnerDropDownItems(mContext);
    }

    public void startAnimation(final int type) {
        final ImageView img = (ImageView) findViewById(R.id.spinner_img);
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 180f,也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(img, "rotation", 0f, 180f);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (type == 0) {
                    img.setBackgroundResource(R.drawable.ic_spiner_up);
                } else if (type == 1) {
                    img.setBackgroundResource(R.drawable.ic_fund_spinder);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        // 动画的持续时间，执行多久？
        anim.setDuration(500);
        anim.start();
    }

    public void setText(String text) {
        TextView tv = (TextView) findViewById(R.id.spinner_text);
        tv.setText(text);
    }

    /**
     * UMSpinnerButton的点击事件
     */
    class UMSpinnerButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.e("ConstomSpinerButton", "UMSpinnerButtonOnClickListener");
            if (mPopupWindow != null) {
                startAnimation(1);
                if (!mPopupWindow.isShowing()) {
                    // 设置PopupWindow弹出,退出样式
                    mPopupWindow.setAnimationStyle(R.style.Animation_dropdown);
//                    // 计算popupWindow下拉x轴的位置
//                    int lx = (ConstomSpinerButton.this.getWidth()
//                            - mPopupWindow.getmViewWidth() - 7) / 2;
                    mPopupWindow.showAtLocation(ConstomSpinerButton.this, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        }
    }

    /**
     * @ClassName UMSpinnerDropDownItems
     * @Description TODO 下拉界面
     */
    public class UMSpinnerDropDownItems extends PopupWindow {

        private Context mContext;
        /**
         * 下拉视图的宽度
         */
        private int mViewWidth;
        /**
         * 下拉视图的高度
         */
        private int mViewHeight;

        public UMSpinnerDropDownItems(Context context) {
            super(context);
            this.mContext = context;
            loadViews();
        }

        /**
         * @param
         * @return void
         * @throws
         * @Description: TODO 加载布局文件
         */
        private void loadViews() {
            // 布局加载器加载布局文件
            LayoutInflater inflater = LayoutInflater.from(mContext);
            final View v = inflater.inflate(mResId, null);
            // 计算view宽高
            onMeasured(v);

            // 必须设置
            setWidth(500);
            setHeight(LayoutParams.WRAP_CONTENT);
            setContentView(v);
            setFocusable(true);

            // 设置布局创建监听器，以便在实例化布局控件对象
            if (mViewCreatedListener != null) {
                mViewCreatedListener.onViewCreated(v);
            }
        }

        /**
         * @param @param v
         * @Description: TODO 计算View长宽
         */
        private void onMeasured(View v) {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.EXACTLY);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            v.measure(w, h);
            mViewWidth = v.getMeasuredWidth();
            mViewHeight = v.getMeasuredHeight();
        }

        public int getmViewWidth() {
            return mViewWidth;
        }

        public void setmViewWidth(int mViewWidth) {
            this.mViewWidth = mViewWidth;
        }

        public int getmViewHeight() {
            return mViewHeight;
        }

        public void setmViewHeight(int mViewHeight) {
            this.mViewHeight = mViewHeight;
        }

    }
    /**
     * @ClassName ViewCreatedListener
     * @Description TODO  布局创建监听器，实例化布局控件对象
     */
    public interface ViewCreatedListener {
        void onViewCreated(View v);
    }

}
