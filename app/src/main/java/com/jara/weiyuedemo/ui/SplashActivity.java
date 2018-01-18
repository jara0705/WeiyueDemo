package com.jara.weiyuedemo.ui;

import com.jara.library.ui.BaseActivity;
import com.jara.library.util.SPUtils;
import com.jara.weiyuedemo.Constants;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.dao.ChannelDao;
import com.jara.weiyuedemo.model.ChannelVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-1-17.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int setContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initInstance() {
        initThreadHelper();
        threadHelper.executeDelay(new Runnable() {
            @Override
            public void run() {
                if (!SPUtils.getInstance(Constants.SP_KEY_CHANNEL).getBoolean(Constants.SP_KEY_CHANNEL, false)) {
                    List<ChannelVo> list = new ArrayList<>();
                    String[] newsTitle = getResources().getStringArray(R.array.news_channel_name);
                    String[] newsId = getResources().getStringArray(R.array.news_channel_id);
                    for (int i = 0; i < newsTitle.length; i++) {
                        ChannelVo channel = new ChannelVo();
                        channel.setNewsId(newsId[i]);
                        if (i<=4){
                            channel.setType(ChannelVo.TYPE_TOP);
                        }else {
                            channel.setType(ChannelVo.TYPE_BOTTOM);
                        }
                        channel.setTitle(newsTitle[i]);
                        channel.setId((i+1));
                        list.add(channel);
                    }
                    ChannelDao.insertsChannel(list);
                    SPUtils.getInstance(Constants.SP_KEY_CHANNEL).put(Constants.SP_KEY_CHANNEL, true);
                }
                GoActivity(MainActivity.class);
            }
        }, 1000);
    }
}
