package com.boyong.youhuishou.data.user;

import com.boyong.youhuishou.data.bean.ActivityModel;
import com.boyong.youhuishou.data.bean.CommonListModel;
import com.boyong.youhuishou.data.bean.UserModel;
import com.boyong.youhuishou.data.results.DataResult;

import io.reactivex.Observable;



public interface UserRepository {
    /**
     * 登录
     */
    Observable<UserModel> login(String username, String password);

    /**
     * 活动
     */
    Observable<CommonListModel<ActivityModel>> activityList(String userid, String pageNo, String pageSize);

    /**
     *产品
     */
    Observable<DataResult> productList();
}
