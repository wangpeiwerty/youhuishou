package com.eleven.mvp.base.lce.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.eleven.mvp.R;
import com.eleven.mvp.base.BaseActivity;
import com.eleven.mvp.base.lce.LcePresenter;
import com.eleven.mvp.base.lce.LcePtrView;
import com.eleven.mvp.base.lce.RefreshHeader;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * Created by Administrator on 2016/12/22.
 */

public abstract class BaseLcePtrActivity<M, V extends LcePtrView<M>, P extends LcePresenter<V>> extends BaseActivity<V, P> implements
        LcePtrView<M>, LcePtrViewPlugin.LcePtrViewHandler{

    private LcePtrViewPlugin lcePtrViewPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lcePtrViewPlugin = new LcePtrViewPlugin(this, this);
        lcePtrViewPlugin.setupBaseView(getLayoutInflater().inflate(R.layout.activity_lce_base, null));
        setContentView(lcePtrViewPlugin.getBaseView());
    }

    @Override
    public void refreshComplete() {
        lcePtrViewPlugin.refreshComplete();
    }

    @Override
    public boolean isRefreshing() {
        return lcePtrViewPlugin.isRefreshing();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        // 如果ContentView 嵌套滑动布局需要重写该方法
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }
    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadData(pullToRefresh? LcePresenter.LoadType.LOAD_REFRESH: LcePresenter.LoadType.LOAD_POP);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        lcePtrViewPlugin.showLoading(pullToRefresh);
    }

    @Override
    public void showContent() {
        lcePtrViewPlugin.showContent();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        lcePtrViewPlugin.showError(e, pullToRefresh);
    }

    @Override
    public void setupPtrHeaderAndHandler(PtrFrameLayout ptrFrameLayout) {
        RefreshHeader refreshHeader = new RefreshHeader(this);
        ptrFrameLayout.addView(refreshHeader, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ptrFrameLayout.addPtrUIHandler(refreshHeader);
    }
}
