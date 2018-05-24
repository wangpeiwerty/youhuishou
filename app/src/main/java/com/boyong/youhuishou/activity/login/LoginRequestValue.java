package com.boyong.youhuishou.activity.login;

import android.text.TextUtils;

import com.boyong.youhuishou.R;
import com.eleven.mvp.base.domain.UseCase;


public class LoginRequestValue implements UseCase.RequestValues {
    private int errorResources;


    /**
     * 账号 app端相当于 电话号码
     */
    private String account;

    /**
     * 账号 app端相当于 电话号码
     */
    private String password;
    /**
     * 设备识别号 visitType=2||3，则为必传参数
     */
    private String alias;
    /**
     * 验证码
     */
    private String msgVerifyCode;
    /**
     * 短息验证码Id
     */
    private String msgVerifyId;
    /**
     * 登录类型
     */
    private int visitType;

    private String channel_id;
    private String channel_type = "Android";

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMsgVerifyCode() {
        return msgVerifyCode;
    }

    public void setMsgVerifyCode(String msgVerifyCode) {
        this.msgVerifyCode = msgVerifyCode;
    }

    public String getMsgVerifyId() {
        return msgVerifyId;
    }

    public void setMsgVerifyId(String msgVerifyId) {
        this.msgVerifyId = msgVerifyId;
    }

    public int getVisitType() {
        return visitType;
    }

    public void setVisitType(int visitType) {
        this.visitType = visitType;
    }


    @Override
    public boolean checkInput() {
        if (TextUtils.isEmpty(account)) {
            errorResources = R.string.error_phone_empty;
            return false;
        }
       /* if (!RegexUtils.isMobileExact(account)) {
            errorResources = R.string.error_phone_fail;
            return false;
        }*/
        if (TextUtils.isEmpty(password)) {
            errorResources = R.string.error_password_empty;
            return false;
        }
        if (password.length() < 6) {
            errorResources = R.string.error_password_fail;
            return false;
        }

        return true;
    }

    @Override
    public int getErrorStringRes() {
        return errorResources;
    }
}