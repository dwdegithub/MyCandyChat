package com.candychat.response;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.candychat.R;
import com.candychat.ui.FreezedActivity;
import com.candychat.utils.ToastUtils;


/**
 * Created by ZN_mager on 2016/5/23.
 */
public class ErrorStateHandler {

    public boolean handleState(Context context, int state) {

        boolean handed = false;
        Intent intent = null;

        switch (state) {
            case DateResponse.STATE_UNREGISTED:
                //start RegisterActivity
                //                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                break;
            case DateResponse.STATE_FREEZED://pop all activity from task
                intent = new Intent(context, FreezedActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                break;
            case DateResponse.STATE_SERVER_EXCEPTION:
                ToastUtils.show(context, "Server Error. Please try again later", Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }
        if (intent != null) {
            handed = true;
            context.startActivity(intent);
        }
        return handed;
    }

}
