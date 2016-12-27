package com.candychat.ui;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.candychat.im.service.SinchService;
import com.candychat.inter.IIMService;
import com.candychat.model.MessageModel;

/**
 * Created by ZN_mager on 2016/5/24.
 */
public class BaseIMActivity extends BaseActivity {
    protected IIMService mIMService;
    private boolean mIMServiceBinded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //用户 sinch 聊天id
        bindIMService("123");
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMService = (IIMService) service;
            MessageModel.getInstance().setmIIMService(mIMService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIMService = null;
        }
    };

    private boolean bindIMService(String currentUserID) {
        Intent intent = new Intent(this, SinchService.class);
        intent.putExtra(SinchService.PARAM_KEY_USER_ID, currentUserID);
        mIMServiceBinded = bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
        return mIMServiceBinded;
    }

    private void unbindIMService() {
        if (mIMService != null && mIMServiceBinded) {
            unbindService(mServiceConnection);
            mIMServiceBinded = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindIMService();
    }

}
