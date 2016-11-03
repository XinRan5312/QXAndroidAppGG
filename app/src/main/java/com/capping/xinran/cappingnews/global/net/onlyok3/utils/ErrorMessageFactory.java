package com.capping.xinran.cappingnews.global.net.onlyok3.utils;

import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.global.EngloryApplication;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * 错误信息提示
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
        //empty
    }

    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }

    public static String createMessage(Exception e) {
        if (e instanceof SocketTimeoutException) {
            CustomToast.showToast("网络连接失败,请检查网络");//change
            return EngloryApplication.getAppContext().getString(R.string.network_connection_failed);
        } else if (e instanceof ConnectException) {
            return EngloryApplication.getAppContext().getString(R.string.network_not_connected);
        }
        return EngloryApplication.getAppContext().getString(R.string.operation_failed);
    }
}
