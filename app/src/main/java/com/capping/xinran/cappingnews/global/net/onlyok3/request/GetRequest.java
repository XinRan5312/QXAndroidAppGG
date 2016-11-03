package com.capping.xinran.cappingnews.global.net.onlyok3.request;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

public class GetRequest extends OkHttpRequest {

    public GetRequest(String url, Object tag, Map<String, Object> params, Map<String, String> headers,int id) {
        super(url, tag, params, headers,id);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.get().build();
    }


}
