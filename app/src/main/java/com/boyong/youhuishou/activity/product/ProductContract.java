package com.boyong.youhuishou.activity.product;

import com.eleven.mvp.base.ActivityHelperView;
import com.eleven.mvp.base.ActivityHintView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;


public interface ProductContract {

    interface View extends ActivityHintView, MvpView, ActivityHelperView {
        // TODO M class type
        void produceListSuccess();
    }

    interface Presenter extends MvpPresenter<View> {
        void produceList();
    }
}
