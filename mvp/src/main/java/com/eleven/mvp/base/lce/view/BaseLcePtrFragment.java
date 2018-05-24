package com.eleven.mvp.base.lce.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.eleven.mvp.R;
import com.eleven.mvp.base.BaseFragment;
import com.eleven.mvp.base.lce.LcePresenter;
import com.eleven.mvp.base.lce.LcePtrView;
import com.eleven.mvp.base.lce.RefreshHeader;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static java.security.AccessController.getContext;

/**
 * Created by Robert yao on 2016/11/10.
 */

public abstract class BaseLcePtrFragment<M, V extends LcePtrView<M>, P extends LcePresenter<V>> extends BaseFragment<V,P>
        implements LcePtrView<M>, LcePtrViewPlugin.LcePtrViewHandler{

    protected LcePtrViewPlugin lcePtrViewPlugin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lcePtrViewPlugin = new LcePtrViewPlugin(this, this);
        lcePtrViewPlugin.setupBaseView(inflater.inflate(R.layout.fragment_lce_base, container, false));
        return lcePtrViewPlugin.getBaseView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lcePtrViewPlugin = null;
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
        RefreshHeader refreshHeader = new RefreshHeader(getContext());
        ptrFrameLayout.addView(refreshHeader, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ptrFrameLayout.addPtrUIHandler(refreshHeader);
    }

}
