package com.jara.http.request;

import com.google.gson.Gson;

import com.jara.http.util.RequestUtil;

import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2018-1-12.
 */

public abstract class PostRequest extends ARequest implements OkRequestEntity {

    @Override
    public Request getRequest() {
        return new Request.Builder().url(url()).post(RequestUtil.getUtil().getOkRequestBody(this)).build();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public Map<String, String> params() {
        return RequestUtil.getUtil().genMapParams(this);
    }

    @Override
    public RequestUtil.RequestMethod method() {
        return RequestUtil.RequestMethod.POST;
    }
}
