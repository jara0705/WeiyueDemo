package com.jara.weiyuedemo.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jara.library.ui.BaseEvent;
import com.jara.library.ui.BaseFragment;
import com.jara.library.util.ToastUtils;
import com.jara.weiyuedemo.Constants;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.model.News;
import com.jara.weiyuedemo.model.request.NewsRequest;
import com.jara.weiyuedemo.presenter.INewsPresenter;
import com.jara.weiyuedemo.presenter.impl.PrensenterFactory;
import com.jara.weiyuedemo.ui.adapter.NewsAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-1-18.
 */

public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private INewsPresenter mPresenter;
    private NewsAdapter newsAdapter;
    private List<News> newsList = new ArrayList<>();
    private int limit;

    public static NewsFragment newInstance(String newsId) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEWS_ID, newsId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initInstance() {
        newsAdapter = new NewsAdapter(R.layout.list_item_news, newsList);
        recycler.setAdapter(newsAdapter);
        swipeRefresh.setOnRefreshListener(this);
        mPresenter = PrensenterFactory.getNewsPresenter();
        newsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        newsAdapter.setPreLoadNumber(1);
        newsAdapter.setOnLoadMoreListener(this, recycler);
        newsAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void lazyLoad() {
        if (newsAdapter.getData().size() <= 0) {
            onRefresh();
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onRefresh() {
        limit = 0;
        swipeRefresh.setRefreshing(true);
        mPresenter.getNewsList(new NewsRequest().setId(getArguments().getString(Constants.NEWS_ID)).setLimit(limit));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEWS_Title, newsAdapter.getItem(position).getTitle());
        bundle.putString(Constants.NEWS_Img, newsAdapter.getItem(position).getImgsrc());
        bundle.putString(Constants.NEWS_HTML, newsAdapter.getItem(position).getUrl_3w());
        bundle.putString(Constants.NEWS_ID, newsAdapter.getItem(position).getPostid());
        GoActivity(NewsDetailActivity.class, bundle);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNewsList(new NewsRequest().setId(getArguments().getString(Constants.NEWS_ID)).setLimit(limit));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event.id == getArguments().getString(Constants.NEWS_ID)) {
            Logger.e("打印了数据" + event.code);
            switch (event.code) {
                case BaseEvent.code_success:
                    break;
                case BaseEvent.code_load_err:
                    newsAdapter.loadMoreEnd();
                    break;
                case BaseEvent.code_refresh:
                    limit += 20;
                    swipeRefresh.setRefreshing(false);
                    newsAdapter.getData().clear();
                    newsAdapter.addData((List<News>) event.data);
                    break;
                case BaseEvent.code_load:
                    limit += 20;
                    newsAdapter.addData((List<News>) event.data);
                    newsAdapter.loadMoreComplete();
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
}
