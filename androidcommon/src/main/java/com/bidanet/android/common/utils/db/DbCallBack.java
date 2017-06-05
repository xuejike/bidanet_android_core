package com.bidanet.android.common.utils.db;

/**
 * Created by xuejike on 2017/6/5.
 */

public abstract class DbCallBack<T> {
    public abstract void have(T data);

    public void error(Throwable ex)  {

    }
    public void noVal(){

    }
}
