package com.boyong.youhuishou.activity.product;


import com.eleven.mvp.base.domain.UseCase;

public class ProductRequestValue implements UseCase.RequestValues {
    private int errorMessageRes;

    @Override
    public int getErrorStringRes() {
        return errorMessageRes;
    }

    public void setErrorMessageRes(int errorMessageRes) {
        this.errorMessageRes = errorMessageRes;
    }

    @Override
    public boolean checkInput() {
        // TODO check Input params

        return true;
    }
}