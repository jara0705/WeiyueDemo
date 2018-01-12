package com.jara.library.handler;

import android.app.Activity;
import android.content.Context;

import com.jara.library.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by jara0705 on 2018-1-11.
 */

public class ActivityManager {

    private static ActivityManager instance;
    private Stack<Activity> activities;

    private ActivityManager() {}

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void pushActivity(Activity activity) {
        if (activities == null) {
            activities = new Stack<>();
        }
        activities.add(activity);
    }

    public Activity getCurrentActivity() {
        Activity activity = null;
        try {
            if (!activities.isEmpty()) {
                activity = activities.lastElement();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activity;
    }

    public List<Activity> getActivity(Class clazz) {
        if(clazz == null){
            return null;
        }

        List activityList = null;
        if(activities != null && activities.size() > 0){
            activityList = new ArrayList();
            for(int i=0; i<activities.size(); i++){
                Activity aActivity = activities.elementAt(i);
                if(clazz.getName().equalsIgnoreCase(aActivity.getClass().getName())){
                    activityList.add(aActivity);
                }
            }
        }

        return activityList;
    }

    public List<Activity> getAllActivity(){
        if(activities == null){
            activities = new Stack<>();
        }
        return activities;
    }
    public void destroyActivity(Class clazz) {
        if(clazz == null){
            return;
        }

        if(activities != null && activities.size() > 0){
            for(int i=0; i<activities.size(); i++){
                Activity aActivity = activities.elementAt(i);
                if(clazz.getName().equalsIgnoreCase(aActivity.getClass().getName())){
                    aActivity.finish();
                    activities.remove(aActivity);
                }
            }
        }
    }

    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activities.remove(activity);
            activity = null;
        }
        LogUtil.info("ActivityManagement",
                "ActivityManagement 数量-->" + activities.size());
    }

    public void clearAllActivity() {
        while (true) {
            Activity activity = getCurrentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }
    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            clearAllActivity();
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } catch (Exception e) {
        }
    }
}
