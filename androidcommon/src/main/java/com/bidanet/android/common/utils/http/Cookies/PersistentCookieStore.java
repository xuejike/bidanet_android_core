package com.bidanet.android.common.utils.http.Cookies;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;

/**
 * Created by robert on ic_text_yybzstg/3/ic_text_yybzstg.
 * okhttp3cooke存储
 */
public class PersistentCookieStore {

    private static final String LOG_TAG = "PersistentCookieStore";
    private static final String COOKIE_PREFS = "CookiePrefsFile3";
    private static final String COOKIE_NAME_PREFIX = "cookie_";

    private final HashMap<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    /**
     * Construct a persistent cookie store.
     *
     * @param context Context to attach cookie store to
     */
    public PersistentCookieStore(Context context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new HashMap<>();

        // Load any previously stored cookies into the store
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            if (((String) entry.getValue()) != null && !((String) entry.getValue()).startsWith(COOKIE_NAME_PREFIX)) {
                String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
                for (String name : cookieNames) {
                    String encodedCookie = cookiePrefs.getString(COOKIE_NAME_PREFIX + name, null);
                    if (encodedCookie != null) {
                        Cookie decodedCookie = decodeCookie(encodedCookie);
                        if (decodedCookie != null) {
                            if (!cookies.containsKey(entry.getKey()))
                                cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                            cookies.get(entry.getKey()).put(name, decodedCookie);
                        }
                    }
                }

            }
        }
    }

    public void add(URI uri, Cookie cookie) {

        // Save cookie into local store, or remove if expired
        if (System.currentTimeMillis() < cookie.expiresAt()) {
            if (!cookies.containsKey(cookie.domain()))
                cookies.put(cookie.domain(), new ConcurrentHashMap<String, Cookie>());
            cookies.get(cookie.domain()).put(cookie.name(), cookie);
        } else {
            if (cookies.containsKey(cookie.domain()))
                cookies.get(cookie.name()).remove(cookie.domain());
        }

        // Save cookie into persistent store
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.putString(cookie.domain(), TextUtils.join(",", cookies.get(cookie.domain()).keySet()));
        prefsWriter.putString(COOKIE_NAME_PREFIX + cookie.name(), encodeCookie(new SerializableHttpCookie(cookie)));
        prefsWriter.commit();
    }

    public void addAll(URI uri, List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            add(uri, cookie);
        }

    }

    protected String getCookieToken(URI uri, Cookie cookie) {
        return cookie.name() + cookie.domain();
    }

    public List<Cookie> get(URI uri) {
        ArrayList<Cookie> ret = new ArrayList<Cookie>();
        for (String key : cookies.keySet()) {
            if (uri.getHost().contains(key)) {
                ret.addAll(cookies.get(key).values());
            }
        }
        return ret;
    }

    public boolean removeAll() {
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.commit();
        cookies.clear();
        return true;
    }


    public boolean remove(URI uri, Cookie cookie) {
        String name = getCookieToken(uri, cookie);

        if (cookies.containsKey(uri.getHost()) && cookies.get(uri.getHost()).containsKey(name)) {
            cookies.get(uri.getHost()).remove(name);

            SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            if (cookiePrefs.contains(COOKIE_NAME_PREFIX + name)) {
                prefsWriter.remove(COOKIE_NAME_PREFIX + name);
            }
            prefsWriter.putString(uri.getHost(), TextUtils.join(",", cookies.get(uri.getHost()).keySet()));
            prefsWriter.commit();

            return true;
        } else {
            return false;
        }
    }

    public List<Cookie> getCookies() {
        ArrayList<Cookie> ret = new ArrayList<Cookie>();
        for (String key : cookies.keySet())
            ret.addAll(cookies.get(key).values());

        return ret;
    }

    /**
     * 获取cookie值
     *
     * @param key
     * @return
     */
    public String getCookieValue(String key) {
        String value = cookiePrefs.getString(COOKIE_NAME_PREFIX + key, "");
        if (!TextUtils.isEmpty(value)) {
            Cookie decodedCookie = null;
            try {
                decodedCookie = this.decodeCookie(value);
                return decodedCookie.value();
            } catch (Exception var5) {
                var5.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

    public List<URI> getURIs() {
        ArrayList<URI> ret = new ArrayList<URI>();
        for (String key : cookies.keySet())
            try {
                ret.add(new URI(key));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        return ret;
    }

    /**
     * Serializes Cookie object into String
     *
     * @param cookie cookie to be encoded, can be null
     * @return cookie encoded as String
     */
    protected String encodeCookie(SerializableHttpCookie cookie) {
        if (cookie == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in encodeCookie", e);
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    /**
     * Returns cookie decoded from cookie string
     *
     * @param cookieString string of cookie as returned from http request
     * @return decoded cookie or null if exception occured
     */
    protected Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableHttpCookie) objectInputStream.readObject()).getCookie();
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Log.d(LOG_TAG, "ClassNotFoundException in decodeCookie", e);
        }

        return cookie;
    }

    /**
     * Using some super basic byte array <-> hex conversions so we don't have to rely on any
     * large Base64 libraries. Can be overridden if you like!
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * Converts hex values from strings to byte arra
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}