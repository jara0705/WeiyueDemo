package com.jara.weiyuedemo.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jara.library.util.GlideUtils;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.model.News;

import java.util.List;

/**
 * Created by Administrator on 2018-1-18.
 */

public class NewsAdapter extends BaseAdapter<News, BaseViewHolder> {
    public NewsAdapter(@LayoutRes int layoutResId, @Nullable List<News> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        helper.setText(R.id.tvNewsTitle, item.getTitle());
        helper.setText(R.id.tvNewsSource, item.getSource());
        helper.setText(R.id.tvNewsTime, item.getPtime());
        GlideUtils.loadImageView(mContext, item.getImgsrc(), (ImageView) helper.getView(R.id.ivNews));
    }

}
