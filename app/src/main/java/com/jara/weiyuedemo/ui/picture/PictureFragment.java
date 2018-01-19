package com.jara.weiyuedemo.ui.picture;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jara.library.ui.BaseEvent;
import com.jara.library.ui.BaseFragment;
import com.jara.library.util.EventBusUtil;
import com.jara.library.util.ToastUtils;
import com.jara.weiyuedemo.Constants;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.model.Pics;
import com.jara.weiyuedemo.model.event.PicEvent;
import com.jara.weiyuedemo.model.request.PicRequest;
import com.jara.weiyuedemo.presenter.IPicPresenter;
import com.jara.weiyuedemo.presenter.impl.PrensenterFactory;
import com.jara.weiyuedemo.ui.adapter.PicAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-1-19.
 */

public class PictureFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private IPicPresenter iPicPresenter;
    private PicAdapter picAdapter;
    private int page = 1;
    private List<Pics> picses = new ArrayList<>();

    @Override
    public void onRefresh() {
        page = 1;
        swipeRefresh.setRefreshing(true);
        iPicPresenter.loadPic(new PicRequest().setPager_offset(page));
    }

    @Override
    public void onLoadMoreRequested() {
        if (picAdapter.getData().size() < 10) {
            return;
        }
        iPicPresenter.loadPic(new PicRequest().setPager_offset(page));
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void initInstance() {
        iPicPresenter = PrensenterFactory.getPicPresenter();
        picAdapter = new PicAdapter(R.layout.grid_item_pic, picses);
        recycler.setAdapter(picAdapter);
        recycler.setLayoutManager(new GridLayoutManager(activity, 2));
        swipeRefresh.setOnRefreshListener(this);
        picAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        picAdapter.setOnLoadMoreListener(this, recycler);
        picAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString(Constants.PHOTO_URL, picAdapter.getItem(position).getImgUrl());
                GoActivity(PictureActivity.class, bundle);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (picAdapter.getData().size() <= 0) {
            onRefresh();
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PicEvent event) {
        Logger.e("打印了数据" + event.code);
        switch (event.code) {
            case BaseEvent.code_success:
                break;
            case BaseEvent.code_load_err:
                picAdapter.loadMoreEnd();
                break;
            case BaseEvent.code_refresh:
                page++;
                swipeRefresh.setRefreshing(false);
                picAdapter.getData().clear();
                picAdapter.addData((List<Pics>) event.data);
                break;
            case BaseEvent.code_load:
                page++;
                picAdapter.addData((List<Pics>) event.data);
                picAdapter.loadMoreComplete();
                break;
            case BaseEvent.code_err:
                ToastUtils.showShort(activity, (String) event.data);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

}
