package com.capping.xinran.cappingnews.global.net.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.capping.xinran.cappingnews.global.GlobalContants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qixinh on 16/9/22.
 */
public class AddHeaderAndCookieInterceptor implements Interceptor {
    private Context context;

    public AddHeaderAndCookieInterceptor(Context context) {
        super();
        this.context = context;

    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder=chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalContants.ENGLORY_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        String cookies=sharedPreferences.getString(GlobalContants.ENGLORY_COOKIE_KEY,"");
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("User-Agent", "")
                .addHeader("Set_Cookie", cookies);

        return chain.proceed(builder.build());
    }
}
