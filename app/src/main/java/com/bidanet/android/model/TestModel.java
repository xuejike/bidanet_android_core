package com.bidanet.android.model;

import android.databinding.Bindable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bidanet.android.BR;
import com.bidanet.android.common.adapter.*;
import com.bidanet.android.common.utils.http.ApiResultCallBack;
import com.bidanet.android.common.utils.http.HttpMethod;
import com.bidanet.android.common.utils.http.api.ApiResult;
import com.bidanet.android.common.utils.http.impl.DefaultLoadingDialog;
import com.bidanet.android.service.UserInfo;

import rx.Observable;

/**
 * Created by xuejike on 2017/5/25.
 */

public class TestModel extends com.bidanet.android.common.adapter.BaseModel{
    private boolean check;
    private String name;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
//        Router.open("activity://testSecond/");
    }

    @Bindable
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
        notifyPropertyChanged(BR.check);
    }

    public void getView(View view){
        check = ((RadioButton)view).isChecked();
        notifyPropertyChanged(BR.check);
    }


    public void click(View view , int position){

        Toast.makeText(view.getContext() , "位置：" + position , Toast.LENGTH_SHORT).show();

    }

}
