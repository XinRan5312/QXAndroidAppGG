package com.capping.xinran.cappingnews.global.net.onlyok3.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.capping.xinran.cappingnews.global.EngloryApplication;


public class CustomToast {
	
	private static Toast mToast;
	private static final int duration = 500;
	private static Handler mHandler = new Handler(Looper.getMainLooper());
	private static Runnable runnable = new Runnable() {
		public void run() {
			mToast.show();
		}
	};

	public static void showToast(Context mContext, String text) {
		if (mToast != null) {
			mToast.setText(text);
		} else {
			mToast = Toast.makeText(EngloryApplication.getAppContext(), text,Toast.LENGTH_LONG);
		}
		mHandler.removeCallbacks(runnable);
		mHandler.postDelayed(runnable, duration);
	}

	/**引用Id*/
	public static void showToast(int resId) {
		showToast(EngloryApplication.getAppContext(), EngloryApplication.getAppContext().getString(resId));
	}

	/**String*/
	public static void showToast(String toast) {
		showToast(EngloryApplication.getAppContext(), toast);
	}

}