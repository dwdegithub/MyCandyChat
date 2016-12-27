package com.candychat.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.candychat.Constants;

/**
 * Created by ZN_mager on 2016/6/3.
 */
public class NotificationUtils {
    public static void sendNotification(Context context, String title, String content, int smallIconResId, int largeIconResId, PendingIntent resultPendingIntent) {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), largeIconResId);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setLargeIcon(icon)
                        .setSmallIcon(smallIconResId, 1000)
                        .setContentTitle(title)
                        .setContentText(content).setSound(alarmSound).setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).setLights(Color.GREEN, 3000, 3000);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        Notification notification = mBuilder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(Constants.NOTIFICATION_ID_NEW_MESSAGE, notification);
    }

}
