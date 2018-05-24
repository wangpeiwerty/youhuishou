package com.eleven.mvp.base;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.eleven.mvp.base.domain.BlankPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceActivity;


/**
 * Created by Administrator on 2018/1/17.
 */

public class BaseLceActivity extends MvpLceActivity {

    @Override
    protected String getErrorMessage(Throwable throwable, boolean b) {
        return null;
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @NonNull
    @Override
    protected View createLoadingView() {
        return super.createLoadingView();
    }

    @NonNull
    @Override
    protected View createContentView() {
        return super.createContentView();
    }

    @NonNull
    @Override
    protected TextView createErrorView() {
        return super.createErrorView();
    }

    @Override
    protected void onErrorViewClicked() {
        super.onErrorViewClicked();
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
    }

    @Override
    protected void animateLoadingViewIn() {
        super.animateLoadingViewIn();
    }

    @Override
    public void showContent() {
        super.showContent();
    }

    @Override
    protected void animateContentViewIn() {
        super.animateContentViewIn();
    }

    @Override
    protected void showLightError(String msg) {
        super.showLightError(msg);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
    }

    @Override
    protected void animateErrorViewIn() {
        super.animateErrorViewIn();
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return BlankPresenter.INSTANCE;
    }

    @Override
    public void setData(Object o) {

    }

    @Override
    public void loadData(boolean b) {

    }
}
