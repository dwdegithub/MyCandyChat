package com.candychat.utils;

/**
 * Created by ZN_mager on 2016/5/23.
 */
public class TimeCountDownThread extends Thread {

    private boolean mRunning = true;
    private long mTotalTimeMillons;
    private long mSpaceTimeMillons;
    private long mStartTimeMillons;

    private OnProgressChangedListener mProgrssChangedListener;

    public TimeCountDownThread(long totalTimeMillions, long spaceTimeMillions) {
        this.mTotalTimeMillons = totalTimeMillions;
        this.mSpaceTimeMillons = spaceTimeMillions;
    }

    @Override
    public final void run() {
        super.run();
        mStartTimeMillons = System.currentTimeMillis();
        long leaveTime = mTotalTimeMillons;
        while (mRunning && leaveTime > 0) {
            if (mProgrssChangedListener != null) {
                mProgrssChangedListener.onProgressChanged(leaveTime);
            }
            try {
                sleep(mSpaceTimeMillons);
                leaveTime = mTotalTimeMillons - (System.currentTimeMillis() - mStartTimeMillons);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void cancel() {
        mRunning = false;
        interrupt();
    }

    public interface OnProgressChangedListener {
        void onProgressChanged(long leavedTime);
    }

    public void setProgressChangedListener(OnProgressChangedListener listener) {
        mProgrssChangedListener = listener;
    }
}
