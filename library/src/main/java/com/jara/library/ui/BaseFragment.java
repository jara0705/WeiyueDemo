package com.jara.library.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jara.library.handler.FragmentHandler;
import com.jara.library.util.EventBusUtil;
import com.jara.library.util.ThreadHelperFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * description: BaseFragment
 * author: xiaocao
 * date: 17/6/26 下午6:16
 */
public abstract class BaseFragment extends Fragment {
    //视图是否已经初初始化
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected AppCompatActivity activity;
    protected View rootView;
    protected FragmentHandler fragmentHandler;
    protected ThreadHelperFactory.ThreadHelper threadHelper;

    public ThreadHelperFactory.ThreadHelper getThreadHelper() {
        return threadHelper;
    }

    public void initThreadHelper() {
        threadHelper = ThreadHelperFactory.getInstance().createUIThreadHelper(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setContentViewResId(), container, false);
        }
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        ButterKnife.bind(this, rootView);
        initInstance();
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        return rootView;
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    protected abstract int setContentViewResId();

    protected abstract void initInstance();

    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {

    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
        if (fragmentHandler != null)
            fragmentHandler.destroyView();
        if (threadHelper != null)
            threadHelper.shutdown();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    /**
     * 跳转到下一个Activity
     *
     * @param cls
     */
    public void GoActivity(Class<?> cls) {

        Intent i1 = new Intent();
        i1.setClass(activity, cls);
        activity.startActivity(i1);
    }

    /**
     * @param cls
     * @param
     */
    public void GoActivity(Class<?> cls, Bundle bundle) {

        Intent i1 = new Intent();
        if (null != bundle) {
            i1.putExtras(bundle);
        }
        i1.setClass(activity, cls);
        activity.startActivity(i1);
    }

    /**
     * @param cls
     * @param
     */
    public void GoActivity(Class<?> cls, Bundle bundle, int requestCode) {

        Intent i1 = new Intent();
        if (null != bundle) {
            i1.putExtras(bundle);
        }
        i1.setClass(activity, cls);
        activity.startActivityForResult(i1, requestCode);
    }
}
