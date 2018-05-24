package com.boyong.youhuishou.web;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.boyong.youhuishou.data.SPKey;
import com.boyong.youhuishou.data.bean.UserModel;
import com.boyong.youhuishou.Injection;
import com.boyong.youhuishou.R;
import com.eleven.mvp.base.BaseFragment;
import com.eleven.mvp.base.domain.BlankPresenter;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import butterknife.BindView;



public class CommonWebViewFragment extends BaseFragment{

    @BindView(R.id.common_webview)
    CommonWebView webView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(toolbar)
                .statusBarDarkFont(true)
                .flymeOSStatusBarFontColor(R.color.black)
                .init();
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return BlankPresenter.INSTANCE;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setVisibility(View.GONE);
        UserModel account = new Gson().fromJson(SPUtils.getInstance().getString(SPKey.LOGIN),UserModel.class);
        if(account!=null && !StringUtils.isEmpty(account.getUserId()))
            webView.loadUrl("http://soft.imtt.qq.com/browser/tes/feedback.html");
        webView.setTitleChangeListener(new CommonWebView.TitleChangeListener() {
            @Override
            public void changeTitle(String title) {

            }
        });
        webView.setGetContentListener(new CommonWebView.GetContentListener() {
            @Override
            public void getContent(final String contentId, final String contentType) {

                CommonWebViewFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    public CommonWebView getWebView() {
        return webView;
    }
}