package com.bidanet.android.service;

import com.bidanet.android.common.utils.http.api.ApiResult;
import com.bidanet.android.model.CpuBean;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xuejike on 2017/5/31.
 */

public interface UserInfo {
    @GET("/agent/test.do")
    Observable<ApiResult<String>> test();
    @GET("/agent/test.do")
    Observable<String> testS();
}
