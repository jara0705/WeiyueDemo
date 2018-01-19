package com.jara.weiyuedemo.utils;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.jara.weiyuedemo.R;

/**
 * Created by Administrator on 2018-1-16.
 */

public class MyLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.load_more_view;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return 0;
    }

//    @Override
//    public boolean isLoadEndGone() {
//        return true;
//    }

}
