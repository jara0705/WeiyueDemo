package com.jara.weiyuedemo.presenter.impl;

import com.jara.weiyuedemo.presenter.INewsPresenter;
import com.jara.weiyuedemo.presenter.IPicPresenter;
import com.jara.weiyuedemo.presenter.IVideoPresenter;

/**
 * Created by Administrator on 2018-1-16.
 */

public class PrensenterFactory {
    public static INewsPresenter getNewsPresenter() {
        return new NewsPresenter();
    }

    public static IPicPresenter getPicPresenter() {
        return new PicPresenter();
    }

    public static IVideoPresenter getVideoPresenter() {
        return new VideoPresenter();
    }
}
