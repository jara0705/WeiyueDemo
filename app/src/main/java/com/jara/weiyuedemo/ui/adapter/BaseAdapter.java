package com.jara.weiyuedemo.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jara.weiyuedemo.utils.MyLoadMoreView;

import java.util.List;

/**
 * Created by Administrator on 2018-1-18.
 */

public abstract class BaseAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        this.setLoadMoreView(new MyLoadMoreView());
    }

    protected int getItemPosition(T item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }
}
