package com.boyong.youhuishou.activity.login;

import com.boyong.youhuishou.data.bean.ActivityModel;
import com.boyong.youhuishou.data.bean.CommonListModel;
import com.eleven.mvp.base.ActivityHelperView;
import com.eleven.mvp.base.ActivityHintView;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;


public interface LoginContract {

    interface View extends ActivityHintView, MvpView, ActivityHelperView {
        void loginSuccess();
        void requestActivityListSuccess(CommonListModel<ActivityModel> listModel);
        void usernameRequestFocus();
        void passwordRequestFocus();
    }

    interface Presenter extends MvpPresenter<View> {
        void login(String username, String password);
        void requestActivityList(String userid, String pageNo, String pageSize);
    }
}
