package com.candychat.inter;

import com.candychat.im.BaseMessage;
import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.video.VideoController;

public interface IIMService {

    boolean sendMessage(BaseMessage msg, String receiveUserChannelId);

    Call getVideoCall(String receiveUserChannelId);

    Call getCall(String callId);

    VideoController getVideoController();

    AudioController getAudioController();

}