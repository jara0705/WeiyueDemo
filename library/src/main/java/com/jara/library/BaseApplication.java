package com.jara.library;

import android.app.Application;

import com.jara.library.handler.ActivityManager;
import com.jara.library.util.Utils;

/**
 * Created by Administrator on 2018-1-11.
 */

public class BaseApplication extends Application {

    public static BaseApplication instance;

    public BaseApplication() {}

    public static BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }

    public static ActivityManager activityManagement;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        activityManagement = ActivityManager.getInstance();
        Utils.init(this);
    }
}
