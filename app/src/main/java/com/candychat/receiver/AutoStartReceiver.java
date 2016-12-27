package com.candychat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoStartReceiver extends BroadcastReceiver {
    public static final String Action = "com.candychat.receiver.AutoStartReceiver";
    public AutoStartReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("dw", " AutoStartReceiver receive the action" + intent.getAction());
        Intent in = new Intent(context, PeriosPushService.class);
        context.startService(in);
    }
}
