package com.candychat.response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZN_mager on 2016/5/10.
 */
public abstract class DateResponse<T> {
    public static final int REQUEST_FAILED = -2;

    public static final int RESPONSE_DATA_ERROR = -1;
    public static final int STATE_SUCCESS = 10000;
    public static final int STATE_USER_REGISTED = 10003;
    public static final int STATE_PARAMS_ERROR = 10001;
    public static final int STATE_UNREGISTED = 10002;
    public static final int STATE_FREEZED = 10010;
    public static final int STATE_TOKEN_ERROR = 10011;
    public static final int STATE_USER_TOKEN_ERROR = 10011;
    public static final int STATE_SERVER_EXCEPTION = 20000;
    public static final int STATE_FREE_TYPE_ADDED = 10026;
    public static final int STATE_CHATROOM_DELETED = 10020;

    private static final String RESPONSE_KEY_STATE = "state";

    private String mUrl;
    private JSONObject mJsonResponse;
    private boolean isError;
    private int mState;
    private Map<String, Object> mParams = new HashMap<>();
    private Object mErrorData;

    /**
     * must keep two string construct implement
     *
     * @param url
     * @param response
     * @throws JSONException
     */
   public DateResponse(String url, Map<String, Object> params, String response) throws JSONException {
        this.mUrl = url;
        this.mParams.putAll(params);
        this.mJsonResponse = new JSONObject(response);
    }

    /**
     * will call by system donot call by yourself
     */
    public void analyzeResponse() {
        mState = mJsonResponse.optInt(RESPONSE_KEY_STATE, -1);
        if (mState == STATE_SUCCESS || mState == -1) {//新老接口 状态码不统一
            isError = false;
            try {
                onResponseStateSuccess(mJsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                isError = true;
                mState = RESPONSE_DATA_ERROR;
            }
        } else {
            if (onResponseStateError(mState, mJsonResponse)) {
                mErrorData = getErrorData(mJsonResponse, mState);
                isError = true;
            }
        }
    }

    /**
     * @param state
     * @param jsonResponse
     * @return error on special response
     */
    protected abstract boolean onResponseStateError(int state, JSONObject jsonResponse);

    protected abstract void onResponseStateSuccess(JSONObject jsonResponse);


    public abstract T getResponseObject();

    boolean isError() {
        return isError;
    }

    public int getResponseState() {
        return mState;
    }

    Map<String, Object> getRequestParams() {
        return mParams;
    }

    abstract Object getErrorData(JSONObject jsonResponse, int state);

    Object getErrorData() {
        return mErrorData;
    }
}
