package com.jara.weiyuedemo.ui.user;

import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.jara.library.ui.BaseActivity;
import com.jara.library.ui.TitleView;
import com.jara.library.util.SpanUtils;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.utils.IntentUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-1-19.
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.tvAboutProject)
    TextView tvAboutProject;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("关于");
    }

    @Override
    protected void initInstance() {
        tvAboutProject.setMovementMethod(LinkMovementMethod.getInstance());
        tvAboutProject.setText(new SpanUtils()
                .append("微阅是一款集新闻，视频和妹子的阅读类软件。如果您喜欢,欢迎star。开源地址:")
                .appendLine("https://github.com/DBxiaocao/Weiyue")
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        IntentUtil.shareUrl(activity,"https://github.com/DBxiaocao/Weiyue");
                    }
                })
                .setUrl("https://github.com/DBxiaocao/Weiyue")
                .create());

    }

}
