package com.candychat.request;

/**
 * Created by ZN_mager on 2016/5/9.
 */
public class DateError {
    private int code;
    private String message;
    private Object errorDate;

    public DateError(int code, String message, Object errorDate) {
        this.code = code;
        this.message = message;
        this.errorDate = errorDate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrorDate() {
        return errorDate;
    }
}
