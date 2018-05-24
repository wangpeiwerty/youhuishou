package com.boyong.youhuishou.data.bean;

import java.io.Serializable;


public class UserModel implements Serializable{
    public String borrowCount = "";
    public String idCard = "";
    public String borrowStatus = "";
    public String headImg = "";
    public int useLimit = 0;
    public String status = "0";
    public String userId = "";
    public String inviteCode;
    public String realName = "";
    public String devName;
    public String creditStatus = "0000000000";
    public String mobile = "";

    //这些没有
    public String buttonStatus = "";
    public String msgUnreadCount = "0";
    public String hx_validator_session = "";

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public boolean isBorrow() {
        boolean result = false;

        if (borrowStatus.equals("Y")) {
            result = true;
        }
        return result;
    }

    public boolean isLogin() {
        return userId != null && !userId.equals("");
    }

    public boolean isIdAuth() {
        return !(creditStatus.toCharArray()[0] == '0');
    }

    public boolean isAuthPersonalInfo() {
        return !(creditStatus.toCharArray()[1] == '0');
    }

    public boolean isAuthBindBank() {
        return !(creditStatus.toCharArray()[2] == '0');
    }

    public boolean isCarrieroperator() {
        return !(creditStatus.toCharArray()[3] == '0');
    }

    public boolean isSesameAuth() {
        return !(creditStatus.toCharArray()[4] == '0');
    }

    public boolean isShowStatus() {
        boolean result = false;

        if (borrowStatus.equals("C")) {
            result = true;
        }
        return result;
    }

    public String showStatusStr() {
        String str = "";

        switch (borrowStatus) {
            case "C":
                str = "借款审核中";
                break;
        }

        return str;
    }


    public void updateUser(UserModel model) {
        this.borrowCount = model.borrowCount;
        this.idCard = model.idCard;
        this.headImg = model.headImg;
        this.useLimit = model.useLimit;
        this.status = model.status;
        this.userId = model.userId;
        this.realName = model.realName;
        this.creditStatus = model.creditStatus;
        this.mobile = model.mobile;
        this.borrowStatus = model.borrowStatus;
        this.buttonStatus = model.buttonStatus;
        this.msgUnreadCount = model.msgUnreadCount;
        this.mobile = model.mobile;

    }

    public int getMsgCount() {
        return Integer.parseInt(this.msgUnreadCount);
    }

    public boolean isShowSubmit() {
        return buttonStatus.equals("Y");
    }


    public boolean isRejust() {
        return status.equals("9");
    }

    public String getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(String borrowCount) {
        this.borrowCount = borrowCount;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getUseLimit() {
        return useLimit;
    }

    public void setUseLimit(int useLimit) {
        this.useLimit = useLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getButtonStatus() {
        return buttonStatus;
    }

    public void setButtonStatus(String buttonStatus) {
        this.buttonStatus = buttonStatus;
    }

    public String getMsgUnreadCount() {
        return msgUnreadCount;
    }

    public void setMsgUnreadCount(String msgUnreadCount) {
        this.msgUnreadCount = msgUnreadCount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHx_validator_session() {
        return hx_validator_session;
    }

    public void setHx_validator_session(String hx_validator_session) {
        this.hx_validator_session = hx_validator_session;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }
}
