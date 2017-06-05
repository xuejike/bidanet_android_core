package com.bidanet.android.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bidanet.android.BR;
import com.bidanet.android.R;

import com.bidanet.android.common.adapter.DataBindAdapter;
import com.bidanet.android.common.utils.http.ApiResultCallBack;
import com.bidanet.android.common.utils.http.HttpMethod;
import com.bidanet.android.common.utils.http.api.ApiResult;
import com.bidanet.android.common.utils.http.impl.DefaultLoadingDialog;
import com.bidanet.android.databinding.TestBinding;
import com.bidanet.android.model.TestModel;
import com.bidanet.android.service.UserInfo;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xuejike on 2017/5/25.
 */

public class TestMainActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestBinding t= DataBindingUtil.setContentView(this, R.layout.test);

        Observable<ApiResult<String>> test = HttpMethod.getHttp().create(UserInfo.class).test();
        HttpMethod.INSTANCE.apiResult(test, this.bindToLifecycle(), new ApiResultCallBack<String>() {

            @Override
            public void success(String data) {

            }
        },new DefaultLoadingDialog(this));
//        SnappyDbUtil.INSTANCE.get("xxx", Integer.class, new SnappyDbAction<Integer>() {
//            @Override
//            public void have(Integer val) {
//
//            }
//        });

//        bindUntilEvent()

//        HttpMethod.getHttp().create(UserInfo.class).testS().subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe();
        TestModel testModel = new TestModel();
        testModel.setName("xxx");
        t.setItem(testModel);
        ArrayList<TestModel> testModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestModel testModel1 = new TestModel();
            testModel1.setName("model+"+i);
            testModels.add(testModel1);
        }

        t.list.setAdapter(new DataBindAdapter<TestModel>(this,testModels,R.layout.item));

    }
}
