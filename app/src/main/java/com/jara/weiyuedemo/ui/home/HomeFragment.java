package com.jara.weiyuedemo.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.jara.library.ui.BaseEvent;
import com.jara.library.ui.BaseFragment;
import com.jara.library.util.EventBusUtil;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.dao.ChannelDao;
import com.jara.weiyuedemo.model.ChannelVo;
import com.jara.weiyuedemo.model.event.ChannelEvent;
import com.jara.weiyuedemo.ui.adapter.ViewPagerAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-1-18.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;

    @Override
    protected int setContentViewResId() {
        EventBusUtil.register(this);
        return R.layout.fragment_home;
    }

    @Override
    protected void initInstance() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.clear();
        titles.clear();
        List<ChannelVo> dbList = ChannelDao.queryChannel(ChannelVo.TYPE_TOP);
        for (int i = 0; i < dbList.size(); i++) {
            fragments.add(NewsFragment.newInstance(dbList.get(i).getNewsId()));
            titles.add(dbList.get(i).getTitle());
        }

        ViewPagerAdapter videoAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(videoAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void lazyLoad() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelEvent event){
        if (event.code== BaseEvent.code_refresh){
            initInstance();
        }
    }

    @OnClick(R.id.ivAdd)
    public void onClick() {
        GoActivity(ChannelActivity.class);
    }

    @Override
    public void onDestroyView() {
        EventBusUtil.unregister(this);
        super.onDestroyView();
    }
}
