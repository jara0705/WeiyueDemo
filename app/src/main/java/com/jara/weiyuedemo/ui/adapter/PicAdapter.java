package com.jara.weiyuedemo.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jara.library.util.GlideUtils;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.model.Pics;

import java.util.List;

/**
 * Created by Administrator on 2018-1-19.
 */

public class PicAdapter extends BaseAdapter<Pics, BaseViewHolder> {

    public PicAdapter(int layoutResId, @Nullable List<Pics> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Pics item) {
        GlideUtils.loadImageView(mContext, item.getUrl(), (ImageView) helper.getView(R.id.ivPic));
    }
}
