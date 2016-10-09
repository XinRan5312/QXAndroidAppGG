package com.capping.xinran.cappingnews.global.net.asynchttp;

import com.capping.xinran.cappingnews.global.net.BaseResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Gson解析工具类
 * Created by qixinh on 16/9/29.
 */
public class GsonAnalysis {

    public <E extends BaseResult> List<E> getListBeanFromResp(JSONObject response, String listkey) throws JSONException {
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<ArrayList<E>>() {
        }.getType();
        return new Gson().fromJson(response.getJSONArray(listkey).toString(), type);
    }

    public <E extends BaseResult> E getBeanFromResp(JSONObject response, String listkey) throws JSONException {
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<E>() {
        }.getType();
        return new Gson().fromJson(response.getJSONArray(listkey).toString(), type);
    }
}
