package com.candychat;

import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by ZN_mager on 2016/5/11.
 */
public class Constants {

    private static Constants sInstance ;
    public static final int NOTIFICATION_ID_NEW_MESSAGE = 1;
    public static final long ONE_SECOND = 1000;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final int ONE_MINUTE_SECOND = 60;

    public static final int SDK_VERSION = Build.VERSION.SDK_INT;
    public static final boolean ABOVE_L = SDK_VERSION >= Build.VERSION_CODES.LOLLIPOP;

    private File APP_DIR ;
    private File TEMP_DIR ;
    private File IMAGE_DIR;


    public void init(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            APP_DIR = new File(Environment.getExternalStorageDirectory(), "wink");
        } else{
            APP_DIR = new File(CandyApplication.getContext().getFilesDir().getAbsolutePath(), "wink");
        }
        TEMP_DIR = new File(APP_DIR, "temp");
        IMAGE_DIR = new File(APP_DIR, "image");
    }

    public static Constants getsInstance() {
        return sInstance;
    }

    public File getIMAGE_DIR() {
        return IMAGE_DIR;
    }

    public File getTEMP_DIR() {
        return TEMP_DIR;
    }

    public File getAPP_DIR() {
        return APP_DIR;
    }

    private Constants(){}

    public static Constants getInstance(){
        if (sInstance == null) {
            synchronized (Constants.class) {
                if (sInstance == null) {
                    sInstance = new Constants();
                }
            }
        }
        return sInstance;
    }
}
