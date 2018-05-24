package com.eleven.mvp.base.lce.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.eleven.mvp.R;
import com.eleven.mvp.base.BaseActivity;
import com.eleven.mvp.base.lce.LcePresenter;
import com.eleven.mvp.base.lce.LcePtrUlmView;
import com.eleven.mvp.base.lce.RefreshHeader;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/12/7.
 */

public abstract class BaseLcePtrUlmActivity<M, V extends LcePtrUlmView<M>, P extends LcePresenter<V>> extends BaseActivity<V, P>
        implements LcePtrUlmView<M>, LcePtrUlmViewPlugin.LcePtrUlmViewHandler{

    LcePtrUlmViewPlugin<M> lcePtrUlmViewPlugin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lcePtrUlmViewPlugin = new LcePtrUlmViewPlugin<>(this, this);
        lcePtrUlmViewPlugin.setupBaseView(getLayoutInflater().inflate(R.layout.activity_lce_base, null));
        setContentView(lcePtrUlmViewPlugin.getBaseView());
    }

    @Override
    public void setData(M data) {
        lcePtrUlmViewPlugin.setData(data);
    }

    @Override
    public void onLoadMore() {
        getPresenter().loadData(LcePresenter.LoadType.LOAD_MORE);
    }

    @Override
    public void addData(M data) {
        lcePtrUlmViewPlugin.addData(data);
    }

    @Override
    public void loadMoreComplete(boolean isLoadSuccess) {
        lcePtrUlmViewPlugin.loadMoreComplete(isLoadSuccess);
    }

    @Override
    public int getPage() {
        return lcePtrUlmViewPlugin.getPage();
    }

    @Override
    public void refreshComplete() {
        lcePtrUlmViewPlugin.refreshComplete();
    }

    @Override
    public boolean isRefreshing() {
        return lcePtrUlmViewPlugin.isRefreshing();
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
        lcePtrUlmViewPlugin.showLoading(pullToRefresh);
    }

    @Override
    public void showContent() {
        lcePtrUlmViewPlugin.showContent();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        lcePtrUlmViewPlugin.showError(e, pullToRefresh);
    }

    @Override
    public void setupPtrHeaderAndHandler(PtrFrameLayout ptrFrameLayout) {
        RefreshHeader refreshHeader = new RefreshHeader(this);
        ptrFrameLayout.addView(refreshHeader, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ptrFrameLayout.addPtrUIHandler(refreshHeader);
    }
}
