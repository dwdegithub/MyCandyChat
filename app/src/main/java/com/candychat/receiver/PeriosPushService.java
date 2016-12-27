package com.candychat.receiver;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.candychat.CandyPreference;
import com.candychat.R;
import com.candychat.ui.MainActivity;
import com.candychat.utils.NotificationUtils;

import java.util.Calendar;

public class PeriosPushService extends Service {

    private PeriodPushReceiver periodPushReceiver;
    public PeriosPushService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("dw", " PeriosPushService onCreate");
        periodPushReceiver = new PeriodPushReceiver();
        IntentFilter timeChangeFilter = new IntentFilter();
        timeChangeFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        timeChangeFilter.addAction(Intent.ACTION_TIME_CHANGED);
        timeChangeFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(periodPushReceiver, timeChangeFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("dw", " PeriosPushService onStartCommand ");
        long targeTime = CandyPreference.getPeriodPushTargetTime();
        if (targeTime != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND,0);
            long t = calendar.getTimeInMillis();
            if (targeTime == t) {

                Calendar nextTime = Calendar.getInstance();
                nextTime.setTimeInMillis(System.currentTimeMillis());
                nextTime.add(Calendar.DATE,1);
                nextTime.set(Calendar.HOUR_OF_DAY, 20);
                nextTime.set(Calendar.MINUTE, 0);
                nextTime.set(Calendar.SECOND, 0);
                nextTime.set(Calendar.MILLISECOND,0);
                CandyPreference.setPeriodPushTargetTime(nextTime.getTimeInMillis());

                Intent resultIntent = new Intent(this, MainActivity.class);
                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                this,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                NotificationUtils.sendNotification(this, "Message", getString(R.string.period_push_string), R.mipmap.ic_launcher, R.mipmap.ic_launcher, resultPendingIntent);

            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("dw"," PeriosPushService onDestroy and sendBroadcast to AutoStartReceiver");
        unregisterReceiver(periodPushReceiver);
        Intent intent = new Intent(AutoStartReceiver.Action);
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}
