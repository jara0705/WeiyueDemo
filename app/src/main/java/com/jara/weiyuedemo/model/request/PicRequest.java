package com.jara.weiyuedemo.model.request;

import com.jara.http.request.GetRequest;
import com.jara.http.util.RequestUtil;
import com.jara.weiyuedemo.Api;

/**
 * Created by Administrator on 2018-1-16.
 */

public class PicRequest extends GetRequest {

    public int pager_offset;
    public PicRequest setPager_offset(int pager_offset) {
        this.pager_offset = pager_offset;
        return this;
    }

    @Override
    public String url() {
        return RequestUtil.getUtil().getHtmlUrl(Api.PIC_URL + pager_offset);
    }
}
