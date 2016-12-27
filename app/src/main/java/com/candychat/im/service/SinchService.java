package com.candychat.im.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.candychat.im.BaseMessage;
import com.candychat.im.receiver.MessageReceiver;
import com.candychat.inter.IIMService;
import com.candychat.ui.IncomingCallScreenActivity;
import com.candychat.utils.JsonUtils;
import com.candychat.utils.LogUtils;
import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.messaging.WritableMessage;
import com.sinch.android.rtc.video.VideoController;


import java.util.List;

public class SinchService extends Service {
    public static final String PARAM_KEY_USER_ID = "currentUser_id";
    public static final String CALL_ID = "call_id";

    private static final String APP_KEY = "25958bb8-6407-46cf-848d-605f5e756b96";
    private static final String APP_SECRET = "97qx+2IFjEWf8YhQyAoY0A==";
    private static final String ENVIRONMENT = "sandbox.sinch.com";

    private SinchServiceInterface mSinchServiceInterface = new SinchServiceInterface();
    private SinchClient mSinchClient = null;
    private MessageClient mMessageClient;
    private CallClient mCallClient;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        String userId = intent.getExtras().getString("userId");
//        if (userId != null && mSinchClient == null) {
//            start(userId);
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        String userId = intent.getExtras().getString(PARAM_KEY_USER_ID);
        if (userId != null && mSinchClient == null) {
            LogUtils.e("dw"," Sinch onBind");
            start(userId);
        }
        return mSinchServiceInterface;
    }


    private class SinchServiceInterface extends Binder implements IIMService {

        @Override
        public boolean sendMessage(BaseMessage msg, String receiveUserChannelId) {
                boolean sendComplete = false;
                if (couldSendMessage()) {
                    WritableMessage sinchMessage = cover2SinchMessage( msg, receiveUserChannelId );
                    mMessageClient.send(sinchMessage);
                    msg.setMessageId(sinchMessage.getMessageId());
                    sendComplete = true;
                }
            return sendComplete;
        }

        @Override
        public Call getVideoCall(String receiveUserChannelId) {

            if (mCallClient == null) {
                return null;
            }
            Call call = mCallClient.callUserVideo(receiveUserChannelId);
            Call call1 = mCallClient.getCall(call.getCallId());
            if (call1 == null) {
                return null;
            }
            return call1;
        }

        @Override
        public Call getCall(String callId) {
            return mCallClient.getCall(callId);
        }


        @Override
        public VideoController getVideoController() {
            if (mSinchClient == null || !mSinchClient.isStarted()) {
                return null;
            }
            return mSinchClient.getVideoController();
        }

        @Override
        public AudioController getAudioController() {
            if (mSinchClient == null || !mSinchClient.isStarted()) {
                return null;
            }
            return mSinchClient.getAudioController();
        }
    }

    private WritableMessage cover2SinchMessage(BaseMessage msg, String receiveUserChannelId) {
        LogUtils.e("dw", "send message to " + receiveUserChannelId + " message content is " + msg.toString());
        String message = JsonUtils.parseBeanToJson(msg);
        WritableMessage writableMessage = new WritableMessage(receiveUserChannelId, message);
        return writableMessage;
    }

    private boolean couldSendMessage() {
        return mSinchClient != null && mSinchClient.isStarted() && mMessageClient != null;
    }

    private void start(String userId) {
        if (mSinchClient == null) {
            mSinchClient = Sinch.getSinchClientBuilder().context(getApplicationContext())
                    .userId(userId)
                    .applicationKey(APP_KEY)
                    .applicationSecret(APP_SECRET)
                    .environmentHost(ENVIRONMENT)
                    .build();
            mSinchClient.setSupportMessaging(true);
            mSinchClient.setSupportCalling(true);
            mSinchClient.startListeningOnActiveConnection();
            mSinchClient.setSupportActiveConnectionInBackground(true);
            mSinchClient.addSinchClientListener(new MySinchClientListener());
            mMessageClient = mSinchClient.getMessageClient();
            mMessageClient.addMessageClientListener(new MyMessageClientListener());
            mCallClient = mSinchClient.getCallClient();
            mCallClient.addCallClientListener(new MyCallClientListener());
            mSinchClient.start();

        }
    }


    private void terminate() {
        if (mSinchClient != null && mSinchClient.isStarted()) {
            mSinchClient.stopListeningOnActiveConnection();
            mSinchClient.terminate();
            mSinchClient = null;
            mMessageClient = null;
            mCallClient = null;
        }
    }



    private class MySinchClientListener implements SinchClientListener {

        @Override
        public void onClientFailed(SinchClient client, SinchError error) {

        }

        @Override
        public void onClientStarted(SinchClient client) {
//            if (client != mSinchClient) {
//                return;
//            }
        }

        @Override
        public void onClientStopped(SinchClient client) {
        }

        @Override
        public void onLogMessage(int level, String area, String message) {
            switch (level) {
                case Log.DEBUG:
                    Log.d("dw","area: "+ area +" message: " + message);
                    break;
                case Log.ERROR:
                    Log.e("dw","area: "+ area +" message: " + message);
                    break;
                case Log.INFO:
                    Log.i("dw","area: "+ area +" message: " + message);
                    break;
                case Log.VERBOSE:
                    Log.v("dw","area: "+ area +" message: " + message);
                    break;
                case Log.WARN:
                    Log.w("dw","area: "+ area +" message: " + message);
                    break;
            }
        }

        @Override
        public void onRegistrationCredentialsRequired(SinchClient client,
                ClientRegistration clientRegistration) {
        }
    }

    private class MyMessageClientListener implements MessageClientListener {

        @Override
        public void onIncomingMessage(MessageClient messageClient, Message message) {
            incomingMessage(message);
        }

        @Override
        public void onMessageSent(MessageClient messageClient, Message message, String s) {
            updateMessageState(message.getMessageId(), s, BaseMessage.SEND_STATUS_SUCCESS);
        }

        @Override
        public void onMessageFailed(MessageClient messageClient, Message message, MessageFailureInfo messageFailureInfo) {
            updateMessageState(message.getMessageId(), messageFailureInfo.getRecipientId(),BaseMessage.SEND_STATUS_FAIL);
        }

        @Override
        public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {
//            updateMessageState(messageDeliveryInfo.getMessageId(), messageDeliveryInfo.getRecipientId(), BaseMessage.SEND_STATUS_SUCCESS);
        }

        @Override
        public void onShouldSendPushData(MessageClient messageClient, Message message, List<PushPair> list) {

        }
    }

    private class MyCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            Intent intent = new Intent(SinchService.this, IncomingCallScreenActivity.class);
            intent.putExtra(CALL_ID, call.getCallId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    public void updateMessageState(String messageId, String recipientId, int state) {
        Intent intent = new Intent(MessageReceiver.ACTION_UPDATE_MESSAGE);
        intent.putExtra(MessageReceiver.PARAM_KEY_MESSAGE_STATE, state);
        intent.putExtra(MessageReceiver.PARAM_KEY_MESSAGE_ID, messageId);
        intent.putExtra(MessageReceiver.PARAM_KEY_RECIPIENT_ID, recipientId);
        sendBroadcast(intent);
    }

    public void incomingMessage(com.sinch.android.rtc.messaging.Message msg) {
        Intent intent = new Intent(MessageReceiver.ACTION_INCOMING_MESSAG);

        intent.putExtra(MessageReceiver.PARAM_KEY_RECEIVER, msg.getRecipientIds().get(0));
        intent.putExtra(MessageReceiver.PARAM_KEY_SENDER, msg.getSenderId());
        intent.putExtra(MessageReceiver.PARAM_KEY_CONTENT, msg.getTextBody());
        intent.putExtra(MessageReceiver.PARAM_KEY_TIMESTAMP, msg.getTimestamp().getTime());
        intent.putExtra(MessageReceiver.PARAM_KEY_MESSAGE_ID, msg.getMessageId());
        sendBroadcast(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        terminate();
    }

}
