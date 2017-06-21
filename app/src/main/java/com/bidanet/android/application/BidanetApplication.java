package com.bidanet.android.application;

import android.app.Activity;
import android.app.Application;

import com.bidanet.android.BR;
import com.bidanet.android.R;
import com.bidanet.android.activity.TestSecondActivity;
import com.bidanet.android.common.CommonConfig;

import java.util.Map;

import cn.campusapp.router.Router;
import cn.campusapp.router.router.IActivityRouteTableInitializer;

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
        CommonConfig.dataBindIndex(BR.item_index);
        CommonConfig.http("http://192.168.18.89" , this);
        CommonConfig.imageGlide(R.mipmap.ic_launcher , R.mipmap.ic_launcher);


        Router.initActivityRouter(getApplicationContext(), new IActivityRouteTableInitializer() {
            @Override
            public void initRouterTable(Map<String, Class<? extends Activity>> router) {
                // only if the host is equal and pathes match, it matches.
                // The url "activity://first/kris/26/birthday" is one of the matches. "kris" and 26 are values of key "name" and "age". and the "name" value type is string, the age value type is integer.
                router.put("activity://testSecond/", TestSecondActivity.class);
            }
        });

    }

}
