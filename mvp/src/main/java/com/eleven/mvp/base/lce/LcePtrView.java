package com.eleven.mvp.base.lce;


import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface LcePtrView<M> extends MvpLceView<M> {


    void refreshComplete();
    boolean isRefreshing();
}
