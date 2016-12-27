package com.candychat;

import android.content.Context;
import android.content.SharedPreferences;

import com.candychat.utils.JsonUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZN_mager on 2016/5/9.
 */
public class CandyPreference {


    private static final String PREF_NAME = "candy_pref";
    private static final String PREF_KEY_APP_TOKEN = "app_token";
    //user info keys
    private static final String PREF_KEY_CURRENTUSER = "currentUser";
    //freeze keys
    private static final String PREF_KEY_FREEZE_START_TIME = "freeze_start_time";
    private static final String PREF_KEY_FREEZE_END_TIME = "freeze_end_time";
//    private static final String PREF_KEY_UNFREEZ_TIME = "unfreeze_time";

    public static final String PREF_KEY_LAST_NOTIFICATION_TIMESTAMP = "last_notification_timestamp";
    public static final String PREF_KEY_LAST_MESSAGE_TIMESTAMP = "last_message_timestamp";
    public static final String PREF_KEY_SHOWN_MESSAGE_TIMESTAMP = "shown_last_message_timestamp";
    private static final String PREF_KEY_PAID = "paid";
    private static final String PREF_KEY_ACCEPT_TERM_OF_USE = "accept_term_of_use";

    public static final String PREF_KEY_SCORED = "scored";

    public static final String PREF_KEY_IS_SCORED_RATE = "is_matchscored_rate";

    public static final String PREF_KEY_IS_INITDATA = "is_init_data";

    public static final String PREF_KEY_SCORE_MATCH_FEEDBACK = "click_matchscored_freedback";

    public static final String PREF_KEY_SCORE_MATCH_NOTNOW = "click_matchscored_notnow";

    public static final String PREF_KEY_COUNT_RUN = "app_count_run";

    public static final String PREF_KEY_NOTNOW_COUNT_RUN = "app_notnow_count_run";

    public static final String PREF_KEY_WHO_LIKE_USER_IS_SHOW = "who_like_user_is_show";

    public static final String PERIOD_PUSH_TARGET_TIME = "PeriodPushTargetTime";

    public static SharedPreferences getSharedpreference() {
        return CandyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

                    public static void setApplicationToken(String token) {
                        getSharedpreference().edit().putString(PREF_KEY_APP_TOKEN, token).apply();
                    }

    public static String getApplicationToken() {
        return getSharedpreference().getString(PREF_KEY_APP_TOKEN, null);
    }


    public static boolean isInitData() {
        return getSharedpreference().getBoolean(PREF_KEY_IS_INITDATA, false);
    }

    public static void setInitData(boolean isInit) {
        getSharedpreference().edit().putBoolean(PREF_KEY_IS_INITDATA, isInit).apply();
    }

    public static void setCurrentUser(CurrentUser user) {
        SharedPreferences sharedPreferences = getSharedpreference();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY_CURRENTUSER, JsonUtils.parseBeanToJson(user).toString());
        editor.apply();
    }

    public static CurrentUser getCurrentUser() {
        CurrentUser user = null;
        SharedPreferences sharedPreferences = getSharedpreference();
        String userData = sharedPreferences.getString(PREF_KEY_CURRENTUSER, null);
        if (userData != null) {
            user = JsonUtils.parseJsonToBean(userData, CurrentUser.class);
        }
        return user;
    }

    public static void setMatchScoredRate(boolean isRate){
        getSharedpreference().edit().putBoolean(PREF_KEY_IS_SCORED_RATE, isRate).apply();
    }

    //是否rate评论 评分过
    public static boolean isScoreMatchRate() {
        return getSharedpreference().getBoolean(PREF_KEY_IS_SCORED_RATE, false);
    }


    public static void setScoreMatchfeedback(boolean isClickFreedback) {
        getSharedpreference().edit().putBoolean(PREF_KEY_SCORE_MATCH_FEEDBACK, isClickFreedback).apply();
    }
    //是否 feedback
    public static boolean isScoreMatchfeedback() {
        return getSharedpreference().getBoolean(PREF_KEY_SCORE_MATCH_FEEDBACK, false);
    }

    public static void setScoreMatchNotnow(boolean isClickNotNow) {
        getSharedpreference().edit().putBoolean(PREF_KEY_SCORE_MATCH_NOTNOW, isClickNotNow).apply();
    }
    //是否 notnow
    public static boolean isScoreMatchNotnow() {
        return getSharedpreference().getBoolean(PREF_KEY_SCORE_MATCH_NOTNOW, false);
    }

