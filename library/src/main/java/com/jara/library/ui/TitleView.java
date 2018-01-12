package com.jara.library.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jara.library.BaseApplication;


/**
 * className: TitleView
 * author: lijun
 * date: 17/5/18 14:20
 */

public class TitleView {
    private Toolbar mToolbar;
    private AppCompatActivity activity;
    public TitleView(AppCompatActivity mActivity, View toolbar) {
        this.mToolbar = (Toolbar) toolbar;
        this.activity=mActivity;
        activity.setSupportActionBar(mToolbar);
    }

    public void setBack(final AppCompatActivity activity) {
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApplication.activityManagement.popActivity(activity);
            }
        });
    }

    public void setBack(AppCompatActivity activity, View.OnClickListener onClickListener) {
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(onClickListener);
    }

    public void setTitleText(CharSequence text) {
//        mToolbar.setTitle(text);
        activity.getSupportActionBar().setTitle(text);
    }

    public Toolbar getToolar(){
        return mToolbar;
    }

}
