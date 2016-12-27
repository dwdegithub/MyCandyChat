package com.candychat.im;

/**
 * Created by zuo on 2016/8/26.
 */
public class MessageType {
    public static final int IM_NOTIFICATION_LIKE = 0;
    public static final int IM_NOTIFICATION_MATCH = 1;
    public static final int IM_NOTIFICATION_BLOCK = 2;
    public static final int IM_NOTIFICATION_UNMATCH = 3;
    public static final int MESSAGE_TEXT = 4;
    public static final int MESSAGE_IMAGE = 5;
    public static final int MESSAGE_LOCATION = 6;

    public static boolean isMessage(int typeValue){
        boolean result = false;
        switch (typeValue) {
            case MESSAGE_IMAGE:
            case MESSAGE_LOCATION:
            case MESSAGE_TEXT:
                result = true;
                break;
            default:
                break;
        }
        return result;
    }
}
