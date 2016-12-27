package com.candychat.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.candychat.ui.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by ZN_mager on 2016/5/16.
 */
public class BaseFragment extends Fragment {
    private BaseActivity mAttached;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAttached = (BaseActivity) context;
    }

    void showLoadingDialog() {
        if (mAttached != null) {
            mAttached.showLoadingDialog();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    void dismissLoadingDialog() {
        if (mAttached != null) {
            mAttached.dismissLoadingDialog();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAttached = null;
    }

    void finish() {
        if (mAttached != null) {
            mAttached.finish();
        }
    }
}
