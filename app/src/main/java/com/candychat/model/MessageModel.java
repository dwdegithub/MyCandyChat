package com.candychat.model;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.candychat.CandyApplication;
import com.candychat.CurrentUser;
import com.candychat.User;
import com.candychat.UserManager;
import com.candychat.analytics.EventUtils;
import com.candychat.im.BaseMessage;
import com.candychat.im.MessageFactory;
import com.candychat.im.MessageType;
import com.candychat.im.receiver.MessageReceiver;
import com.candychat.inter.IIMService;
import com.candychat.inter.MessageListener;
import com.candychat.ui.MainActivity;
import com.candychat.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 2016/8/26.
 */
public class MessageModel {

    private IIMService mIIMService;
    private static MessageModel sInstance;
    private ArrayList<MessageListener> mListeners = new ArrayList<>();
    private List<NotificationListener> notificationListeners = new ArrayList<>();
    private List<SendMessageStatusChangeListener> sendMessageStatusChangeListeners = new ArrayList<>();

    public static MessageModel getInstance() {
        if (sInstance == null) {
            synchronized (MessageModel.class) {
                if (sInstance == null) {
                    sInstance = new MessageModel();
                }
            }
        }
        return sInstance;
    }

    public void setmIIMService(IIMService mIIMService){
        this.mIIMService = mIIMService;
    }

    public IIMService getmIIMService() {
        return mIIMService;
    }

    public synchronized void addMessageListener(MessageListener listener) {
        mListeners.add(listener);
    }

    public synchronized void removeMessageListener(MessageListener listener) {
        if (!mListeners.isEmpty()) {
            mListeners.remove(listener);
        }
    }



    /**
     * @param message
     * @param messgaeChannelId message 所在的 channel
     */
    public synchronized void addMessage(BaseMessage message, String messgaeChannelId) {
        if (MessageType.isMessage(message.getMessageType())) {
//                addSingleChatMessage(message, messgaeChannelId);
        } else {
            invokeApplicationHandle(message, messgaeChannelId);
        }
        invokeNotificationHandle(message);
    }

    public interface NotificationListener {
        void onNewLikeNotification(BaseMessage likeMessage);

        void onMatchNotification(BaseMessage matchMessage);
    }

    public synchronized void addOnNotificationListener(NotificationListener notificationListener) {
        notificationListeners.add(notificationListener);
    }

    public synchronized void removeNotificationListener(NotificationListener notificationListener) {
        notificationListeners.remove(notificationListener);
    }

    private synchronized void invokeApplicationHandle(BaseMessage message, String messgaeChannelId) {
        switch (message.getMessageType()) {
            case MessageType.IM_NOTIFICATION_BLOCK:
                break;
            default:
                break;
        }
    }



    private synchronized void invokeNewMessage(final BaseMessage msg) {
        if (!mListeners.isEmpty()) {
            CandyModel.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    for (MessageListener listener : mListeners) {
                        listener.onNewMessage( msg);
                    }
                }
            });

        }
    }

    //    public void receiveIMMessage(String channel, String JsonMessage, long timeToken) {
    public void receiveIMMessage(Bundle data) {
        BaseMessage msg;
        CurrentUser currentUser = UserManager.getInstance().getCurrentUser();
        String content = data.getString(MessageReceiver.PARAM_KEY_CONTENT);
        long timestamp = data.getLong(MessageReceiver.PARAM_KEY_TIMESTAMP);
        msg = MessageFactory.parseStringToMessage(content, timestamp);
        MessageModel.getInstance().addMessage(msg, "");

    }

    private void invokeNotificationHandle(BaseMessage msg) {
        String content = null;
        int messageType = msg.getMessageType();
        if (!CandyApplication.isInBackground()) {
            return;
        }

        sendMessageNotification(CandyApplication.getContext(), content);
    }


    public interface SendMessageStatusChangeListener {
        void onSendMessageFail(String channel, BaseMessage message);

        void onSendMessageSuccess(String channel, BaseMessage message);
    }

    public synchronized void addSendMessageStatusChangeListener(SendMessageStatusChangeListener listener) {
        sendMessageStatusChangeListeners.add(listener);
    }

    public void removeSendMessageStatusChangeListener(SendMessageStatusChangeListener listener) {
        sendMessageStatusChangeListeners.remove(listener);
    }

    private void invokeSendMessageFail(final String channel, final BaseMessage msg) {
        msg.setSendStatus(BaseMessage.SEND_STATUS_FAIL);
        if (sendMessageStatusChangeListeners != null && sendMessageStatusChangeListeners.size() > 0) {
            CandyModel.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    for (SendMessageStatusChangeListener listener : sendMessageStatusChangeListeners) {
                        listener.onSendMessageFail(channel, msg);
                    }
                }
            });
        }
    }

    private void invokeSendMessageSuccess(final String channel, final BaseMessage msg) {
        msg.setSendStatus(BaseMessage.SEND_STATUS_SUCCESS);
        if (sendMessageStatusChangeListeners != null && sendMessageStatusChangeListeners.size() > 0) {
            CandyModel.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    for (SendMessageStatusChangeListener listener : sendMessageStatusChangeListeners) {
                        listener.onSendMessageSuccess(channel, msg);
                    }
                }
            });
        }
    }


    public void sendMessage(final BaseMessage msg, final String channel) {
        mIIMService.sendMessage(msg, channel);
    }

    public void updateMessageState(String messageId, String recipientId, int newState){
        BaseMessage msg = null;

        //

        if (newState == BaseMessage.SEND_STATUS_FAIL) {
            invokeSendMessageFail(recipientId, msg);
        } else if(newState == BaseMessage.SEND_STATUS_SUCCESS){
            invokeSendMessageSuccess(recipientId, msg);
        }
    }

    public void sendMessageNotification(Context context, String content) {
        String contentText = null;
        String titleText = null;
        titleText = "title";// context.getString(R.string.notification_title_message);
        contentText = "new message";//context.getString(R.string.notification_new_message_content);
        if (content != null) {
            contentText = content;
        }
        if (!TextUtils.isEmpty(contentText)) {
            Intent resultIntent = new Intent(context, MainActivity.class);
//            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
//            NotificationUtils.sendNotification(context, titleText, contentText, R.mipmap.ic_launcher, R.mipmap.ic_launcher, resultPendingIntent);
        }
    }
}

