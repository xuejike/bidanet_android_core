package com.bidanet.android.common;

import com.bidanet.android.common.adapter.DataBindAdapter;
import com.bidanet.android.common.utils.http.HttpMethod;

/**
 * Created by xuejike on 2017/6/5.
 */

public class CommonConfig {
    public static boolean DEBUG=true;


    public static HttpMethod http(String baseurl){
        HttpMethod.init(baseurl);
        return HttpMethod.INSTANCE;
    }
    public static void dataBind(int itemId){
        DataBindAdapter.BIND_ITEM_ID=itemId;
    }


}
