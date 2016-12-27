package com.candychat.model;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.candychat.CandyApplication;
import com.candychat.utils.LogUtils;

public class CandyModel {


    private static final CandyModel sInstance = new CandyModel();
    private static final String WORK_THREAD_NAME = "candy_worker";

    private static HandlerThread sWorkThread = new HandlerThread(WORK_THREAD_NAME);

    static {
        sWorkThread.start();
    }

    private static Handler sWorkHandler = new Handler(sWorkThread.getLooper());

    public static CandyModel getInstance() {
        return sInstance;
    }

    public void init(Context context, final OnModelInitListner listner) {
        runOnWorkThread(new InitTask(context, listner));
    }


    public static void runOnMainThread(Runnable task) {
        CandyApplication.runOnUIThread(task);
    }

    public static void runOnWorkThread(Runnable task) {
        runOnWorkThread(task, 0);
    }

    public static void runOnWorkThread(Runnable task, long delay) {
        if (delay > 0) {
            sWorkHandler.postDelayed(task, delay);
        } else {
            sWorkHandler.post(task);
        }
    }

    public interface OnModelInitListner {

        void onInitOver();

    }

    private class InitTask implements Runnable {
        private OnModelInitListner mListener;
        private Context mContext;

        InitTask(Context context, OnModelInitListner listner) {
            this.mListener = listner;
            this.mContext = context;
        }

        @Override
        public void run() {
            LogUtils.e("dw", "init start");

            if (mListener != null) {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onInitOver();
                    }
                });
            }
            LogUtils.e("dw", "init over");
        }
    }

}