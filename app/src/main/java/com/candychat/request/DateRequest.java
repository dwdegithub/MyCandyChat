package com.candychat.request;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.candychat.response.DateResponse;
import com.candychat.response.DateResponseListener;
import com.candychat.utils.DesUtil;
import com.candychat.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DateRequest<T extends DateResponse> extends JsonRequest<T> {
    private static final String TAG = "Request";
    private String mRequestUrl;
    private Class<T> mTClass;
    private Map<String, Object> mRequestParams = new HashMap<>();

    public DateRequest(String url, Map<String, Object> params, @NonNull DateResponseListener<T> listener, Class<T> tClass) throws JSONException {
        super(Method.POST, url, buildRequestBody(params, true), listener, listener);
        mRequestParams.putAll(params);
        mRequestUrl = url;
        this.mTClass = tClass;
    }

    public DateRequest(int method, String url, Map<String, Object> params, @NonNull DateResponseListener<T> listener, Class<T> tClass) throws JSONException {
        super(Method.GET, url+buildRequestGETBody(params), null, listener, listener);
        mRequestParams.putAll(params);
        mRequestUrl = url;
        this.mTClass = tClass;
    }

    public DateRequest(String url, Map<String, Object> params, @NonNull DateResponseListener<T> listener, Class<T> tClass, boolean encoded) throws JSONException {
        super(Method.POST, url, buildRequestBody(params, encoded), listener, listener);
        mRequestParams.putAll(params);
        mRequestUrl = url;
        this.mTClass = tClass;
    }

    private static String buildRequestGETBody(Map<String, Object> params) {
        StringBuilder result = new StringBuilder();
        result.append("?");
        for (String key : params.keySet()) {
            result.append(key);
            result.append("=");
            result.append(params.get(key));
            result.append("&");
        }
        String date = result.toString();
        date = date.subSequence(0, date.length()-1).toString();
        return date;
    }

    public static String buildRequestBody(Map<String, Object> params, boolean encode) throws JSONException {
        String stringEntity;
        String paramBody = buildParamsValueEncoded(params, encode);
        if (encode) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("parm", paramBody);
            stringEntity = jsonObject.toString();
        } else {
            stringEntity = paramBody;
        }
        LogUtils.e(TAG," request body " + stringEntity);
        return stringEntity;
    }


    public static String buildParamsValueEncoded(Map<String, Object> params, boolean encode) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (String key : params.keySet()) {
            jsonObject.put(key, params.get(key));

        }
        String paramBody;
        if (encode) {
            paramBody = encodeParms(jsonObject.toString());
        } else {
            paramBody = jsonObject.toString();
        }
        return paramBody;
    }

    public static String encodeParms(String paramsBody) {
        LogUtils.e(TAG, paramsBody);
        byte des[] = DesUtil.encrypt(paramsBody.getBytes(), DesUtil.pass);
        byte base64[] = Base64.encode(des, Base64.DEFAULT);
        String base64String = new String(base64);
        return base64String.replaceAll("\n", "");
//        return  base64String;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String resData = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            LogUtils.e(TAG," parseNetworkResponse data "+ resData);
//            DateResponse res = DateResponseFactory.newResponse(mRequestUrl, resData);
            LogUtils.e(TAG, " parseNetworkResponse "+ mTClass.toString());

            T t = mTClass.getConstructor(String.class, Map.class, String.class).newInstance(mRequestUrl, mRequestParams, resData);
            t.analyzeResponse();
            return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
