package com.jara.weiyuedemo.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.jara.library.handler.FragmentHandler;
import com.jara.library.ui.BaseActivity;
import com.jara.library.ui.TitleView;
import com.jara.weiyuedemo.MyApp;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.ui.behavior.BottomNavigationViewHelper;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by Administrator on 2018-1-17.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.flContent)
    FrameLayout flContent;
    @BindView(R.id.navigationView)
    BottomNavigationView navigationView;
    private MenuItem prevMenuItem;
    private FragmentHandler fragmentHandler;
    private TitleView title;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        title = new TitleView(activity, findViewById(R.id.toolBar));
        title.setTitleText("文章");
    }

    @Override
    protected void initInstance() {
        initThreadHelper();
        fragmentHandler = new FragmentHandler(activity, getSupportFragmentManager());
        if (flContent != null) {
        }
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        title.setTitleText("文章");
                        JCVideoPlayer.releaseAllVideos();
                        break;
                    case R.id.itemVideo:
                        title.setTitleText("视频");
                        JCVideoPlayer.releaseAllVideos();
                        break;
                    case R.id.itemPicture:
                        title.setTitleText("美图");
                        JCVideoPlayer.releaseAllVideos();
                        break;
                    case R.id.itemUser:
                        title.setTitleText("");
                        JCVideoPlayer.releaseAllVideos();
                        break;
                }
                return false;
            }
        });
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        } else {
            prevMenuItem = navigationView.getMenu().getItem(0).setCheckable(true);
        }
        BottomNavigationViewHelper.disableShiftMode(navigationView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        ((MyApp) activity.getApplication()).activityManagement.clearAllActivity();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        fragmentHandler.destroyView();
        super.onDestroy();
    }
}
