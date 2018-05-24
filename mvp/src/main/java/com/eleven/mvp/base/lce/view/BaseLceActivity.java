package com.eleven.mvp.base.lce.view;

import android.os.Bundle;
import android.view.View;


import com.eleven.mvp.R;
import com.eleven.mvp.base.BaseActivity;
import com.eleven.mvp.base.lce.LcePresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;


/**
 * Created by Administrator on 2016/12/22.
 */

public abstract class BaseLceActivity<M, V extends MvpLceView<M>, P extends LcePresenter<V>> extends BaseActivity<V,P> implements LceViewPlugin.LceViewHandler, MvpLceView<M> {

    private LceViewPlugin lceViewPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lceViewPlugin = new LceViewPlugin(this, this);
        lceViewPlugin.setupBaseView(getLayoutInflater().inflate(R.layout.activity_lce_base, null));
        setContentView(lceViewPlugin.getBaseView());
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadData(pullToRefresh? LcePresenter.LoadType.LOAD_REFRESH: LcePresenter.LoadType.LOAD_POP);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        lceViewPlugin.showLoading(pullToRefresh);
    }

    @Override
    public void showContent() {
        lceViewPlugin.showContent();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        lceViewPlugin.showError(e, pullToRefresh);
    }

    public void setupContentView(View view) {
        lceViewPlugin.setupContentView(view);
    }
}
