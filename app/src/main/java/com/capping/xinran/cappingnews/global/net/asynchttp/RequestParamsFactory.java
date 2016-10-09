package com.capping.xinran.cappingnews.global.net.asynchttp;

import com.capping.xinran.cappingnews.global.net.BaseRequestParams;

/**
 * Created by qixinh on 16/9/29.
 */
public class RequestParamsFactory {

    public static BaseRequestParams getTrendingNewsListParams(final boolean isUpQuery, final int sort) {
        BaseRequestParams params = new BaseRequestParams();
        params.put("sort", sort);
        params.put("isupquery", isUpQuery ? 1 : 0);
            /*com.me.topnews.constant.Constants.MAINNEWSCOUNT*/
        params.put("count", 10);
        return params;
    }
}
