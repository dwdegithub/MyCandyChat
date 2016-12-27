package com.candychat.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.candychat.R;


/**
 * Created by ZN_mager on 2016/5/24.
 */
public class TermOfUseDialog extends Dialog implements View.OnClickListener {

    private OnTermClickListener mListener;

    public TermOfUseDialog(Context context, OnTermClickListener listener) {
        super(context, R.style.Theme_Dialog_Transparent);
        this.mListener = listener;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_term_of_use);
        findViewById(R.id.tv_review).setOnClickListener(this);
        findViewById(R.id.tv_accept).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            int viewId = v.getId();
            switch (viewId) {
                case R.id.tv_review:
                    mListener.onReview(this);
                    break;
                case R.id.tv_accept:
                    mListener.onAccept(this);
                    break;
            }
        }
    }

    public interface OnTermClickListener {
        void onAccept(TermOfUseDialog dialog);

        void onReview(TermOfUseDialog dialog);
    }
}
