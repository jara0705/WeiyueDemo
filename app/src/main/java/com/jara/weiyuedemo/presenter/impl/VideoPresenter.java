package com.jara.weiyuedemo.presenter.impl;

import android.support.annotation.NonNull;

import com.jara.http.HttpUtils;
import com.jara.http.result.ResponseResult;
import com.jara.http.util.JsonUtil;
import com.jara.library.ui.BaseEvent;
import com.jara.library.util.EventBusUtil;
import com.jara.weiyuedemo.model.Video;
import com.jara.weiyuedemo.model.request.VideoRequest;
import com.jara.weiyuedemo.presenter.IVideoPresenter;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-1-16.
 */

public class VideoPresenter implements IVideoPresenter {
    @Override
    public void getVideoList(final VideoRequest request) {
        HttpUtils.async(request).map(new Function<ResponseResult, List<Video>>() {
            @Override
            public List<Video> apply(@NonNull ResponseResult responseResult) throws Exception {
                if (responseResult.success) {
                    Map<String, List<Video>> map = JsonUtil.stringToCollect(responseResult.result);
                    return JsonUtil.toObjects(map.get(request.id).toString(), Video.class);
                } else {
                    return null;
                }
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(new Observer<List<Video>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Video> tests) {
                if (tests.isEmpty()) {
                    EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_err, "暂无数据", request.id));
                } else {
                    if (request.limit == 0)
                        EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_refresh, tests, request.id));
                    else
                        EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_load, tests, request.id));
                }
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
}
