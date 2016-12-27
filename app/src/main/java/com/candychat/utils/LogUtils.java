package com.candychat.utils;

import android.util.Log;

/**
 * Created by dw on 2016/8/27.
 */
public class LogUtils {
    public static final boolean WILL_LOG = true;

    public static void e(String tag, String message) {
        if (WILL_LOG) {
            Log.e(tag, message);
        }
    }

    public static void e(String message) {
        if (WILL_LOG) {
            Log.e("dw", message);
        }
    }
}
