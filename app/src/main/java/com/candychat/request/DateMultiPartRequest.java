package com.candychat.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.candychat.response.DateResponse;
import com.candychat.response.DateResponseListener;
import com.candychat.utils.LogUtils;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZN_mager on 2016/5/11.
 */
public class DateMultiPartRequest<T extends DateResponse> extends Request<T> {
    private static final String TAG = "MultipartRequest";
    private DateResponseListener<T> mListener;
    private File mFileUpload;
    private Class<T> mTClass;
    private String mRequestUrl;
    private Map<String, Object> mRequestParams = new HashMap<>();
    private String mTextBody;
    private HttpEntity mHttpEntity;

    public DateMultiPartRequest(String url, Map<String, Object> params, File fileUpload, DateResponseListener<T> listener, Class<T> tClass) throws JSONException {
        super(Method.POST, url, listener);
        mTextBody = DateRequest.buildParamsValueEncoded(params,true);
        mListener = listener;
        mFileUpload = fileUpload;
        mTClass = tClass;
        mRequestParams.putAll(params);
        mRequestUrl = url;
        mHttpEntity = buildHttpEntity();
    }

    private HttpEntity buildHttpEntity() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//        entity.addPart("picfile", new FileBody(mFileUpload));
//        entity.addPart("json", new StringBody(mTextBody, ContentType.APPLICATION_JSON));
//        builder.addBinaryBody("picfile", mFileUpload, ContentType.create("image/jpeg"), mFileUpload.getName());
        builder.addPart("picfile", new FileBody(mFileUpload));
//        FormBodyPart jsonBody = new FormBodyPart("json",new StringBody(mTextBody,ContentType.APPLICATION_JSON));
        builder.addPart("parm", new StringBody(mTextBody, ContentType.APPLICATION_JSON));
        return builder.build();
//        return entity;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String resData = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            LogUtils.e(TAG, resData);
            T t = mTClass.getConstructor(String.class, Map.class, String.class).newInstance(mRequestUrl, mRequestParams, resData);
            t.analyzeResponse();
            return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public String getBodyContentType() {
        String type = mHttpEntity.getContentType().getValue();
        return type;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
}