    public static int getCountRun(){
        return getSharedpreference().getInt(PREF_KEY_COUNT_RUN, 0);
    }

    public static void setCountRun(int count) {
        getSharedpreference().edit().putInt(PREF_KEY_COUNT_RUN, count).apply();
    }

    //点击  notnow后启动次数
    public static void setNotNowCountRun(int count) {
        getSharedpreference().edit().putInt(PREF_KEY_NOTNOW_COUNT_RUN, count).apply();
    }

    public static int getNotNowCountRun() {
        return getSharedpreference().getInt(PREF_KEY_NOTNOW_COUNT_RUN, 0);
    }

    public static boolean isAcceptTermOfUse() {
        return getSharedpreference().getBoolean(PREF_KEY_ACCEPT_TERM_OF_USE, false);
    }

    public static void setAcceptTermOfUse(boolean accepted) {
        getSharedpreference().edit().putBoolean(PREF_KEY_ACCEPT_TERM_OF_USE, accepted).apply();
    }


    public static void setIsShowUserSet(Set<String> userSet) {
        getSharedpreference().edit().putStringSet(PREF_KEY_WHO_LIKE_USER_IS_SHOW,userSet).apply();
    }

    public static Set<String> getIsShowUserSet() {
        return getSharedpreference().getStringSet(PREF_KEY_WHO_LIKE_USER_IS_SHOW, new HashSet<String>());
    }


    public static void setPeriodPushTargetTime(long time) {
        getSharedpreference().edit().putLong(PERIOD_PUSH_TARGET_TIME, time).apply();
    }

    public static long getPeriodPushTargetTime() {
        return getSharedpreference().getLong(PERIOD_PUSH_TARGET_TIME,0);
    }






    public static void setFreezeTime(long startTime, long endTime) {
        SharedPreferences sharedPreferences = getSharedpreference();
        sharedPreferences.edit().putLong(PREF_KEY_FREEZE_START_TIME, startTime).putLong(PREF_KEY_FREEZE_END_TIME, endTime).apply();
    }

    public static long[] getFreezeTime(Context context) {
        SharedPreferences sharedPreferences = getSharedpreference();
        return new long[]{sharedPreferences.getLong(PREF_KEY_FREEZE_START_TIME, 0), sharedPreferences.getLong(PREF_KEY_FREEZE_END_TIME, 0)};
    }


    public static long getLastShownNotificationTimeStamp() {
        SharedPreferences pref = getSharedpreference();
        return pref.getLong(PREF_KEY_LAST_NOTIFICATION_TIMESTAMP, 0);
    }

    public static long getLastShownMessageTimeStamp() {
        SharedPreferences pref = getSharedpreference();
        return pref.getLong(PREF_KEY_SHOWN_MESSAGE_TIMESTAMP, 0);
    }

    public static void setLastShownNotificationTimeStamp(long time) {
        SharedPreferences preferences = getSharedpreference();
        preferences.edit().putLong(PREF_KEY_LAST_NOTIFICATION_TIMESTAMP, time).apply();
    }

    public static void setLastShownMessageTimeStamp(long time) {
        SharedPreferences preferences = getSharedpreference();
        preferences.edit().putLong(PREF_KEY_SHOWN_MESSAGE_TIMESTAMP, time).apply();
    }

    public static void setLastMessageTimeStamp(long time) {
        getSharedpreference().edit().putLong(PREF_KEY_LAST_MESSAGE_TIMESTAMP, time).apply();
    }

    public static long getLastMessageTimeStamp(Context context) {
        return getSharedpreference().getLong(PREF_KEY_LAST_MESSAGE_TIMESTAMP, 0);
    }

    public static boolean isScored() {
        return getSharedpreference().getBoolean(PREF_KEY_SCORED, false);
    }

    public static void setScored(boolean commented) {
        getSharedpreference().edit().putBoolean(PREF_KEY_SCORED, commented).apply();
    }

    public static void setPaid(boolean paid) {
        getSharedpreference().edit().putBoolean(PREF_KEY_PAID, paid).apply();
    }

    public static boolean paid() {
        return getSharedpreference().getBoolean(PREF_KEY_PAID, false);
    }

}
