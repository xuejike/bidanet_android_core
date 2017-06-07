package com.bidanet.android.model;

import android.util.Log;
import android.view.View;

import com.bidanet.android.common.utils.http.ApiResultCallBack;
import com.bidanet.android.common.utils.http.HttpMethod;
import com.bidanet.android.common.utils.http.api.ApiResult;
import com.bidanet.android.common.utils.http.impl.DefaultLoadingDialog;
import com.bidanet.android.service.UserInfo;

import cn.campusapp.router.Router;
import rx.Observable;

/**
 * Created by xuejike on 2017/5/25.
 */

public class TestModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void load(View v){
        Observable<ApiResult<String>> test = HttpMethod.getHttp().create(UserInfo.class).test();
        HttpMethod.INSTANCE.apiResult(test,null, new ApiResultCallBack<String>() {

            @Override
            public void success(String data) {

            }
        },new DefaultLoadingDialog(v.getContext()));
        name="ss"+1;
    }

    public void goNext(View view){
        Router.open("activity://testSecond/");
    }
}
