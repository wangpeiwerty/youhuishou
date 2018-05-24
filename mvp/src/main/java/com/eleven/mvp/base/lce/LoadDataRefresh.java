package com.eleven.mvp.base.lce;


import com.eleven.mvp.base.domain.UseCase;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import static com.eleven.mvp.base.lce.PageManager.FIRST_PAGE;

/**
 * Created by Administrator on 2016/12/9.
 */

public class LoadDataRefresh implements LoadData{

    private LcePtrView lcePtrView;


    public LoadDataRefresh(MvpLceView lceView) {
        if(lceView instanceof LcePtrView)
            this.lcePtrView = (LcePtrView) lceView;
    }

    @Override
    public void paramsFail() {
        if(lcePtrView != null)
            lcePtrView.refreshComplete();
    }

    @Override
    public void onError(Throwable e) {

        if(lcePtrView != null) {
            lcePtrView.refreshComplete();
            lcePtrView.showError(e, true);
        }
    }

    @Override
    public void onNext(Object dataList) {
        if(lcePtrView != null) {
            lcePtrView.refreshComplete();
            lcePtrView.setData(dataList);
            lcePtrView.showContent();
        }
    }

    @Override
    public void addPageParams(UseCase.RequestPageValue requestPageValue) {
        requestPageValue.setPage(FIRST_PAGE);
    }
}
