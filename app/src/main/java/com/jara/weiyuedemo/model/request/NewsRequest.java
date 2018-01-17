package com.jara.weiyuedemo.model.request;

import com.jara.http.request.GetRequest;
import com.jara.http.util.RequestUtil;
import com.jara.weiyuedemo.Api;

/**
 * Created by Administrator on 2018-1-16.
 */

public class NewsRequest extends GetRequest {
    public String id;


    public int limit;

    public NewsRequest setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public NewsRequest setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String url() {
        return RequestUtil.getUtil().getUrl(Api.HTTP_NEWS_HEAD + id + "/" + limit + Api.END_URL);
    }
}
