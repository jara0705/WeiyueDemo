package com.jara.weiyuedemo.ui.home;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jara.library.ui.BaseActivity;
import com.jara.library.ui.TitleView;
import com.jara.weiyuedemo.Constants;
import com.jara.weiyuedemo.R;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-1-17.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolBar));
        title.setBack(this);
        title.setTitleText("就是个title");
    }

    @Override
    protected void initInstance() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webView.setWebChromeClient(new WebChromeClient());
        webView.clearCache(true);//支持缓存
        webView.loadUrl(getIntent().getExtras().getString(Constants.NEWS_HTML));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        try {
            webView.getClass().getMethod("onPause").invoke(webView,  (Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        try {
            webView.getClass().getMethod("onResume").invoke(webView,(Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
