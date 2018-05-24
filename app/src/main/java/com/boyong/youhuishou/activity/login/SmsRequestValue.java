package com.boyong.youhuishou.activity.login;

import android.text.TextUtils;

import com.blankj.utilcode.util.RegexUtils;
import com.boyong.youhuishou.R;
import com.eleven.mvp.base.domain.UseCase;



public class SmsRequestValue implements UseCase.RequestValues {

    // TODO 定义变量 
    private int errorMessageRes;
    private String username;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SmsRequestValue() {

    }

    public int getErrorMessageRes() {
        return errorMessageRes;
    }

    public void setErrorMessageRes(int errorMessageRes) {
        this.errorMessageRes = errorMessageRes;
    }

    @Override
    public boolean checkInput() {
        if (TextUtils.isEmpty(username)){
            errorMessageRes = R.string.error_phone_empty;
            return false;
        }

        if (!RegexUtils.isMobileExact(username)){
            errorMessageRes = R.string.error_phone_fail;
            return false;
        }
        return true;
    }

    @Override
    public int getErrorStringRes() {
        return errorMessageRes;
    }
}