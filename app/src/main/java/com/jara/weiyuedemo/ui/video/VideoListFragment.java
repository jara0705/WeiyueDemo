package com.jara.weiyuedemo.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jara.library.ui.BaseEvent;
import com.jara.library.ui.BaseFragment;
import com.jara.library.util.ToastUtils;
import com.jara.weiyuedemo.Constants;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.model.Video;
import com.jara.weiyuedemo.model.request.VideoRequest;
import com.jara.weiyuedemo.presenter.IVideoPresenter;
import com.jara.weiyuedemo.presenter.impl.PrensenterFactory;
import com.jara.weiyuedemo.ui.adapter.VideoAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;

/**
 * Created by Administrator on 2018-1-19.
 */

public class VideoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    private IVideoPresenter mPresenter;

    private List<Video> videoList = new ArrayList<>();
    private VideoAdapter listAdapter;
    private int limit;

    public static VideoListFragment newInstance(String index) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.VIDEO_ID, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initInstance() {
        listAdapter = new VideoAdapter(R.layout.list_item_video, videoList);
        mRecycler.setAdapter(listAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
        mPresenter = PrensenterFactory.getVideoPresenter();
        listAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        listAdapter.setPreLoadNumber(1);
        listAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (JCVideoPlayerManager.getFirstFloor() != null) {
                    JCVideoPlayer videoPlayer = JCVideoPlayerManager.getFirstFloor();
                    if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        JCVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        JCVideoPlayer.releaseAllVideos();
        if (listAdapter.getData().size() <= 0) {
            onRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event.id == getArguments().getString(Constants.VIDEO_ID)) {
            Logger.e("打印了数据" + event.code);
            switch (event.code) {
                case BaseEvent.code_success:
                    break;
                case BaseEvent.code_refresh:
                    limit += 20;
                    mSwipeRefresh.setRefreshing(false);
                    listAdapter.getData().clear();
                    listAdapter.addData((List<Video>) event.data);
                    break;
                case BaseEvent.code_load:
                    limit += 20;
                    listAdapter.addData((List<Video>) event.data);
                    listAdapter.loadMoreComplete();
                    break;
                case BaseEvent.code_err:
                    ToastUtils.showShort(activity, (String) event.data);
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        limit = 0;
        mSwipeRefresh.setRefreshing(true);
        mPresenter.getVideoList(new VideoRequest().setLimit(limit).setId(getArguments().getString(Constants.VIDEO_ID)));
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getVideoList(new VideoRequest().setLimit(limit).setId(getArguments().getString(Constants.VIDEO_ID)));
    }
}
