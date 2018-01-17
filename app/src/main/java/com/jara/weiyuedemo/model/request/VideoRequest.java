package com.jara.weiyuedemo.model.request;

import com.jara.http.request.GetRequest;
import com.jara.http.util.RequestUtil;
import com.jara.weiyuedemo.Api;

/**
 * Created by Administrator on 2018-1-16.
 */

public class VideoRequest extends GetRequest {
    public int limit;
    public String id;

    public VideoRequest setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public VideoRequest setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String url() {
        return RequestUtil.getUtil().getUrl(Api.HTTP_VIDEO_HEAD + id + "/y/" + limit + Api.END_URL);
    }
}
