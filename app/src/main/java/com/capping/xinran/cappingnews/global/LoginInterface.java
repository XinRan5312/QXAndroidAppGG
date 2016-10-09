package com.capping.xinran.cappingnews.global;

import android.os.Handler;

/**
 * Created by qixinh on 16/9/19.
 */
public interface LoginInterface {
    public static final int CLIENT_UNLOGIN = -1;
    public static final int CLIENT_LOGIN_SUCCESS = 1;
    public static final int CLIENT_LOGIN_PROCESSING = 2;

    void setClientLoginStatus(int status);

    int getClientLoginStatus();

    Handler getHandler();

    String getNetworkInfo();
}
