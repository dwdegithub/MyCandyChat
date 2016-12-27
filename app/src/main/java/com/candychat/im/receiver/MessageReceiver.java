package com.candychat.im.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.candychat.im.BaseMessage;
import com.candychat.model.MessageModel;


/**
 * Created by ZN_mager on 2016/5/3.
 */
public class MessageReceiver extends BroadcastReceiver {
    public static final String ACTION_INCOMING_MESSAG = "im.receiver.MessageReceiver.ActionIncomingMessage";
    public static final String ACTION_UPDATE_MESSAGE = "im.receiver.MessageReceiver.ActionUpdateMessage";

    public static final String PARAM_KEY_MESSAGE_STATE = "message_state";
    public static final String PARAM_KEY_MESSAGE_ID = "message_id";
    public static final String PARAM_KEY_RECIPIENT_ID = "recipient_id";
    public static final String PARAM_KEY_TIMESTAMP = "incoming_timestamp";
    public static final String PARAM_KEY_RECEIVER = "incoming_receiver";
    public static final String PARAM_KEY_SENDER = "incoming_sender";
    public static final String PARAM_KEY_CONTENT = "incoming_content";

    public MessageReceiver(Context context) {}

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_UPDATE_MESSAGE.equals(action)) {
            if (intent.getExtras() != null && intent.getExtras().containsKey(PARAM_KEY_MESSAGE_ID)) {
                String messageId = intent.getStringExtra(PARAM_KEY_MESSAGE_ID);
                String recipientId = intent.getStringExtra(PARAM_KEY_RECIPIENT_ID);
                int newState = intent.getIntExtra(PARAM_KEY_MESSAGE_STATE, BaseMessage.SEND_STATUS_SENDING);
                MessageModel.getInstance().updateMessageState(messageId, recipientId,newState);
            }
        } else if (ACTION_INCOMING_MESSAG.equals(action)) {

            if (intent.getExtras() != null) {
                MessageModel.getInstance().receiveIMMessage(intent.getExtras());
            }
        }
    }
}
