package com.candychat.analytics;

import android.content.Context;

import com.candychat.CandyApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by ZN_mager on 2016/5/31.
 */
public class EventUtils {
    private static void onEvent(String cate, String message) {
        Context context = CandyApplication.getContext();
        MobclickAgent.onEvent(context, cate, message);
    }

    public static class Regist {
        private static final String CATE = "Sign_up_NEW";

        private static void onEvent(String event) {
            EventUtils.onEvent(CATE, event);
        }

        public static void male() {
            onEvent("male");
        }

        public static void female() {
            onEvent("female");
        }

        public static void make_new_friend() {
            onEvent("make_new_friend");
        }

        public static void hook() {
            onEvent("hook");
        }

        public static void long_term() {
            onEvent("long_term");
        }

    }

    public static class Nearby {
        private static final String CATE = "nearby_NEW";

        private static void onEvent(String event) {
            EventUtils.onEvent(CATE, event);
        }

        public static void nope() {
            onEvent("nope");
        }

        public static void like() {
            onEvent("like");
        }

        public static void location() {
            onEvent("location");
        }

        public static void message() {
            onEvent("message");
        }
    }

    public static class Chatroom {
        private static final String CATE = "chatroom_NEW";

        private static void onEvent(String event) {
            EventUtils.onEvent(CATE, event);
        }

        public static void chatroom_like() {
            onEvent("chatroom_like");
        }

        public static void chatroom_message() {
            onEvent("chatroom_message");
        }

        public static void chatroom_send(String chatroomName) {
            onEvent("chatroom_"+chatroomName+"_send");
        }

        public static void chatroom_picture(String chatroomName) {
            onEvent("chatroom_"+chatroomName+"_picture");
        }
    }


    public static class Message {
        private static final String CATE = "message_NEW";

        private static void onEvent(String event) {
            EventUtils.onEvent(CATE, event);
        }

        public static void Location() {
            onEvent("location");
        }

        public static void send() {
            onEvent("send");
        }

        public static void picture() {
            onEvent("picture");
        }
    }

    public static class WhoLikesYou {
        private static final String CATE = "who_likes_you_NEW";

        private static void onEvent(String event) {
            EventUtils.onEvent(CATE, event);
        }

        public static void more() {
            onEvent("more");
        }

        public static void check() {
            onEvent("check");
        }

    }

    public static class Match {
        private static final String CATE = "Match_NEW";

        private static void onEvent(String event) {
            EventUtils.onEvent(CATE, event);
        }

        public static void match() {
            onEvent("match");
        }

    }

    public static class InAppPush {
        private static final String CATE = "In_App_push_NEW";

        private static void onEvent(String event) {
            EventUtils.onEvent(CATE, event);
        }

        public static void like_click() {
            onEvent("like_click");
        }

        public static void match_click() {
            onEvent("match_click");
        }

        public static void message_click() {
            onEvent("message_click");
        }

    }

    public static class IAP {
        private static final String CATE = "IAP_NEW";

        private static void onEvent(String event) {
            EventUtils.onEvent(CATE, event);
        }

        public static void messageClick() {
            onEvent("message_click");
        }

        public static void messageSuccess() {
            onEvent("message_success");
        }



    }


}
