package com.jara.weiyuedemo.presenter.impl;

import android.support.annotation.NonNull;

import com.jara.http.HttpUtils;
import com.jara.http.result.ResponseResult;
import com.jara.http.util.JsonUtil;
import com.jara.library.ui.BaseEvent;
import com.jara.library.util.EventBusUtil;
import com.jara.weiyuedemo.model.Pics;
import com.jara.weiyuedemo.model.event.PicEvent;
import com.jara.weiyuedemo.model.request.PicRequest;
import com.jara.weiyuedemo.presenter.IPicPresenter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018-1-16.
 */

public class PicPresenter implements IPicPresenter {
    @Override
    public void loadPic(final PicRequest request) {
        HttpUtils.async(request).map(new Function<ResponseResult, List<Pics>>() {
            @Override
            public List<Pics> apply(@NonNull ResponseResult responseResult) throws Exception {
                JSONObject obj = new JSONObject(responseResult.result);
                String result;
                if (obj != null && obj.has("results")) {
                    result = obj.getString("results");
                    return JsonUtil.toObjects(result, Pics.class);
                } else {
                    return new ArrayList<>();
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Pics>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Pics> picses) {
                        if (picses.isEmpty()) {
                            EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_load_err, "暂无更多", null));
                            return;
                        }
                        if (request.pager_offset == 1) {
                            EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_refresh, picses, null));
                        } else {
                            EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_load, picses, null));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_err, e.getMessage(), null));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
