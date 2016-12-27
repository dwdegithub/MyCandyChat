package com.candychat.utils;

import android.graphics.Typeface;

import com.candychat.CandyApplication;

/**
 * Created by dw on 2016/9/2.
 */
public class FontsUtils {
    private static FontsUtils sInstance = new FontsUtils();
    private Typeface typeface;
    private FontsUtils() {
    }

    public static FontsUtils getsInstance() {
        return sInstance;
    }
    public Typeface getTypeface(){
        if (typeface == null) {
            typeface = Typeface.createFromAsset(CandyApplication.getContext().getAssets(),"fonts/MyriadPro-Regular_0.otf");
        }
        return typeface;
    }
}
