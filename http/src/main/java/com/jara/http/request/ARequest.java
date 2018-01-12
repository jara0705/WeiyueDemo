package com.jara.http.request;

import com.jara.http.util.RequestUtil;

import java.util.Map;

/**
 * Created by Administrator on 2018-1-12.
 */

public abstract class ARequest {
    /*请求方法：get、post。。。*/
    public abstract RequestUtil.RequestMethod method();
    public abstract Map<String,String> params();
    public abstract String url();

}
