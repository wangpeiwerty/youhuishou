package com.boyong.youhuishou.web;


import com.eleven.mvp.base.ActivityHelperView;
import com.eleven.mvp.base.ActivityHintView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;


public interface ShoucangContract {
    interface View extends ActivityHintView, MvpView, ActivityHelperView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}