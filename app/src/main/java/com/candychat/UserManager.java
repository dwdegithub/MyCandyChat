package com.candychat;
import com.candychat.inter.CurrentUserInfoListener;
import com.candychat.model.CandyModel;
import com.candychat.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuo on 2016/9/5.
 */
public class UserManager {
    public static final int OUT_SEND_MSG_TIME_LIMIT = 0; //chatroom不在免费试用期
    public static final int IN_SEND_MSG_TIME_LIMIT = 1;  //chatroom在免费试用期

    private static final UserManager sInstance = new UserManager();
    private CurrentUser currentUser;
    private int sendMsgTimeLimitFlag = 0;
    public static UserManager getInstance(){
        return sInstance;
    }

    private List<CurrentUserInfoListener> currentUserInfoListeners = new ArrayList<>();

    public void init(){
        currentUser = CandyPreference.getCurrentUser();
    }


    public synchronized void addCurrentUserInfoListener(CurrentUserInfoListener currentUserInfoListener) {
        currentUserInfoListeners.add(currentUserInfoListener);
    }

    public synchronized void removeCurrentUserInfoListener(CurrentUserInfoListener currentUserInfoListener) {
        if (currentUserInfoListeners.size() > 0) {
            currentUserInfoListeners.remove(currentUserInfoListener);
        }
    }


    public boolean isRegisted() {
        return currentUser != null;
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
        saveCurrentUser();
    }

    public void saveCurrentUser() {
        CandyPreference.setCurrentUser(currentUser);
    }


    private void invokeCurrentUserVipStatusChanged(final int vipType) {

        if (currentUserInfoListeners != null && currentUserInfoListeners.size() > 0) {
            CandyModel.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    for (CurrentUserInfoListener listener : currentUserInfoListeners) {
                        listener.onCurrentUserVipStatusChanged(vipType);
                    }
                }
            });
        }

    }

}
