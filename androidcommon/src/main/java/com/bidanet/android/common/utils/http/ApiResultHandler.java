package com.bidanet.android.common.utils.http;

import com.bidanet.android.common.utils.http.api.ApiException;
import com.bidanet.android.common.utils.http.api.NoLoginException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by xuejike on 2017/6/5.
 */

public abstract class ApiResultHandler<K> {
    protected abstract<T> T handler(K d);
    protected void error(String msg){
        throw new ApiException(msg);
    }
    protected void noLogin(){
        throw new NoLoginException("未登录");
    }

    public  <T> Observable<T> resultMap(Observable obs){
       return obs.flatMap(new Func1<K, Observable<T>>() {
            @Override
            public Observable<T> call(K k) {
                T handler = handler(k);
                Observable<T> data = createData(handler);
                return data;
            }
        });
    }
    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }
}
