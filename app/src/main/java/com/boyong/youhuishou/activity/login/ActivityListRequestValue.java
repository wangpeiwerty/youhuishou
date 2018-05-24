package com.boyong.youhuishou.activity.login;


import com.eleven.mvp.base.domain.UseCase;

class ActivityListRequestValue implements UseCase.RequestValues {

    private String pageNo;
    private String pageSize;
    private String userId;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean checkInput() {
        return false;
    }

    @Override
    public int getErrorStringRes() {
        return 0;
    }
}
