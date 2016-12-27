package com.candychat.im;


import com.google.gson.JsonSyntaxException;
import com.candychat.utils.JsonUtils;

/**
 * Created by dw on 2016/8/26.
 */
public class MessageFactory {

    public static BaseMessage parseStringToMessage(String message, long timeStamp) throws JsonSyntaxException {
        BaseMessage baseMessage = parseStringToMessage(message);
        baseMessage.setTimeStamp(timeStamp/10000);
        return baseMessage;
    }

    public static BaseMessage parseStringToMessage(String message)throws JsonSyntaxException{
        BaseMessage baseMessage = JsonUtils.parseJsonToBean(message, BaseMessage.class);
        return baseMessage;
    }

}
