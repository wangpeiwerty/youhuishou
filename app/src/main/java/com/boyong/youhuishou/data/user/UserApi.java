package com.boyong.youhuishou.data.user;

import com.boyong.youhuishou.data.BaseApi;
import com.boyong.youhuishou.data.MD5Util;
import com.boyong.youhuishou.data.bean.ActivityModel;
import com.boyong.youhuishou.data.bean.CommonListModel;
import com.boyong.youhuishou.data.bean.UserModel;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


public class UserApi extends BaseApi {

    public final static String LOGIN_API_URL = "account/login";
    public final static String LOGOUT_API_URL = "loginSignOut";
    public static final String ACTIVITY_LIST_API_URL = "activity/list";
    public static final String PRODUCT_LIST = "product/list";

    String API_SERVICE;

    public UserApi(String API_SERVICE) {
        this.API_SERVICE = API_SERVICE;
    }


    public Observable<UserModel> login(String mobile, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", MD5Util.getMD5(password));
        params.put("md5Sign", MD5Util.getMD5(mobile, password));
        return requestPost(LOGIN_API_URL, params, new TypeToken<UserModel>() {
        }.getType());
    }

    public Observable<CommonListModel<ActivityModel>> activityList(String userid, String pageNo, String pageSize) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userid);
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("md5Sign", MD5Util.getMD5(userid, pageNo, pageSize));
        return requestPost(ACTIVITY_LIST_API_URL,params,new TypeToken<CommonListModel<ActivityModel>>(){}.getType());
    }


    @Override
    public String getApiService() {
        return API_SERVICE;
    }
}
