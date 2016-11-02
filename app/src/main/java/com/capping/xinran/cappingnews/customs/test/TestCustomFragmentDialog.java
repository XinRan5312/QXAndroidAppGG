package com.capping.xinran.cappingnews.customs.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.View;

import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.base.BaseActivity;
import com.capping.xinran.cappingnews.customs.dialog.CustomDialog;

/**
 * Created by houqixin on 2016/11/2.
 */
public class TestCustomFragmentDialog extends BaseActivity {
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFragmentDialog();
    }

    public void showFragmentDialog(){
        //防止旋转屏幕时发生窗体泄露
        if(dialog!=null&&dialog.getDialog()!=null&&dialog.getDialog().isShowing()){
            dialog.dismiss();
            return;
        }
        dialog=new CustomDialog();
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.loading_dialog);
        dialog.show(getSupportFragmentManager(),"dialog");
    }

    @Override
    protected void initData() {

    }
}
