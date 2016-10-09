package com.capping.xinran.cappingnews.global.net.retrofit;

/**
 * Created by qixinh on 16/9/22.
 */
public class ApiException extends Exception {
    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;

    }
}
