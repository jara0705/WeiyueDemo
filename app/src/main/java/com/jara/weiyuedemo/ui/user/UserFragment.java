package com.jara.weiyuedemo.ui.user;

import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jara.library.ui.BaseFragment;
import com.jara.library.util.AppUtil;
import com.jara.library.util.CleanUtils;
import com.jara.library.util.SpanUtils;
import com.jara.weiyuedemo.MyApp;
import com.jara.weiyuedemo.R;
import com.jara.weiyuedemo.utils.IntentUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-1-19.
 */

public class UserFragment extends BaseFragment {

    @BindView(R.id.tvSelfCollection)
    TextView tvSelfCollection;
    @BindView(R.id.rlClearCache)
    RelativeLayout tvClearCache;
    @BindView(R.id.tvCacheSize)
    TextView tvCacheSize;
    @BindView(R.id.tvAbout)
    TextView tvAbout;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvUrl)
    TextView tvUrl;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initInstance() {
        try {
            tvCacheSize.setText(CleanUtils.getCacheSize(MyApp.getInstance().getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvVersion.setText(AppUtil.getAppVersionName());
        tvUrl.setMovementMethod(LinkMovementMethod.getInstance());
        tvUrl.setText(new SpanUtils()
                .append("GitHub:")
                .appendLine("https://github.com/DBxiaocao")
                .setUrl("https://github.com/DBxiaocao")
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        IntentUtil.shareUrl(activity,"https://github.com/DBxiaocao");
                    }
                }).create());
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.tvSelfCollection, R.id.rlClearCache, R.id.tvAbout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlClearCache:
                CleanUtils.cleanCustomCache(MyApp.getInstance().getCacheDir());
                tvCacheSize.setText("0Byte");
                break;
            case R.id.tvSelfCollection:
                GoActivity(CollectionActivity.class);
                break;
            case R.id.tvAbout:
                GoActivity(AboutActivity.class);
                break;
        }
    }
}
