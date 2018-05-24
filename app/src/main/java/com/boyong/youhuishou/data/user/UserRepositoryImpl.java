package com.boyong.youhuishou.data.user;


import com.boyong.youhuishou.data.bean.ActivityModel;
import com.boyong.youhuishou.data.bean.CommonListModel;
import com.boyong.youhuishou.data.bean.UserModel;
import com.boyong.youhuishou.data.results.DataResult;

import io.reactivex.Observable;


public class UserRepositoryImpl implements UserRepository {

    private UserApi userApi;

    public UserRepositoryImpl(UserApi userApi) {
        this.userApi = userApi;
    }


    @Override
    public Observable<UserModel> login(String username, String password) {

        return userApi.login(username, password);
    }

    @Override
    public Observable<CommonListModel<ActivityModel>> activityList(String userid, String pageNo, String pageSize){
        return userApi.activityList(userid,pageNo,pageSize);
    }

    @Override
    public Observable<DataResult> productList() {
        return null;
    }


}
