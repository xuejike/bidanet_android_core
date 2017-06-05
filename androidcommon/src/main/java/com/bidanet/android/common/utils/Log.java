package com.bidanet.android.common.utils;


import com.bidanet.android.common.CommonConfig;
import com.orhanobut.logger.Logger;

/**
 * 日志工具类
 */

public class Log {
    public static void json(String json){
        if (CommonConfig.DEBUG){
            Logger.json(json);
        }

    }
    public static void d(String msg){
        if (CommonConfig.DEBUG){
            Logger.d(msg);
        }
    }
    public static void e(String msg,Throwable e){
        Logger.e(e,msg);
    }
}
