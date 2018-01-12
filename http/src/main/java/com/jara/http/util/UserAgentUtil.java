package com.jara.http.util;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Administrator on 2018-1-11.
 */

public class UserAgentUtil {
    public static String getUserAgent(Context context) {
        WebView webview;
        webview = new WebView(context);
        WebSettings settings = webview.getSettings();
        String str=settings.getUserAgentString();
        return settings.getUserAgentString();
    }
}
