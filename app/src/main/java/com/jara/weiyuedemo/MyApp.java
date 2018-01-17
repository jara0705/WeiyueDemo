package com.jara.weiyuedemo;

import com.jara.http.HttpUtils;
import com.jara.http.util.RequestUtil;
import com.jara.library.BaseApplication;
import com.jara.weiyuedemo.dao.ChannelDao;
import com.jara.weiyuedemo.dao.CollectionDao;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Created by Administrator on 2018-1-16.
 */

public class MyApp extends BaseApplication {
    private static final String DB_NAME_CHANNEL = "channel.db";
    private static final String DB_NAME_COLLECTION = "collection.db";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        HttpUtils.init(Api.NETEAST_HOST, instance);
        RequestUtil.getUtil().setServerUrl(Api.NETEAST_HOST);
        ChannelDao.initDatabase(DB_NAME_CHANNEL);
        CollectionDao.initDatabase(DB_NAME_COLLECTION);
    }

}
