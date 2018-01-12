package com.jara.library.util;

import android.content.Context;

/**
 * Created by Administrator on 2018-1-11.
 */

public class Utils {

    private static Context context;

    private Utils() {

    }

    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should init Utils first");
    }
}
