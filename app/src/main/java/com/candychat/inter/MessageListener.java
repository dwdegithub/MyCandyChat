package com.candychat.inter;

import com.candychat.im.BaseMessage;

/**
 * Created by dw on 2016/8/30.
 */
public interface MessageListener {

    void onNewMessage(BaseMessage msg);

    void onMessageStateChanged(BaseMessage msg, int oldState, int newState);

}
