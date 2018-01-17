package com.jara.weiyuedemo.presenter.impl;

import android.support.annotation.NonNull;

import com.jara.http.HttpUtils;
import com.jara.http.result.ResponseResult;
import com.jara.http.util.JsonUtil;
import com.jara.library.ui.BaseEvent;
import com.jara.library.util.EventBusUtil;
import com.jara.weiyuedemo.model.News;
import com.jara.weiyuedemo.model.NewsDetail;
import com.jara.weiyuedemo.model.event.NewsDetailEvent;
import com.jara.weiyuedemo.model.request.NewsDetailRequest;
import com.jara.weiyuedemo.model.request.NewsRequest;
import com.jara.weiyuedemo.presenter.INewsPresenter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-1-16.
 */

public class NewsPresenter implements INewsPresenter {
    @Override
    public void getNewsList(final NewsRequest request) {
        HttpUtils.async(request).map(new Function<ResponseResult, List<News>>() {
            @Override
            public List<News> apply(@NonNull ResponseResult responseResult) throws Exception {
                if (responseResult.success) {
                    Map<String, List<News>> map = JsonUtil.stringToCollect(responseResult.result);
                    return JsonUtil.toObjects(map.get(request.id).toString(), News.class);
                } else {
                    return null;
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<News>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<News> news) {
                if (news.isEmpty()) {
                    EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_load_err, "暂无更多", request.id));
                    return;
                }
                Collections.sort(news, new Comparator<News>() {
                    @Override
                    public int compare(News news, News t1) {
                        return t1.getPtime().compareTo(news.getPtime());
                    }
                });
                if (request.limit == 0)
                    EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_refresh, news, request.id));
                else
                    EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_load, news, request.id));

            }

            @Override
            public void onError(@NonNull Throwable e) {
                EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_err, e.getMessage(), request.id));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getNewsDetail(final NewsDetailRequest detailRequest) {
        HttpUtils.async(detailRequest).map(new Function<ResponseResult, NewsDetail>() {
            @Override
            public NewsDetail apply(@NonNull ResponseResult responseResult) throws Exception {
                if (responseResult.success) {
                    Map<String, NewsDetail> map = JsonUtil.stringToCollect(responseResult.result);
                    return JsonUtil.toObject(JsonUtil.GsonString(map.get(detailRequest.id)), NewsDetail.class);
                } else {
                    return null;
                }
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(new Observer<NewsDetail>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull NewsDetail news) {
                if (news != null)
                    EventBusUtil.sendEvent(new NewsDetailEvent(BaseEvent.code_success, news, detailRequest.id));
                else
                    EventBusUtil.sendEvent(new NewsDetailEvent(BaseEvent.code_err, "获取数据失败", detailRequest.id));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                EventBusUtil.sendEvent(new NewsDetailEvent(BaseEvent.code_err, e.getMessage(), detailRequest.id));
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
