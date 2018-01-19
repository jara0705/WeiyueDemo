package com.jara.weiyuedemo.ui.video;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.jara.library.ui.BaseFragment;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-1-19.
 */

public class VideoFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_video;
    }


    @Override
    protected void initInstance() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles=new ArrayList<>();
        String[] videoName=getResources().getStringArray(R.array.video_name);
        String[] videoId=getResources().getStringArray(R.array.video_id);
        for (int i = 0; i < videoName.length; i++) {
            fragments.add(VideoListFragment.newInstance(videoId[i]));
            titles.add(videoName[i]);
        }
        ViewPagerAdapter videoAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewpager.setAdapter(videoAdapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void lazyLoad() {

    }
}
