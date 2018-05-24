package com.eleven.mvp.base.lce;


import com.eleven.mvp.base.domain.UseCase;

/**
 * Created by Administrator on 2016/12/9.
 */

public class LoadDataMore implements LoadData{

    private LcePtrUlmView lcePtrUlmView;

    public LoadDataMore(LcePtrUlmView lcePtrUlmView){
        this.lcePtrUlmView = lcePtrUlmView;
    }

    @Override
    public void paramsFail() {
        if(lcePtrUlmView != null)
            lcePtrUlmView.loadMoreComplete(false);
    }

    @Override
    public void onError(Throwable e) {
        if(lcePtrUlmView != null){

            lcePtrUlmView.showError(e, true);
            lcePtrUlmView.loadMoreComplete(false);
        }
    }

    @Override
    public void onNext(Object dataList) {
        if(lcePtrUlmView != null){
            lcePtrUlmView.addData(dataList);
            lcePtrUlmView.showContent();
            lcePtrUlmView.loadMoreComplete(true);
        }
    }

    @Override
    public void addPageParams(UseCase.RequestPageValue requestPageValue) {
        requestPageValue.setPage(lcePtrUlmView.getPage() + 1);
        requestPageValue.setLimit(lcePtrUlmView.getLimit());

    }
}
