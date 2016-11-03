package com.capping.xinran.cappingnews.global.net.onlyok3.callback;



import com.capping.xinran.cappingnews.global.net.onlyok3.utils.Tools;

import java.io.IOException;

import okhttp3.Response;

public abstract class StringCallback extends Callback<String> {

    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        String str = response.body().string();
        Tools.debugger("StringCallback", "parseNetworkResponse : " + str);
        return str;
    }
}
