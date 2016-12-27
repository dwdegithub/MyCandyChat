package com.candychat;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.candychat.im.receiver.MessageReceiver;
import com.candychat.receiver.PeriosPushService;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by admin on 2016/12/22.
 */

public class CandyApplication extends Application {
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());
    private static Context sContext;
    private static boolean sInBackground = false;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //registe IM Message receiver
        MessageReceiver msgReceiver = new MessageReceiver(this);
        IntentFilter imMessageFilter = new IntentFilter();
        imMessageFilter.addAction(MessageReceiver.ACTION_INCOMING_MESSAG);
        imMessageFilter.addAction(MessageReceiver.ACTION_UPDATE_MESSAGE);
        registerReceiver(msgReceiver, imMessageFilter);

    }

    private void init() throws IOException {

        Constants.getInstance().init();
        initImageLoader();
        DateSettings.init(getApplicationContext());
    }

    private void initImageLoader() throws IOException {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(Constants.getInstance().getIMAGE_DIR()))
                .memoryCache(new LRULimitedMemoryCache(1024 * 1024 * 5))
                .threadPoolSize(5)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static boolean isInBackground() {
        return sInBackground;
    }

    public static Context getContext() {
        return sContext;
    }

    public static void runOnUIThread(Runnable task, long delay) {
        if (delay > 0) {
            sMainHandler.postDelayed(task, delay);
        } else {
            sMainHandler.post(task);
        }
    }
    public static void runOnUIThread(Runnable task) {
        runOnUIThread(task, 0);
    }

    public static Handler getHandler() {
        return sMainHandler;
    }

    private void listener() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                sInBackground = false;
                canclePeriodPush();
            }

            @Override
            public void onActivityPaused(Activity activity) {
                sInBackground = true;
                initPeriodPush();
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                initPeriodPush();
            }
        });
    }

    private void initPeriodPush() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);//(int)Math.random()*30
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        CandyPreference.setPeriodPushTargetTime(calendar.getTimeInMillis());
        Intent startIntent = new Intent(sContext,PeriosPushService.class);
        sContext.startService(startIntent);
    }

    private void canclePeriodPush() {
        CandyPreference.setPeriodPushTargetTime(0);
    }
}
