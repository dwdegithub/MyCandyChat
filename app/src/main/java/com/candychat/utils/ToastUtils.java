package com.candychat.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ZN_mager on 2016/5/9.
 */
public class ToastUtils {

    public static void show(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }

    public static void show(Context context, int message, int length) {
        Toast.makeText(context, message, length).show();
    }
}
