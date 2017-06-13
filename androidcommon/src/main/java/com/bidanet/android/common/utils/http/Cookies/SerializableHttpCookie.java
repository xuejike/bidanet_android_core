package com.bidanet.android.common.utils.http.Cookies;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

/**
 * Created by robert on ic_text_yybzstg/3/ic_text_yybzstg.
 * okhttp3 Cookie的存储model
 */
public class SerializableHttpCookie implements Serializable {
    private static final long serialVersionUID = 6374381323722046732L;

    private transient final Cookie cookie;
    private transient Cookie clientCookie;

    public SerializableHttpCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        Cookie bestCookie = cookie;
        if (clientCookie != null) {
            bestCookie = clientCookie;
        }
        return bestCookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {

        out.writeObject(cookie.name());
        out.writeObject(cookie.value());
        out.writeObject(cookie.domain());
        out.writeLong(cookie.expiresAt());
        out.writeObject(cookie.path());
        out.writeBoolean(cookie.secure());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        Cookie.Builder clientCookiebuilder = new Cookie.Builder()
                .name(name)
                .value(value)
                .domain((String) in.readObject())
                .expiresAt(in.readLong())
                .path((String) in.readObject());
        boolean secure = in.readBoolean();
        if (secure) {
            clientCookiebuilder.secure();
        }
        clientCookie = clientCookiebuilder.build();
    }
}
