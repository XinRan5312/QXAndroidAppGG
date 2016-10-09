package com.capping.xinran.cappingnews.global;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;


/**
 * Created by qixinh on 16/9/22.
 */
public abstract class BaseDialogHelper {
    protected  Dialog mDialog;
    protected ProgressDialog progressDlg = null;
    protected Context context;

    public BaseDialogHelper(Context context) {
        this.context = context;
    }

    public interface OnOkClickListener {
        abstract void onOkClick();
    }

    public interface OnCancelClickListener {
        abstract void onCancelClick();
    }

    public abstract void showDialog(String title,String msg);
    public void showDialog(String title,String content,final OnOkClickListener listenerYes,
                                       final OnCancelClickListener listenerNo){
        showDialog(context.getString(android.R.string.ok), context.getString(android.R.string.cancel), title, content, listenerYes, listenerNo);
    }

    public abstract void showDialog(String ok, String cancel, String title, String content, final OnOkClickListener listenerYes,
                                  final OnCancelClickListener listenerNo);

    public  void showDialog(int ok, int cancel, int title, int content, final OnOkClickListener listenerYes,
                                  final OnCancelClickListener listenerNo) {
        showDialog(context.getString(ok), context.getString(cancel), context.getString(title), context.getString(content), listenerYes, listenerNo);
    }
    public abstract void showProgressDlg(String strMessage);

    public abstract void dismissProgressDlg();
    public abstract void dismissDilog();

    public abstract void showDialogForLoading(String msg, boolean cancelable);
}
