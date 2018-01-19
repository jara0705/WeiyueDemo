package com.jara.weiyuedemo.ui.home;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jara.library.ui.BaseActivity;
import com.jara.library.ui.BaseEvent;
import com.jara.library.ui.TitleView;
import com.jara.library.util.ButtonClickUtils;
import com.jara.library.util.EventBusUtil;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.dao.ChannelDao;
import com.jara.weiyuedemo.model.ChannelVo;
import com.jara.weiyuedemo.model.event.ChannelEvent;
import com.jara.weiyuedemo.ui.adapter.BaseAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by Administrator on 2018-1-17.
 */

public class ChannelActivity extends BaseActivity {

    private static final String TAG = "ChannelActivity";
    @BindView(R.id.checkRecycler)
    RecyclerView checkRecycler;
    @BindView(R.id.addRecycler)
    RecyclerView addRecycler;
    @BindColor(R.color.tab_unchecked)
    int chenckColor;
    @BindColor(R.color.tab_checked)
    int unColor;

    private List<ChannelVo> checkStrs = new ArrayList<>();
    private List<ChannelVo> addStrs = new ArrayList<>();
    private CheckAdapter checkAdapter;
    private AddAdapter addAdapter;

    @Override
    protected int setContentViewResId() {
        Logger.d("这是ChannleActivity");
        return R.layout.activity_channel;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("频道管理");
    }

    @Override
    protected void initInstance() {
        checkStrs = ChannelDao.queryChannel(ChannelVo.TYPE_TOP);
        addStrs = ChannelDao.queryChannel(ChannelVo.TYPE_BOTTOM);
        checkRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        addRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        checkAdapter = new CheckAdapter(R.layout.grid_item_tabs, checkStrs);
        checkRecycler.setAdapter(checkAdapter);
        addAdapter = new AddAdapter(R.layout.grid_item_tabs, addStrs);
        addRecycler.setAdapter(addAdapter);
    }

    private class CheckAdapter extends BaseAdapter<ChannelVo, BaseViewHolder> {
        public CheckAdapter(@LayoutRes int layoutResId, @Nullable List<ChannelVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ChannelVo item) {
            TextView tvTab = helper.getView(R.id.tvTab);
            tvTab.setText(item.getTitle());
            if (getItemPosition(item) > 4) {
                tvTab.setTextColor(chenckColor);
            } else {
                tvTab.setTextColor(unColor);
            }
            tvTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ButtonClickUtils.isFastDoubleClick()) {
                        return;
                    }
                    if (getItemPosition(item) > 4) {
                        item.setType(ChannelVo.TYPE_BOTTOM);
                        remove(getItemPosition(item));
                        addAdapter.addData(0, item);
                        ChannelDao.updateChannel(item);
                        EventBusUtil.sendEvent(new ChannelEvent(BaseEvent.code_refresh, null, null));
                    }
                }
            });
        }
    }

    private class AddAdapter extends BaseAdapter<ChannelVo, BaseViewHolder> {
        public AddAdapter(@LayoutRes int layoutResId, @Nullable List<ChannelVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ChannelVo item) {
            TextView tvTab = helper.getView(R.id.tvTab);
            tvTab.setText(item.getTitle());
            tvTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ButtonClickUtils.isFastDoubleClick()) {
                        return;
                    }
                    item.setType(ChannelVo.TYPE_TOP);
                    ChannelDao.updateChannel(item);
                    remove(getItemPosition(item));
                    checkAdapter.addData(item);
                    EventBusUtil.sendEvent(new ChannelEvent(BaseEvent.code_refresh, null, null));
                }
            });
        }

    }
}
