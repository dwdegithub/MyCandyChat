package com.candychat.request;

/**
 * Created by ZN_mager on 2016/5/10.
 */
public class RequestUrls {
    private static final String BASE_URL = "http://192.168.0.88:8088/DatingWeb/";
//    private static final String BASE_URL = "http://45.79.10.24:8080/DatingWeb/";
//    private static final String BASE_URL = "http://dating.rcplatformhk.com/DatingWeb/";

    //userinfo
    private static final String USER_URL_BASE = BASE_URL + "user/";
    public static final String REGISTER = USER_URL_BASE + "userRegister_v2.do?method=upload";
    public static final String USERINFO_MODIFY = USER_URL_BASE +"userinfoModify_v2.do?method=upload";
    public static final String USERINFO_MODIFY_HOOKUP = USER_URL_BASE +"userinfoModifyHookup_v2.do";
    public static final String LOGIN = USER_URL_BASE + "userLogin_v2.do";
    public static final String GET_NEARBY_USERS = USER_URL_BASE + "getNearbyUsers_v2.do";
    public static final String QUERY_USERINFO = USER_URL_BASE + "queryUserinfo_v2.do";


    //system
    public static final String SYS_URL_BASE = BASE_URL + "sys/";
    public static final String REQUEST_SERVER_TIME = SYS_URL_BASE + "getSystemtime_v2.do";
    public static final String REPORT_USER = SYS_URL_BASE + "reportUser_v2.do";
    public static final String BLOCK_USER = SYS_URL_BASE + "blockUser_v2.do";
    public static final String GET_SYSTEM_CONFIGURE = SYS_URL_BASE + "getSystemConfigure_v2.do";
    public static final String QUERY_BLOCK_USER = SYS_URL_BASE + "queryBlockUser_v2.do";

    //likeaction
    public static final String LIKEACTION_URL_BASE = BASE_URL + "likeaction/";

    public static final String LIKEA_USER = LIKEACTION_URL_BASE + "likeUser_v2.do";
    public static final String CANCLE_MATCH = LIKEACTION_URL_BASE + "cancelMatching_v2.do";

    //im
    public static final String IM_URL_BASE = BASE_URL + "im/";

    public static final String INITIATE_CHAT = IM_URL_BASE + "initiateChat_v2.do";
    public static final String QUERY_ROOMINFO = IM_URL_BASE + "queryRoominfo_v2.do";
    public static final String TRUN_OVER_ROOM = IM_URL_BASE + "turnoverRoom_v2.do";
    public static final String QUERY_SINGLE_CHAT = IM_URL_BASE + "querySingleChat_v2.do";
    public static final String UPLOAD_IMAGE = IM_URL_BASE + "upLoadPic_v2.do?method=upload";
    public static final String QUERY_SINGLE_CHAT_LIST = IM_URL_BASE + "querySingleChatList_v2.do";
    public static final String QUERY_MESSAGE = IM_URL_BASE + "queryMessage_v2.do";
    public static final String REPORT_PIC = IM_URL_BASE + "reportPic_v2.do";
    public static final String SEND_MSG_TIME_LIMIT = IM_URL_BASE + "getSendMsgTimeLimit_v2.do";

    //vip
    public static final String VIP_URL_BASE = BASE_URL + "vip/";
    public static final String VERIFYPURCHASE = VIP_URL_BASE + "verifyPurchaseForAndriod_v2.do";


    //杨鹏新修改接口
    private static final String YP_BASE_URL = "http://192.168.0.88:8101";
//    private static final String YP_BASE_URL = "http://dating.rcplatformhk.com";
    //popular
    public static final String POPULAR_URL_BASE = YP_BASE_URL + "/api/old/";
    public static final String POPULAR_URL = "/users/populars";

    //VerifyOncePurchase
    public static final String VERIFY_ONCE_PURCHASE = "/vips/google";
}
