package com.candychat.request;

import android.content.Context;
import android.text.TextUtils;


import com.candychat.CandyApplication;
import com.candychat.DateSettings;
import com.candychat.utils.Utils;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DateService implements IDateService {
    public static final int LIKE = 0;
    public static final int REFUSE = 1;

    private DateHttpClient mClient;

    public DateService(Context context) {
        mClient = new VollyHttpClient(context);
    }

    private Map<String, Object> getParamsfillBase() {
        Map<String, Object> params = new HashMap<>();
        filleBaseParams(params);
        return params;
    }

    private Map<String, Object> getParamsfillLessBase() {
        Map<String, Object> params = new HashMap<>();
        filleLessBaseParams(params);
        return params;
    }


//    @Override
//    public void login(String userId, String userToken, DateResponseListener<LoginResponse> listener) throws JSONException {
//        Map<String, Object> params = getParamsfillBase();
//        if (!TextUtils.isEmpty(userId)) {
//            params.put("userid", userId);
//        }
//        if (!TextUtils.isEmpty(userToken)) {
//            params.put("usertoken", userToken);
//        }
//        params.put("icloudtoken", DateSettings.GOOGLE_ACCOUNT);
//        mClient.sendRequest(RequestUrls.LOGIN, params, listener, LoginResponse.class);
//    }
//
//    @Override
//    public void registe(int gender, String gcmToken, double longitude, double latitude, int target, File iconFile, DateResponseListener<RegisteResponse> responseListener) throws JSONException {
//        Map<String, Object> params = getParamsfillBase();
//        params.put("lat", latitude);
//        params.put("lon", longitude);
//        params.put("gender", gender);
//        params.put("pushtoken", gcmToken);
//        params.put("isfee", DateSettings.IS_FREE);
//        params.put("icloudtoken", DateSettings.GOOGLE_ACCOUNT);
//        params.put("target", target);
//        mClient.upload(RequestUrls.REGISTER, params, iconFile, responseListener, RegisteResponse.class);
//    }
//
//
//    @Override
//    public void getPopularUsers(int userid, int pageNo, int pageSize, int gender, DateResponseListener<PopularUserResponse> listener) throws JSONException {
//        Map<String, Object> params = new HashMap<>();
//        params.put("userid", userid);
//        params.put("pageNo", pageNo);
//        params.put("pageSize", pageSize);
//        params.put("gender", gender);
//        String url = RequestUrls.POPULAR_URL_BASE+ Utils.getVersion(CandyApplication.getContext()) + RequestUrls.POPULAR_URL;
//        mClient.sendRequestGET(url,params,listener,PopularUserResponse.class);
//    }
//
//    @Override
//    public void verifyOncePurchase(AndroidVipPayDto androidVipPayDto, DateResponseListener<VerifyOncePurchaseResponse> listener) throws JSONException {
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("appid", DateSettings.APPID);
//        params.put("channel", 0);
//        params.put("plat", DateSettings.PLATFORM);
//        params.put("var", DateSettings.VER);
//        params.put("signature", androidVipPayDto.signature);
//        params.put("signedData", androidVipPayDto.signedData);
//        String url = RequestUrls.POPULAR_URL_BASE+ Utils.getVersion(CandyApplication.getContext()) + RequestUrls.VERIFY_ONCE_PURCHASE;
//        mClient.sendRequest(url,params,listener,VerifyOncePurchaseResponse.class);
//    }

    @Override
    public void release() {
        mClient.stop();
    }

    private void filleBaseParams(Map<String, Object> params) {
        params.put("countryid", DateSettings.COUNTRY);
        params.put("timezone", 8);
        params.put("language", DateSettings.LANGUAGE);
        params.put("deviceid", DateSettings.DEVICEID);
        filleLessBaseParams(params);
    }

    private void filleLessBaseParams(Map<String, Object> params) {
        params.put("plat", DateSettings.PLATFORM);
        params.put("appid", DateSettings.APPID);
        params.put("ver", DateSettings.VER);
    }

}

