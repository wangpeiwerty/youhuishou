package com.eleven.mvp.base.lce;


import com.eleven.mvp.base.domain.UseCase;

/**
 * Created by Administrator on 2016/12/9.
 */

public interface LoadData<M> {

    void paramsFail();

    void onError(Throwable e);

    void onNext(M dataList);

    void addPageParams(UseCase.RequestPageValue requestPageValue);

}
