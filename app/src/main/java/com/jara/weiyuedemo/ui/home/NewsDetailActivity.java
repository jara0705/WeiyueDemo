package com.jara.weiyuedemo.ui.home;

import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.jara.library.ui.BaseActivity;
import com.jara.library.ui.TitleView;
import com.jara.library.util.GlideUtils;
import com.jara.library.util.ToastUtils;
import com.jara.weiyuedemo.Constants;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.dao.CollectionDao;
import com.jara.weiyuedemo.model.CollectionVo;
import com.jara.weiyuedemo.model.NewsDetail;
import com.jara.weiyuedemo.model.event.NewsDetailEvent;
import com.jara.weiyuedemo.model.request.NewsDetailRequest;
import com.jara.weiyuedemo.presenter.INewsPresenter;
import com.jara.weiyuedemo.presenter.impl.PrensenterFactory;
import com.jara.weiyuedemo.utils.IntentUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-1-17.
 */

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.ivNewsImg)
    ImageView ivNewsImg;
    @BindView(R.id.tvNewsContent)
    TextView tvNewsContent;
    @BindView(R.id.tvNewsTitle)
    TextView tvNewsTitle;
    @BindView(R.id.toolbarLayout)
    CollapsingToolbarLayout toolbarLayout;
    private INewsPresenter mPresenter;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    protected void initInstance() {
        tvNewsTitle.setText(getIntent().getExtras().getString(Constants.NEWS_Title));
        toolbarLayout.setTitle(getIntent().getExtras().getString(Constants.NEWS_Title));
        GlideUtils.loadImageView(activity, getIntent().getExtras().getString(Constants.NEWS_Img), ivNewsImg);
        mPresenter = PrensenterFactory.getNewsPresenter();
        mPresenter.getNewsDetail(new NewsDetailRequest().setId(getIntent().getExtras().getString(Constants.NEWS_ID)));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NewsDetailEvent event) {
        switch (event.code) {
            case NewsDetailEvent.code_success:
                tvNewsContent.setText(Html.fromHtml(((NewsDetail) event.data).getBody()));
                break;
            case NewsDetailEvent.code_err:
                ToastUtils.showShort(activity, (String) event.data);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        CollectionVo dbVo= CollectionDao.queryImgUrl(getIntent().getExtras().getString(Constants.NEWS_Img));
        if (null !=dbVo && dbVo.getImgUrl().equals(getIntent().getExtras().getString(Constants.NEWS_Img))){
            menu.getItem(0).setTitle("取消收藏");
        }else {
            menu.getItem(0).setTitle("添加收藏");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCollection:
                CollectionVo dbVo=CollectionDao.queryImgUrl(getIntent().getExtras().getString(Constants.NEWS_Img));
                if (null !=dbVo && dbVo.getImgUrl().equals(getIntent().getExtras().getString(Constants.NEWS_Img))){
                    CollectionDao.deleteChannel(dbVo.getId());
                    ToastUtils.showShort(activity,"已取消收藏");
                }else {
                    CollectionVo vo = new CollectionVo();
                    vo.setType(CollectionVo.TYPE_News);
                    vo.setImgUrl(getIntent().getExtras().getString(Constants.NEWS_Img));
                    vo.setTitle(getIntent().getExtras().getString(Constants.NEWS_Title));
                    vo.setNewsId(getIntent().getExtras().getString(Constants.NEWS_ID));
                    vo.setId(CollectionDao.queryAll().size()+1);
                    CollectionDao.insert(vo);
                    ToastUtils.showShort(activity,"已添加收藏");
                }
                supportInvalidateOptionsMenu();
                break;
            case R.id.menuShare:
                IntentUtil.shareText(activity, getIntent().getExtras().getString(Constants.NEWS_Title), getIntent().getExtras().getString(Constants.NEWS_HTML));
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
