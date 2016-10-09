package com.capping.xinran.cappingnews.global;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by qixinh on 16/9/22.
 */
public class DialogHelper extends BaseDialogHelper {


    public DialogHelper(Context context) {
        super(context);
    }

    @Override
    public void showDialog(String title, String msg) {

    }

    @Override
    public void showDialog(String ok, String cancel, String title, String content, final OnOkClickListener listenerYes, final OnCancelClickListener listenerNo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(content);
        // 设置title
        builder.setTitle(title);
        // 设置确定按钮，固定用法声明一个按钮用这个setPositiveButton
        builder.setPositiveButton(ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 如果确定被电击
                        if (listenerYes != null) {
                            listenerYes.onOkClick();
                        }
                        mDialog = null;
                    }
                });
        // 设置取消按钮，固定用法声明第二个按钮要用setNegativeButton
        builder.setNegativeButton(cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 如果取消被点击
                        if (listenerNo != null) {
                            listenerNo.onCancelClick();
                        }
                        mDialog = null;
                    }
                });

        // 控制这个dialog可不可以按返回键，true为可以，false为不可以
        builder.setCancelable(false);
        // 显示dialog
        mDialog = builder.create();
        if (!mDialog.isShowing())
            mDialog.show();
    }

    @Override
    public void showProgressDlg(String strMessage) {
        if (null == progressDlg) {
            progressDlg = new ProgressDialog(context);
            //设置进度条样式
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //提示的消息
            progressDlg.setMessage(strMessage);
            progressDlg.setIndeterminate(false);
            progressDlg.setCancelable(true);
            progressDlg.show();
        }
    }

    @Override
    public void dismissProgressDlg() {
        if (null != progressDlg && progressDlg.isShowing()) {
            progressDlg.dismiss();
            progressDlg = null;
        }
    }

    @Override
    public void dismissDilog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void showDialogForLoading(String msg, boolean cancelable) {

    }

}