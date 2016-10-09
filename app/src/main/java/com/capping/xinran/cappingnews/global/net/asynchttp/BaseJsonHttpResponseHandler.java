package com.capping.xinran.cappingnews.global.net.asynchttp;

import android.os.Message;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

/**
 * JsonHttpResponseHandler模板类
 * Created by qixinh on 16/9/29.
 */
public class BaseJsonHttpResponseHandler extends JsonHttpResponseHandler {
    public final static String TAG=BaseJsonHttpResponseHandler.class.getSimpleName();
    /**
     * Creates new JsonHttpResponseHandler, with JSON String encoding UTF-8
     */
    public BaseJsonHttpResponseHandler() {
        super();
    }
    /**
     * Creates new JsonHttpResponseHandler with given JSON String encoding
     *
     * @param encoding String encoding to be used when parsing JSON
     */
    public BaseJsonHttpResponseHandler(String encoding) {
        super(encoding);
    }
    /**
     * Creates new JsonHttpResponseHandler with JSON String encoding UTF-8 and given RFC5179CompatibilityMode
     *
     * @param useRFC5179CompatibilityMode Boolean mode to use RFC5179 or latest
     */
    public BaseJsonHttpResponseHandler(boolean useRFC5179CompatibilityMode) {
        super(useRFC5179CompatibilityMode);
    }
    /**
     * Creates new JsonHttpResponseHandler with given JSON String encoding and RFC5179CompatibilityMode
     *
     * @param encoding                    String encoding to be used when parsing JSON
     * @param useRFC5179CompatibilityMode Boolean mode to use RFC5179 or latest
     */
    public BaseJsonHttpResponseHandler(String encoding, boolean useRFC5179CompatibilityMode) {
        super(encoding, useRFC5179CompatibilityMode);
    }
    /**
     * Returns when request succeeds
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     * @param response   parsed response if any
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
    }
    /**
     * Returns when request failed
     *
     * @param statusCode    http response status line
     * @param headers       response headers if any
     * @param throwable     throwable describing the way request failed
     * @param errorResponse parsed response if any
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        super.onSuccess(statusCode, headers, responseString);
    }

    @Override
    protected Object parseResponse(byte[] responseBody) throws JSONException {
        return super.parseResponse(responseBody);
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        super.onProgress(bytesWritten, totalSize);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    public void onPreProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
        super.onPreProcessResponse(instance, response);
    }

    @Override
    public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
        super.onPostProcessResponse(instance, response);
    }

    @Override
    public void onRetry(int retryNo) {
        super.onRetry(retryNo);
    }

    @Override
    public void onCancel() {
        super.onCancel();
    }

    @Override
    public void onUserException(Throwable error) {
        super.onUserException(error);
    }

    @Override
    protected void handleMessage(Message message) {
        super.handleMessage(message);
    }

    @Override
    protected void sendMessage(Message msg) {
        super.sendMessage(msg);
    }

    @Override
    protected void postRunnable(Runnable runnable) {
        super.postRunnable(runnable);
    }

    @Override
    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return super.obtainMessage(responseMessageId, responseMessageData);
    }

    @Override
    public void sendResponseMessage(HttpResponse response) throws IOException {
        super.sendResponseMessage(response);
    }
}
