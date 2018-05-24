package com.boyong.youhuishou.web;


import com.eleven.mvp.base.domain.UseCase;


public class ShoucangRequestValue implements UseCase.RequestValues{

    // TODO 定义变量 
    private int errorMessageRes;
    private String contentId;
    private String contentType;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ShoucangRequestValue() {

    }

    public int getErrorMessageRes() {
        return errorMessageRes;
    }

    public void setErrorMessageRes(int errorMessageRes) {
        this.errorMessageRes = errorMessageRes;
    }

    @Override
    public boolean checkInput() {
        // TODO
        return true;
    }

    @Override
    public int getErrorStringRes() {
        return 0;
    }
}