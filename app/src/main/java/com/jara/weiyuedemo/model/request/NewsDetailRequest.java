package com.jara.weiyuedemo.model.request;

import com.jara.http.request.GetRequest;
import com.jara.http.util.RequestUtil;
import com.jara.weiyuedemo.Api;

/**
 * Created by Administrator on 2018-1-16.
 */

public class NewsDetailRequest extends GetRequest {

    public String id;

    public NewsDetailRequest setId(String newsId) {
        this.id = newsId;
        return this;
    }

    @Override
    public String url() {
        return RequestUtil.getUtil().getUrl(Api.NEWS_DETAIL + id + Api.ENDDETAIL_URL);
    }

}
