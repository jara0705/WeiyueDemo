package com.jara.weiyuedemo.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2018-1-16.
 */

public class IntentUtil {
    public static void shareText(AppCompatActivity activity, String title, String url) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, new StringBuffer().append(title).append(url).toString());
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent, "分享到"));
    }

    public static void shareUrl(AppCompatActivity activity, String url){
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        activity.startActivity(intent);
    }
}
