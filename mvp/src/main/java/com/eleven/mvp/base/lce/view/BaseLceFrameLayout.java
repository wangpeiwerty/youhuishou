package com.eleven.mvp.base.lce.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.eleven.mvp.R;
import com.eleven.mvp.base.lce.LcePresenter;
import com.hannesdorfmann.mosby3.mvp.layout.MvpFrameLayout;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;


/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class BaseLceFrameLayout<M, V extends MvpLceView<M>, P extends LcePresenter<V>> extends MvpFrameLayout<V,P> implements LceViewPlugin.LceViewHandler, MvpLceView<M> {

    private LceViewPlugin lceViewPlugin;

    public BaseLceFrameLayout(Context context) {
        super(context);
        init();
    }

    public BaseLceFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseLceFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseLceFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        lceViewPlugin = new LceViewPlugin(this, this);
    }

    @Override
    public void addView(View child) {
        if(getChildCount() > 0){
            throw new IllegalArgumentException("BaseLceFrameLayout can host only one direct child");
        }
        lceViewPlugin.setupBaseView(LayoutInflater.from(getContext()).inflate(R.layout.activity_lce_base, this, false));
        super.addView(lceViewPlugin.getBaseView());
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
    public void
    showError(Throwable e, boolean pullToRefresh) {
        lceViewPlugin.showError(e, pullToRefresh);
    }

    public void setupContentView(View view) {
        lceViewPlugin.setupContentView(view);
    }
}
