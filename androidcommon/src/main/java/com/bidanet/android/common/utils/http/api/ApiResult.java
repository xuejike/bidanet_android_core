package com.bidanet.android.common.utils.http.api;

/**
 * Created by xuejike on 2017/5/31.
 */


public class ApiResult<T>  {
    public static final int STATUS_OK = 200;
    public static final int STATUS_ERROR = 300;
    public static final int STATUS_TIMEOUT = 301;
    protected int statusCode;
    protected String message;
    protected T data;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
