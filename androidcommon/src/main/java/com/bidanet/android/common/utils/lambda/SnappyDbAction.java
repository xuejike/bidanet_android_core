package com.bidanet.android.common.utils.lambda;

/**
 * Created by xuejike on 2017/5/27.
 */

public abstract class SnappyDbAction<T> {
    protected T val;

    public SnappyDbAction(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public void have(Fun1<T,Void> fun){
        if (val!=null){
            fun.fun(val);
        }

    }
    public void noVal(Fun0<Void> fun){
        if (val==null){
            fun.fun();
        }
    }
    public void error(Exception e){

    }
}
