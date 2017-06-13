package com.bidanet.android.common.utils.http;

import android.content.Context;

import com.bidanet.android.common.CommonConfig;

import com.bidanet.android.common.exception.BaseException;
import com.bidanet.android.common.utils.http.Cookies.CookiesManager;
import com.bidanet.android.common.utils.http.api.ApiException;
import com.bidanet.android.common.utils.http.api.NoLoginException;
import com.bidanet.android.common.utils.http.impl.DefaultApiResultHandler;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xuejike on 2017/5/31.
 */

public class HttpMethod<K> {
    protected static int DEFAULT_TIMEOUT=3;
    protected static Retrofit HTTP;
    protected String baseUrl;
    protected Context context;
    protected ApiResultHandler apiResultHandler;
    protected LoadingDialog loadingDialog=new LoadingDialog() {
        @Override
        public void show() {

        }

        @Override
        public void dismiss() {

        }
    };


    public static Map<String,String> urlParams;
    public static Map<String,String> headers;
    public static HttpMethod INSTANCE;

    private HttpMethod(){

    }

    /**
     * 初始化
     * @param baseUrl
     */
    public static void init(String baseUrl , Context context){
        INSTANCE = new HttpMethod();
        INSTANCE.baseUrl=baseUrl;
        INSTANCE.context = context;
        INSTANCE.apiResultHandler=new DefaultApiResultHandler();
        INSTANCE.createHttp();
    }

    public ApiResultHandler getApiResultHandler() {
        return apiResultHandler;
    }

    public void setApiResultHandler(ApiResultHandler apiResultHandler) {
        this.apiResultHandler = apiResultHandler;
    }

    /**
     * 创建 HTTP服务
     */
    protected void createHttp(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        /**
         *  拦截器
         */
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                HttpUrl.Builder authorizedUrlBuilder = request.url()
                        .newBuilder();
                if (urlParams!=null){
                    for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                        authorizedUrlBuilder.addQueryParameter(entry.getKey(),entry.getValue());
                    }
                }

                Request.Builder builder = request.newBuilder()
                        .method(request.method(), request.body())
                        .url(authorizedUrlBuilder.build())
                        //对所有请求添加请求头
                        .header("mobileFlag", "android");
                if (headers!=null){
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        builder.addHeader(entry.getKey(),entry.getValue());
                    }
                }


                Request newRequest = builder.build();


                return  chain.proceed(newRequest);
            }
        });
        //日志输出
        if (CommonConfig.DEBUG){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookiesManager(context)).build();

        HTTP = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .client(client)
                .addConverterFactory(FastJsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public static Retrofit getHttp(){
        if (HTTP==null){
            throw new BaseException("HttpMethod 未初始化");
        }
        return HTTP;

    }


    /**
     * HTTP请求结果统一处理
     * @param obs
     * @param lt
     * @param apiResultHandler
     * @param <T>
     * @return
     */
    public  <T> Observable<T>  toSubscribe(Observable<K> obs, LifecycleTransformer lt, final ApiResultHandler<K> apiResultHandler){
        Observable<K> compose;
        //1.处理生命周期
        if (lt!=null){
            compose= obs.compose(lt);
        }else{
            compose=obs;
        }

        Observable<T> map =apiResultHandler.resultMap(compose);

        return map.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());


    }
    public  <T> Observable<T>  toSubscribe(Observable<K> obs, LifecycleTransformer lt){
        return toSubscribe(obs,lt,apiResultHandler);
    }

    public <T> void apiResult(Observable<K> obs, LifecycleTransformer lt,
                              final ApiResultCallBack<T> apiResultCallBack, final LoadingDialog loadingDialog){
        Observable<T> observable = toSubscribe(obs, lt, apiResultHandler);
        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                loadingDialog.show();
            }
        }).subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
                apiResultCallBack.completed();
            }

            @Override
            public void onError(Throwable e) {
                loadingDialog.dismiss();
                if (e instanceof IOException){
                    apiResultCallBack.networkError(e);
                }else if(e instanceof NoLoginException){
                    apiResultCallBack.noLoginError(e);
                }
                else if (e instanceof ApiException ){
                    apiResultCallBack.error(e.getMessage());
                }else{
                    apiResultCallBack.exception(e.getMessage(),e);
                }
            }

            @Override
            public void onNext(T t) {
                apiResultCallBack.success(t);
            }
        });

    }


    public <T> void apiResult(Observable<K> obs, LifecycleTransformer lt,
                              final ApiResultCallBack<T> apiResultCallBack){
        apiResult(obs, lt, apiResultCallBack,loadingDialog);
    }
}
