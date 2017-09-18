package com.bidanet.android.common.utils.http;

/**
 * Created by xuejike on 2017/6/5.
 */

public abstract class ApiResultCallBack<T> {
    /**
     * 执行成功
     * @param data
     */
    public abstract void success(T data);

    /**
     * 请求-完成（包含成功和失败回调）
     */
    public void completed(){

    }


    /**
     * 业务异常
     * @param msg
     */
    public void error(String msg){

    }

    /**
     * 未识别异常
     * @param msg
     * @param ex
     */
    public void exception(String msg,Throwable ex){


    }

    /**
     * 网络异常

     * @param ex
     */
    public void networkError(Throwable ex){

    }

    /**
     * 未登录异常

     * @param ex
     */
    public void noLoginError(Throwable ex){

    }
}
