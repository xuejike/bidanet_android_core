package com.bidanet.android.common.utils.db;

/**
 * Created by xuejike on 2017/5/26.
 */

public class SnappyException extends RuntimeException {
    public SnappyException() {
    }

    public SnappyException(String message) {
        super(message);
    }

    public SnappyException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnappyException(Throwable cause) {
        super(cause);
    }

    public SnappyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
