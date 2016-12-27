package com.candychat.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candychat.R;
import com.candychat.request.DateService;
import com.candychat.request.IDateService;
import com.candychat.ui.HomeActivity;

/**
 * Created by zuo on 2016/9/6.
 */
public class HistoryFragment extends BaseFragment {
    public static final String TAG = "HistoryFragment";

    private HomeActivity mController;
    private IDateService mDateService;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mController = (HomeActivity) getActivity();
        mDateService = new DateService(mController);

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        mDateService.release();
    }

}
