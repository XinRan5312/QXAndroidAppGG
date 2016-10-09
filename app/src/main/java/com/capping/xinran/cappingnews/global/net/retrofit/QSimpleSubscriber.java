package com.capping.xinran.cappingnews.global.net.retrofit;

import android.content.Context;
import android.util.Log;

import com.capping.xinran.cappingnews.global.BaseDialogHelper;
import com.capping.xinran.cappingnews.global.DialogHelper;

/**
 * Created by qixinh on 16/9/22.
 */
public abstract class QSimpleSubscriber<T> extends BaseSubscriber<T> {
 private Context context;

    public QSimpleSubscriber(Context context) {
        this.context = context;
    }
    BaseDialogHelper dialogHelper;
    @Override
    public void onStart() {
        super.onStart();
        dialogHelper=new DialogHelper(context);
                dialogHelper.showProgressDlg("");
        Log.e("Franky", "onStart");
    }

    @Override
    public void onCompleted() {
          dialogHelper.dismissProgressDlg();
    }

    @Override
    protected void onError(ApiException ex) {
           dialogHelper.dismissProgressDlg();
           dialogHelper.showDialog("温馨提示", ex.message);
           dialogHelper.dismissProgressDlg();
        Log.e("Franky", "onError:"+ex.message);
    }

    @Override
    public void onNext(T t) {
          onNextDo(t);
    }

    protected abstract void onNextDo(T t);
}

