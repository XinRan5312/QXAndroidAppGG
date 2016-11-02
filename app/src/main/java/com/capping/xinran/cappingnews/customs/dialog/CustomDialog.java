package com.capping.xinran.cappingnews.customs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;


import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.capping.xinran.cappingnews.R;

/**
 * Created by houqixin on 2016/11/2.
 */
public class CustomDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_fragment_dialog, container);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog =getDialog();//获取Dialog
        WindowManager.LayoutParams attr = dialog.getWindow().getAttributes();//获取Dialog属性
        WindowManager wm= (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetric=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetric);
        attr.width= (int) (outMetric.widthPixels*0.618f);//设置dialog的宽度是屏幕的0.618倍
        dialog.getWindow().setAttributes(attr);
    }
}
