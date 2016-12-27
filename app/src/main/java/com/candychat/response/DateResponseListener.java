package com.candychat.response;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.candychat.request.DateError;


public abstract class DateResponseListener<T extends DateResponse> implements Response.ErrorListener, Response.Listener<T> {
    private Context mContext;
    private ErrorStateHandler mErrorHandler;
    private boolean mAutoResolve;

    public DateResponseListener(Context context, boolean autoResolveError) {
        mContext = context;
        mErrorHandler = new ErrorStateHandler();
        mAutoResolve = autoResolveError;
    }

    @Override
    public final void onErrorResponse(VolleyError error){
        onError(new DateError(DateResponse.REQUEST_FAILED, error.getMessage(), null));
    }

    @Override
    public final void onResponse(T response) {
        if (response.isError()) {
            boolean needInvokeListener = !mAutoResolve || (mAutoResolve && !mErrorHandler.handleState(mContext, response.getResponseState()));
            if (needInvokeListener) {
                onError(new DateError(response.getResponseState(), "error", response.getErrorData()));
            }
        } else {
            onComplete(response);
        }
    }

    public abstract void onComplete(T response);

    public abstract void onError(DateError error);
}
