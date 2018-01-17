package com.jara.weiyuedemo.utils;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * Created by Administrator on 2018-1-16.
 */

public class MyLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected int getLoadingViewId() {
        return 0;
    }

    @Override
    protected int getLoadFailViewId() {
        return 0;
    }

    @Override
    protected int getLoadEndViewId() {
        return 0;
    }
}
