package com.candychat.request;


import com.candychat.response.DateResponse;
import com.candychat.response.DateResponseListener;

import org.json.JSONException;

import java.io.File;
import java.util.Map;

public interface DateHttpClient {

    <T extends DateResponse> void sendRequest(String url, Map<String, Object> params, DateResponseListener<T> listener, Class<T> tClass) throws JSONException;

    <T extends DateResponse> void sendRequestGET(String url, Map<String, Object> params, DateResponseListener<T> listener, Class<T> tClass) throws JSONException;

    <T extends DateResponse> void sendRequest(String url, Map<String, Object> params, DateResponseListener<T> listener, Class<T> tClass, boolean encode) throws JSONException;

    <T extends DateResponse> void upload(String url, Map<String, Object> params, File fileUpload, DateResponseListener<T> listener, Class<T> tClass) throws JSONException;

    void stop();

}
