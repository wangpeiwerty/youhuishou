package com.boyong.youhuishou.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.LogUtils;

import com.boyong.youhuishou.R;
import com.eleven.mvp.base.lce.view.ErrorView;
import com.eleven.mvp.util.DialogUtils;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * Created by Administrator on 2017/9/11.
 */

public class CommonWebView extends FrameLayout {
    private ProgressBar progressBar;
    private ErrorView errorView;
    private WebView webView;
    TitleChangeListener titleChangeListener;
    GetContentListener getContentListener;
    private String cur_url;

    private final static int HIDE_PROGRESS = 1;
    private Context context;
    public CommonWebView(Context context) {
        this(context, null);
        this.context=context;
        init();
    }

    public CommonWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    public void setTitleChangeListener(TitleChangeListener titleChangeListener) {
        this.titleChangeListener = titleChangeListener;
    }
    public void setGetContentListener(GetContentListener getContentListener){
        this.getContentListener=getContentListener;
    }

    private void init() {

        LayoutInflater.from(getContext()).inflate(R.layout.view_webview, this, true);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        errorView = (ErrorView) findViewById(R.id.error_view);
        webView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //自适应屏幕
        webSettings.setUseWideViewPort(true);

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(GONE);
                } else {
                    if (progressBar.getVisibility() == GONE)
                        progressBar.setVisibility(VISIBLE);
                    progressBar.setProgress(newProgress<10?10:newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return CommonWebView.this.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if(titleChangeListener!=null)
                    titleChangeListener.changeTitle(s.toLowerCase());
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return CommonWebView.this.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                CommonWebView.this.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                CommonWebView.this.onPageFinished(webView, s);
            }
        });
        webView.addJavascriptInterface(new WinStarBaseJsInterface(), "androidBaseInterface");


    }

    public interface TitleChangeListener{
        void changeTitle(String title);
    }

    public interface  GetContentListener{
        void getContent(String contentId, String contentType);
    }

    protected void onPageFinished(WebView webView, String s) {

    }

    private class WinStarBaseJsInterface{
        @JavascriptInterface
        public String getToken(){
            String token = null;
          /*  try {
                token = UserCacheImpl.getInstance().getFromDb().getTk();
            }catch (Exception e){
                JLog.e(e);
            }*/
            return token;
        }

        @JavascriptInterface
        public void requestLogin(final String url){
           /* autoLoginLayout.setLoginListener(getContext(), new LoginCallBack() {
                @Override
                public void loginSuccess() {
                    if(webView != null)
                        webView.loadUrl(url);
                }
            });*/
        }
    }


    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtils.e(String.format("=== load : %s ===", url));
        if(url.startsWith("http")) {
            if(url.endsWith("doc")){
                try {
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }catch (Exception e){
                    LogUtils.e(e);
                }
            }else{
                loadUrl(url);
            }
        }else {
            try {
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }catch (Exception e){
                LogUtils.e(e);
            }
        }
        return true;
    }

    public void onReceivedError(WebView view, int errorCode, String description, final String failingUrl) {
        LogUtils.e(String.format("=== load : %s, error:%s, description:%s ===", failingUrl, errorCode, description));
        errorView.setVisibility(View.VISIBLE);
        errorView.setErrorHintImageViewBackgroundResource(R.mipmap.ic_launcher);
        errorView.setErrorProcessButtonText("重新加载");
        errorView.setErrorMessage("加载失败");
        errorView.setOnErrorProcessListener(new ErrorView.OnErrorProcessListener() {
            @Override
            public void onErrorProcessButtonClick() {
                webView.reload();
                errorView.setVisibility(View.GONE);
            }
        });
    }

    public boolean onJsAlert(WebView view, String url, String message,
                             final JsResult result) {

        DialogUtils.showWebViewDialog(getContext(), message, new OnClickListener() {
            @Override
            public void onClick(View v) {
                result.confirm();
            }
        });
        return true;
    }

    public void loadUrl(String url){
        cur_url=url;
        webView.loadUrl(url);
    }

    public String getCur_url(){
        return  cur_url;
    }

    public void loadData(String data, String mimeType, String charset){
        webView.loadData(data, mimeType, charset);
    }

    public void goBack(){
        webView.goBack();
        if(errorView != null && errorView.getVisibility() == View.VISIBLE)
            errorView.setVisibility(View.GONE);
    }

    public WebView getWebClient() {
        return webView;
    }
}
