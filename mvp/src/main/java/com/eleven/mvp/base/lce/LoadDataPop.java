package com.eleven.mvp.base.lce;


import com.eleven.mvp.base.domain.UseCase;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

/**
 * Created by Administrator on 2016/12/9.
 */

public class LoadDataPop implements LoadData{

    private MvpLceView mvpLceView;

    public LoadDataPop(MvpLceView mvpLceView) {
        this.mvpLceView = mvpLceView;
    }

    @Override
    public void paramsFail() {

    }

    @Override
    public void onNext(Object dataList) {
        if(mvpLceView != null){
            mvpLceView.setData(dataList);
            mvpLceView.showContent();
        }
    }

    @Override
    public void addPageParams(UseCase.RequestPageValue requestPageValue) {
        requestPageValue.setPage(PageManager.FIRST_PAGE);
    }

    @Override
    public void onError(Throwable e) {
        if(mvpLceView != null){
            mvpLceView.showError(e, false);
        }
    }
}
