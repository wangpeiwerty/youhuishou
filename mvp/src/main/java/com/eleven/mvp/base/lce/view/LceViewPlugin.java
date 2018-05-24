package com.eleven.mvp.base.lce.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.eleven.mvp.R;
import com.eleven.mvp.base.lce.ErrorMessage;
import com.hannesdorfmann.mosby3.mvp.lce.LceAnimator;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;


/**
 * Created by Administrator on 2016/12/22.
 */

public class LceViewPlugin{


    protected View baseView;
    protected View loadingView;
    protected ViewGroup contentView;
    protected ErrorView errorView;

    private Runnable errorProcessRunnable;

    protected LceViewHandler lceViewHandler;
    protected MvpLceView mvpLceView;

    public LceViewPlugin(LceViewHandler lceViewHandler, MvpLceView mvpLceView){
        this.lceViewHandler = lceViewHandler;
        this.mvpLceView = mvpLceView;
    }



    protected void setupBaseView(View view){

        if (view == null) {
            throw new NullPointerException(
                    "Base view is null!");
        }

        baseView = view;
        loadingView = view.findViewById(R.id.loadingView);
        contentView = (ViewGroup) view.findViewById(R.id.contentView);
        errorView = (ErrorView) view.findViewById(R.id.errorView);

        setupErrorView(errorView);

        if (loadingView == null) {
            throw new NullPointerException(
                    "Loading view is null! Have you specified a loading view in your layout xml file?"
                            + " You have to give your loading View the id R.id.loadingView");
        }

        if (contentView == null) {
            throw new NullPointerException(
                    "Content view is null! Have you specified a content view in your layout xml file?"
                            + " You have to give your content View the id R.id.contentView");
        }

        if (errorView == null) {
            throw new NullPointerException(
                    "Error view is null! Have you specified a content view in your layout xml file?"
                            + " You have to give your error View the id R.id.contentView");
        }

        errorView.setOnErrorProcessListener(new ErrorView.OnErrorProcessListener() {
            @Override
            public void onErrorProcessButtonClick() {
                onErrorViewClicked();
            }
        });
    }

    public void setupContentView(View view){
        contentView.addView(view);
    }

    public View getBaseView(){
        return baseView;
    }

    public void setupErrorView(ErrorView errorView) {
        this.errorView = errorView;
    }

    /**
     * Called if the error view has been clicked. To disable clicking on the errorView use
     * <code>errorView.setClickable(false)</code>
     */
    protected void onErrorViewClicked() {
        if (null != errorProcessRunnable){
            errorProcessRunnable.run();
            changeLceViewsOnErrorProcessed(loadingView,contentView,errorView);
        }else {
            mvpLceView.loadData(false);
        }
    }

    protected void changeLceViewsOnErrorProcessed(View loadingView, ViewGroup contentView, ErrorView errorView) {
        // todo
    }

    public void showLoading(boolean isHint) {

        if (!isHint) {
            animateLoadingViewIn();
        }

        // otherwise the pull to refresh widget will already display a loading animation
    }

    /**
     * Override this method if you want to provide your own animation for showing the loading view
     */
    protected void animateLoadingViewIn() {
        LceAnimator.showLoading(loadingView, contentView, errorView);
    }

    public void showContent() {
        animateContentViewIn();
    }

    /**
     * Called to animate from loading view to content view
     */
    protected void animateContentViewIn() {
        LceAnimator.showContent(loadingView, contentView, errorView);
    }

    /**
     * The default behaviour is to display a toast message as light error (i.e. pull-to-refresh
     * error).
     * Override this method if you want to display the light error in another way (like crouton).
     */
    protected void showLightError(String msg) {

        Toast.makeText(baseView.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showError(Throwable e, boolean pullToRefresh) {

        final ErrorMessage errorMsg = lceViewHandler.getErrorMessage(e, pullToRefresh, baseView.getContext());

        if (pullToRefresh) {
            showLightError(errorMsg.getHintText());
        } else {
            errorView.setErrorMessage(errorMsg.getHintText());
            errorView.setErrorHintImageViewBackgroundResource(errorMsg.getHintImage());
            errorView.setErrorProcessButtonText(errorMsg.getErrorProcessButtonText());
            if(errorMsg instanceof TokenErrorMessage){
                errorView.setOnLoginErrorProcessListener(new ErrorView.OnLoginErrorProcessListener() {
                    @Override
                    public void refresh() {
                        mvpLceView.loadData(false);
                    }
                }, ((TokenErrorMessage) errorMsg).getLoginAction());
            }
            errorProcessRunnable = errorMsg.getLceErrorProcessRunnable();
            animateErrorViewIn();
        }
    }

    /**
     * Animates the error view in (instead of displaying content view / loading view)
     */
    protected void animateErrorViewIn() {
        LceAnimator.showErrorView(loadingView, contentView, errorView);
    }

    public interface LceViewHandler{
        ErrorMessage getErrorMessage(Throwable e, boolean pullToRefresh, Context context);
    }




}
