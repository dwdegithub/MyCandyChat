package com.candychat.request;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.candychat.Constants;
import com.candychat.response.DateResponse;
import com.candychat.response.DateResponseListener;


import org.json.JSONException;

import java.io.File;
import java.util.Map;

/**
 * Created by ZN_mager on 2016/5/9.
 */
public class VollyHttpClient implements DateHttpClient {

    private static final long TIMEOUT = 30 * Constants.ONE_SECOND;
    private RequestQueue mRequestQueue;

    public VollyHttpClient(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }


    @Override
    public <T extends DateResponse> void sendRequest(String url, Map<String, Object> params, DateResponseListener<T> listener, Class<T> tClass) throws JSONException {
        DateRequest<T> request = new DateRequest<>(url, params, listener, tClass);
        sendRequest(request);
    }


    public <T extends DateResponse> void sendRequestGET(String url, Map<String, Object> params, DateResponseListener<T> listener, Class<T> tClass) throws JSONException {
        DateRequest<T> request = new DateRequest<>(Request.Method.GET, url, params, listener, tClass);
        sendRequest(request);
    }

    private void sendRequest(Request request) {
        request.setTag(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                (int) TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);
    }

    @Override
    public <T extends DateResponse> void sendRequest(String url, Map<String, Object> params, DateResponseListener<T> listener, Class<T> tClass, boolean encode) throws JSONException {
        DateRequest<T> request = new DateRequest<>(url, params, listener, tClass, encode);
        sendRequest(request);

    }

    @Override
    public <T extends DateResponse> void upload(String url, Map<String, Object> params, File fileUpload, DateResponseListener<T> listener, Class<T> tClass) throws JSONException {
        DateMultiPartRequest<T> request = new DateMultiPartRequest<>(url, params, fileUpload, listener, tClass);
        sendRequest(request);

    }

    @Override
    public void stop() {
        mRequestQueue.cancelAll(this);
    }
}
