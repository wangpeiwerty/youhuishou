package com.eleven.mvp.base.lce;


import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

/**
 * Created by Administrator on 2016/12/9.
 */

public class LoadDataFactory {

    public static LoadDataFactory mInstance;
    public static LoadDataFactory getInstance(){
        if(mInstance == null){
            synchronized(LoadDataFactory.class){
                if(mInstance == null)
                    mInstance = new LoadDataFactory();
            }
        }
        return mInstance;
    }
    private LoadDataFactory(){}
    // TODO 强制类型转换, 目前的LoadType由Activity发起,有依赖性.
    public LoadData getLoadData(LcePresenter.LoadType loadType, MvpLceView view){
        LoadData loadData;
        switch (loadType){
            case LOAD_MORE:
                loadData = new LoadDataMore((LcePtrUlmView) view);
                break;
            case LOAD_REFRESH:
                loadData = new LoadDataRefresh(view);
                break;
            default:
                loadData = new LoadDataPop(view);
                break;
        }
        return loadData;
    }

}
