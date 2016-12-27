package com.candychat.inter;


import com.candychat.CurrentUser;

/**
 * Created by admin on 2016/9/27.
 */

public interface CurrentUserInfoListener {

    void onCurrentUserInfoChange(CurrentUser currentUser);

    void onCurrentUserVipStatusChanged(int vipType);

    void onCurrentUserPortrait(String url_small, String url_big);

}
