package com.eleven.mvp.base.lce;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface LcePtrUlmView<M> extends LcePtrView<M> {
    void addData(M data);
    void loadMoreComplete(boolean isLoadSuccess);
    int getLimit();
    int getPage();
}
