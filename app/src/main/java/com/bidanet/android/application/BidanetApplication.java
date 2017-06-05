package com.bidanet.android.application;

import android.app.Application;

import com.bidanet.android.BR;
import com.bidanet.android.common.CommonConfig;

/**
 * Created by xuejike on 2017/5/26.
 */

public class BidanetApplication extends Application {
    protected static Application application;


    public static Application getApplication(){
        return application;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        CommonConfig.dataBind(BR.item);
        CommonConfig.http("http://192.168.18.89");

    }

}
