package com.eleven.mvp.base.lce.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Robert yao on 2016/11/9.
 */

public class ErrorView extends RelativeLayout {

    private ImageView errorHintIv;
    private TextView errorHintTv;
    private Button errorProcessButton;
    private OnErrorProcessListener onErrorProcessListener;
    private OnLoginErrorProcessListener onLoginErrorProcessListener;
    private LoginReceiver loginReceiver;
    private String action;

    public interface OnErrorProcessListener {
        void onErrorProcessButtonClick();
    }

    public interface OnLoginErrorProcessListener{
        void refresh();
    }

    public ErrorView(Context context) {
        super(context);
        initViews();
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }


    private void initViews() {
        errorHintIv = new ImageView(getContext());
        //errorHintIv.setId(View.generateViewId());
        errorHintTv = new TextView(getContext());
        //errorHintTv.setId(View.generateViewId());
        errorHintTv.setGravity(Gravity.CENTER);
        errorProcessButton = new Button(getContext());
        //errorProcessButton.setId(View.generateViewId());
        errorProcessButton.setTextColor(Color.WHITE);
        errorProcessButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onErrorProcessListener){
                    onErrorProcessListener.onErrorProcessButtonClick();
                }
            }
        });
        layoutViews();
    }

    private void layoutViews() {

        LayoutParams errorHintIvLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        errorHintIvLayoutParams.addRule(CENTER_IN_PARENT);
        addView(errorHintIv,errorHintIvLayoutParams);
        LayoutParams errorHintTvLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        errorHintTvLayoutParams.addRule(CENTER_HORIZONTAL);
        errorHintTvLayoutParams.addRule(BELOW, errorHintIv.getId());
        addView(errorHintTv,errorHintTvLayoutParams);
        LayoutParams errorProcessButtonLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        errorProcessButtonLayoutParams.addRule(CENTER_HORIZONTAL);
        errorProcessButtonLayoutParams.addRule(BELOW, errorHintTv.getId());
        addView(errorProcessButton,errorProcessButtonLayoutParams);

    }

    public void setErrorMessage(String errorMessage){
        errorHintTv.setText(errorMessage);
    }
    public void serErrorHintTvTextSize(float textSize){
        errorHintTv.setTextSize(textSize);
    }
    public void setErrorHintTvTextColor(int color){
        errorHintTv.setTextColor(getContext().getResources().getColor(color));
    }
    public void setErrorHintImageViewBackgroundResource(int  backgroundResource){
        errorHintIv.setImageResource(backgroundResource);
    }
    public void setErrorHintImageViewVisible(boolean isVisible){
        errorHintIv.setVisibility(isVisible ? VISIBLE : GONE);
    }
    public void setErrorProcessButtonText(String buttonText){
        errorProcessButton.setText(buttonText);
    }
    public void setErrorProcessButtonVisible(boolean isVisible){
        errorProcessButton.setVisibility(isVisible ? VISIBLE : GONE);
        if (isVisible){
            errorProcessButton.setOnClickListener(null);
        }
    }
    public void setErrorProcessButtonBackgroundResource(int backgroundResource){
        errorProcessButton.setBackgroundResource(backgroundResource);
    }
    public void setOnErrorProcessListener(OnErrorProcessListener onErrorProcessListener) {
        this.onErrorProcessListener = onErrorProcessListener;
    }

    public void setOnLoginErrorProcessListener(OnLoginErrorProcessListener onLoginErrorProcessListener, String action) {
        this.onLoginErrorProcessListener = onLoginErrorProcessListener;
        this.action = action;
        initLoginReceiver(action);
    }

    private void initLoginReceiver(String action){
        if(loginReceiver == null) {
            loginReceiver = new LoginReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            getContext().registerReceiver(loginReceiver, filter);
        }
    }

    public class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context mContext, Intent intent) {
            if(loginReceiver != null) {
                getContext().unregisterReceiver(loginReceiver);
                loginReceiver = null;
            }

            if(null != onLoginErrorProcessListener){
                onLoginErrorProcessListener.refresh();
            }

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(loginReceiver != null) {
            getContext().unregisterReceiver(loginReceiver);
            loginReceiver = null;
        }
    }
}
