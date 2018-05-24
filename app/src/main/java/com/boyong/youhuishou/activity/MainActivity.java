package com.boyong.youhuishou.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import com.boyong.youhuishou.R;
import com.boyong.youhuishou.activity.login.PasswordLoginFragment;
import com.boyong.youhuishou.web.CommonWebView;
import com.boyong.youhuishou.web.CommonWebViewFragment;

import com.eleven.mvp.base.BaseActivity;
import com.eleven.mvp.base.domain.BlankPresenter;
import com.eleven.mvp.widget.NavigateTabBar;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_NOTIFICATION= "我的";
    private static final String TAG_PAGE_CONTACTS = "通讯录";
    private static final String TAG_PAGE_MY = "我";

    //    退出时间
    private long exitTime = 0;

    @BindView(R.id.mainTabBar)
    NavigateTabBar mNavigateTabBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        //mImmersionBar.titleBar(toolbar).init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(PasswordLoginFragment.class, new NavigateTabBar.TabParam(R.mipmap.navigator_home, R.mipmap
                .navigator_home_p,TAG_PAGE_HOME));
        mNavigateTabBar.addTab(CommonWebViewFragment.class, new NavigateTabBar.TabParam(R.mipmap.navigator_home, R.mipmap
                .navigator_home_p,TAG_PAGE_NOTIFICATION));

        mNavigateTabBar.setTabSelectListener(new NavigateTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(NavigateTabBar.ViewHolder holder) {
                switch (holder.tag.toString()) {
//                    首页
                    case TAG_PAGE_HOME:

                        break;
//                    直播
                    case TAG_PAGE_NOTIFICATION:

                        break;
//                    视频
                    case TAG_PAGE_CONTACTS:

                        break;
//                    我的
                    case TAG_PAGE_MY:

                        break;
                }
                if(mNavigateTabBar!=null)
                    mNavigateTabBar.showFragment(holder);
            }
        });

    }


    /**
     * 拦截返回键，要求点击两次返回键才退出应用
     *
     * @param keyCode 按键代码
     * @param event   点击事件
     * @return 是否处理本次事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if(mNavigateTabBar.getCurrentSelectedTab()==1){
            CommonWebViewFragment homeFragment = (CommonWebViewFragment) mNavigateTabBar.getCurrentFragment();
            CommonWebView webView = homeFragment.getWebView();
            if (webView != null && webView.getWebClient().canGoBack()) {
                webView.goBack();
                return;
            }
        }
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showLongToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * 保存数据状态
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return BlankPresenter.INSTANCE;
    }

}
