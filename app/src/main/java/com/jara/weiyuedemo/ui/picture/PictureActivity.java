package com.jara.weiyuedemo.ui.picture;

import com.jara.library.ui.BaseActivity;
import com.jara.library.ui.TitleView;
import com.jara.library.util.GlideUtils;
import com.jara.weiyuedemo.Constants;
import com.jara.weiyuedemo.R;

import uk.co.senab.photoview.PhotoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-1-19.
 */

public class PictureActivity extends BaseActivity {

    @BindView(R.id.ivPhoto)
    PhotoView photoView;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_picture;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("图片详情");
    }

    @Override
    protected void initInstance() {
        GlideUtils.loadImageView(activity, getIntent().getExtras().getString(Constants.PHOTO_URL)
                , photoView);
    }

    @OnClick(R.id.ivPhoto)
    public void onClick() {
        finish();
    }
}
