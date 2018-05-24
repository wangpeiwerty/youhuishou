package com.eleven.mvp.base.lce;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by Administrator on 2016/12/5.
 */

public class RefreshHeader extends AppCompatTextView implements PtrUIHandler {

    private static final String TAG = "RefreshHeader";

    public RefreshHeader(Context context) {
        super(context);
        initView();
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setGravity(Gravity.CENTER);
        setPadding(30, 30, 30, 30);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        setText("下拉刷新 (,,• ₃ •,,) ");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        setText("努力刷新中(ง •̀_•́)ง ");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        if(ptrIndicator.getCurrentPercent() > 1) {
            setText("快放手 ∑(っ °Д °;)っ ");
        }else{
            setText("下拉刷新 (,,• ₃ •,,) ");
        }
    }
}
