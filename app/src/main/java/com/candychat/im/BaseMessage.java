package com.candychat.im;

import java.io.Serializable;

/**
 * Created by dw on 2016/8/25.
 */
public class BaseMessage implements Serializable {

    public static final int SEND_STATUS_SENDING = 1;
    public static final int SEND_STATUS_FAIL = 2;
    public static final int SEND_STATUS_SUCCESS = 0;

    private String messageId;//消息编号
    private int userId;

    private double timeStamp;
    private String imagePath;
    private int messageType;
    private String messageContent;
    private int chatType;
    private int sendStatus = SEND_STATUS_SENDING;

    public BaseMessage(int userId, double timeStamp, String imagePath, int messageType, String messageContent, int chatType) {
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.imagePath = imagePath;
        this.messageType = messageType;
        this.messageContent = messageContent;
        this.chatType = chatType;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    //返回消息发送者的  channel id
    public String getSenderChannelId(){
        return "wink_" + userId;
    }
    
    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }


}
