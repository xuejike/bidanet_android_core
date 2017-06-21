package com.bidanet.android.common;

import android.content.Context;

import com.bidanet.android.common.adapter.DataBindAdapter;
import com.bidanet.android.common.utils.ImageGlide;
import com.bidanet.android.common.utils.http.HttpMethod;

/**
 * Created by xuejike on 2017/6/5.
 */

public class CommonConfig {
    public static boolean DEBUG=true;


    public static HttpMethod http(String baseurl , Context context){
        HttpMethod.init(baseurl ,context);
        return HttpMethod.INSTANCE;
    }

    public static void dataBind(int itemId){
        DataBindAdapter.BIND_ITEM_ID=itemId;
    }

    public static void dataBindIndex(int indexId){
        DataBindAdapter.BIND_ITEM_INDEX_ID = indexId;
    }

    public static void imageGlide(int errorImg , int placeholderImg){
        ImageGlide.init(errorImg , placeholderImg);
    }

}
