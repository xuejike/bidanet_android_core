package com.bidanet.android.common.utils.http.impl;

import com.bidanet.android.common.utils.http.ApiResultHandler;
import com.bidanet.android.common.utils.http.api.ApiException;
import com.bidanet.android.common.utils.http.api.ApiResult;
import com.bidanet.android.common.utils.http.api.NoLoginException;

/**
 * Created by xuejike on 2017/6/5.
 */

public class DefaultApiResultHandler extends ApiResultHandler<ApiResult> {
    @Override
    protected <T> T handler(ApiResult d) {
        int statusCode = d.getStatusCode();
        switch (statusCode){
            case 200:{
                return (T) d.getData();
            }
            case 301:{
                throw new NoLoginException("未登录");
            }
            case 300:{
                throw new ApiException(d.getMessage());
            }
        }
        return null;
    }
}
