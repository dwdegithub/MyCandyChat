package com.candychat;

import android.content.Context;
import android.text.TextUtils;

import com.candychat.utils.Utils;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by ZN_mager on 2016/5/3.
 */
public class DateSettings {

    public static final int MAX_IMAGE_SIZE = 1080;

    public static final int NEARBY_USER_COUNT = 50;
    public static final int NEARBY_USER_MIN_COUNT = 45;

    public static final String COUNTRY = Locale.getDefault().getCountry();
    public static final String LANGUAGE = Locale.getDefault().getLanguage();

    public static final String TEMP_ACCOUNT = "zhaonan303@gmail.com";
    public static final String TEMP_DEVICEID = "00000000000000000000000303";

    public static final int IS_FREE = 0;
    public static String APPLICATION_TOKEN;
    public static String TIMEZONE = Utils.getTimeZone(CandyApplication.getContext());
    public static final int APPID = 10000;
    public static String GOOGLE_ACCOUNT;
    public static final int PLATFORM = 2;
    public static String DEVICEID;
    public static String VER;

    public static final float ONE_MONTH_PRICE = 29.99f;
    public static final float THREE_MONTH_PRICE = 49.99f;
    public static final float SIX_MONTH_PRICE = 59.99f;

    public static void init(Context context) {
        generateApplicationToken(context);
        DateSettings.TIMEZONE = Utils.getTimeZone(context);
        DateSettings.DEVICEID = Utils.getDeviceId(context);
        VER = Utils.getVer(context)+"";
        if (TextUtils.isEmpty(DateSettings.DEVICEID)) {
            DateSettings.DEVICEID = DateSettings.APPLICATION_TOKEN;
        }
        GOOGLE_ACCOUNT = Utils.getGooleAccount(context);
        if (TextUtils.isEmpty(GOOGLE_ACCOUNT)) {
            GOOGLE_ACCOUNT = DEVICEID;
        }


//        DEVICEID = DEVICEID+"aw117";
//        GOOGLE_ACCOUNT = "temp"+DEVICEID;
//        GOOGLE_ACCOUNT = TEMP_ACCOUNT;
    }

    private static void generateApplicationToken(Context context) {
        String tokenCached = CandyPreference.getApplicationToken();
        if (TextUtils.isEmpty(tokenCached)) {
            String token = UUID.randomUUID().toString();
            CandyPreference.setApplicationToken(token);
            tokenCached = token;
        }
        DateSettings.APPLICATION_TOKEN = tokenCached;
    }

//
//    public class Actions {
//        public static final String ACTION_NEARBY_USER_LOGIN = "com.sweetjelly.wink.ACTION_NEARBY_USER_LOGIN";
//    }

}

