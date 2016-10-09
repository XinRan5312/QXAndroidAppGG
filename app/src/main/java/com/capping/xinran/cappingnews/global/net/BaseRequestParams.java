package com.capping.xinran.cappingnews.global.net;

import com.loopj.android.http.RequestParams;

/**
 * Created by qixinh on 16/9/29.
 */
public class BaseRequestParams extends RequestParams {
    private final String engloryRequest="EngloryReqAndroid";

    public String getEngloryRequest() {
        return engloryRequest;
    }
}
