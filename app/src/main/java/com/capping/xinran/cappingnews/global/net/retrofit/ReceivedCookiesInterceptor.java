package com.capping.xinran.cappingnews.global.net.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.capping.xinran.cappingnews.global.GlobalContants;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by qixinh on 16/9/22.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        List<String> list = originalResponse.headers("Set_Cookie");
        final StringBuilder sb = new StringBuilder();
        if (!list.isEmpty()) {
            for (String str : list) {
                sb.append(str).append(";");
            }
        }
        if (sb.length() > 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalContants.ENGLORY_SHAREDPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(GlobalContants.ENGLORY_COOKIE_KEY, sb.toString());
            editor.commit();
        }
        return originalResponse;
    }
}
