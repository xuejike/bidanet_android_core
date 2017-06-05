package com.bidanet.android.common.utils.http.api;

/**
 * Created by xuejike on 2017/6/2.
 */

public class NoLoginException extends ApiException {
    public NoLoginException() {
    }

    public NoLoginException(String message) {
        super(message);
    }

    public NoLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoLoginException(Throwable cause) {
        super(cause);
    }

    public NoLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
