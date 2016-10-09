package com.capping.xinran.cappingnews.global.net.retrofit;

/**
 * Created by qixinh on 16/9/22.
 */
public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(int code,String message) {
        super(message);
        this.code = code;

    }

}
