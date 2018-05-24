package com.boyong.youhuishou.activity.login;


import com.boyong.youhuishou.data.bean.ActivityModel;
import com.boyong.youhuishou.data.bean.CommonListModel;
import com.boyong.youhuishou.data.bean.UserModel;
import com.boyong.youhuishou.data.user.UserApi;
import com.boyong.youhuishou.R;
import com.boyong.youhuishou.ThrowableConsumer;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;


import io.reactivex.functions.Consumer;


public class LoginPresenter extends MvpNullObjectBasePresenter<LoginContract.View> implements LoginContract.Presenter {

    LoginUseCase useCase;
    ActivityListUseCase activityListUseCase;

    public LoginPresenter(LoginUseCase useCase, ActivityListUseCase activityListUseCase) {
        this.useCase = useCase;
        this.activityListUseCase=activityListUseCase;
    }

    @Override
    public void detachView() {
        super.detachView();
        useCase.unSubscribe();
        activityListUseCase.unSubscribe();
    }

    @Override
    public void login(String username, String password) {
        LoginRequestValue requestValue = new LoginRequestValue();
        requestValue.setAccount(username);
        requestValue.setPassword(password);
        if (!requestValue.checkInput()) {
            switch (requestValue.getErrorStringRes()) {
                case R.string.error_phone_empty:
                case R.string.error_phone_fail:
                    getView().usernameRequestFocus();
                    break;
                case R.string.error_password_empty:
                case R.string.error_password_fail:
                    getView().passwordRequestFocus();
                    break;
            }
            getView().showToast(requestValue.getErrorStringRes());
            return;
        }
        useCase.unSubscribe(UserApi.LOGIN_API_URL);
        getView().showProgressDialog(R.string.logining);
        useCase.execute(new Consumer<UserModel>() {
            @Override
            public void accept(UserModel o){
                getView().hideProgressDialogIfShowing();
                getView().showToast(R.string.login_success);
                getView().loginSuccess();
            }
        }, new ThrowableConsumer(getView().getActivityContext()) {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().hideProgressDialogIfShowing();
                super.accept(throwable);
            }
        }, requestValue);
    }

    @Override
    public void requestActivityList(String userid, String pageNo, String pageSize) {
        activityListUseCase.unSubscribe(UserApi.ACTIVITY_LIST_API_URL);
        ActivityListRequestValue rv = new ActivityListRequestValue();
        rv.setUserId(userid);
        rv.setPageNo(pageNo);
        rv.setPageSize(pageSize);
        getView().showProgressDialog(R.string.loading);
        activityListUseCase.execute(new Consumer<CommonListModel<ActivityModel>>() {
            @Override
            public void accept(CommonListModel<ActivityModel> listModel) throws Exception {
                getView().hideProgressDialogIfShowing();
                getView().showToast(R.string.loading_success);
                getView().requestActivityListSuccess(listModel);
            }
        }, new ThrowableConsumer(getView().getActivityContext()) {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().hideProgressDialogIfShowing();
                super.accept(throwable);
            }
        },rv);
    }


}
