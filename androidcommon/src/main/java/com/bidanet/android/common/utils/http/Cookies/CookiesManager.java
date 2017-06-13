package com.bidanet.android.common.utils.http.Cookies;


import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2016/ic_yello_ball/28.
 */
public class CookiesManager implements CookieJar {

    private PersistentCookieStore cookiestore;


    public CookiesManager(Context context) {
        this.cookiestore = new PersistentCookieStore(context);
    }

    public PersistentCookieStore getCookieStore() {
        return cookiestore;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookiestore.addAll(url.uri(), cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookiestore.getCookies();
    }
}
