package com.candychat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PeriodPushReceiver extends BroadcastReceiver {


    public PeriodPushReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("dw", "PeriodPushReceiver receive the action " + intent.getAction());
        Intent startIntent = new Intent(context,PeriosPushService.class);
        context.startService(startIntent);
    }
}
