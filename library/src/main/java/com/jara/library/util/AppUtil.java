package com.jara.library.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2018-1-11.
 */

public class AppUtil {
    public static String getAppVersionName() {
        return "v" + getAppVersionName(Utils.getContext().getPackageName());
    }

    public static String getAppVersionName(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
