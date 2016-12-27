package com.candychat.utils;

import android.graphics.Bitmap;

/**
 * Created by ZN_mager on 2016/5/25.
 */
public class BitmapUtils {
    public static Bitmap scaleBitmap(int maxSize, String path) {
        return RCImageUtils.decodeSampledBitmapFromFile(path, maxSize, maxSize, RCImageUtils.getImageAngle(path));
    }
}
