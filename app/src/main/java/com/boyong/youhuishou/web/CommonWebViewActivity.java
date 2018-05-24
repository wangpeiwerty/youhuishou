package com.boyong.youhuishou.web;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.boyong.youhuishou.R;
import com.eleven.mvp.base.BaseActivity;
import com.eleven.mvp.base.domain.BlankPresenter;
import com.eleven.mvp.base.lce.ToolbarManager;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import butterknife.BindView;


public class CommonWebViewActivity extends BaseActivity {

    @BindView(R.id.common_webview)
    CommonWebView webView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return BlankPresenter.INSTANCE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToolbarManager.with(this).title(getResources().getString(R.string.app_name)).setNavigationIcon(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //this.setSupportActionBar(toolbar);
        String url = getIntent().getStringExtra("data");
        String title = getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(title)){
            setTitle(title);
        }
        LogUtils.e(url);
        webView.loadUrl(url);
        webView.setTitleChangeListener(new CommonWebView.TitleChangeListener() {
            @Override
            public void changeTitle(String title) {
                setTitle(title);
            }
        });
        webView.setGetContentListener(new CommonWebView.GetContentListener() {
            @Override
            public void getContent(final String contentId, final String contentType) {

                CommonWebViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    public void setTitle(String title){
        if (toolbar == null) return;
        if (TextUtils.isEmpty(title)) {
            ((TextView) toolbar.findViewById(R.id.title_content_tv)).setText(getResources().getString(R.string.app_name));
        } else {
            ((TextView) toolbar.findViewById(R.id.title_content_tv)).setText(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.getWebClient().canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(toolbar).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
