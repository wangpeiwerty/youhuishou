package com.eleven.mvp.base.lce;



import com.eleven.mvp.base.ActivityHintView;
import com.eleven.mvp.base.domain.UseCase;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import io.reactivex.functions.Consumer;


/**
 * Created by Administrator on 2016/12/8.
 */

public abstract class BaseLcePresenter<V extends MvpLceView> extends MvpBasePresenter<V> implements LcePresenter<V> {

    private UseCase useCase;
    private LoadData loadData;

    public BaseLcePresenter(UseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void loadData(final LoadType loadType) {
        if (!isViewAttached()){
            return;
        }

        loadData = LoadDataFactory.getInstance().getLoadData(loadType, getView());

        UseCase.RequestValues requestValues = generateRequestValue(loadType);
        if(requestValues != null) {

            if(requestValues instanceof UseCase.RequestPageValue){
                loadData.addPageParams((UseCase.RequestPageValue) requestValues);
            }

            if (!requestValues.checkInput()) {
                if (getView() instanceof ActivityHintView) {
                    ((ActivityHintView) getView()).showToast(requestValues.getErrorStringRes());
                }
                loadData.paramsFail();
                return;
            }
        }

        getView().showLoading(loadType != LoadType.LOAD_POP);

        useCase.unSubscribe("");
        useCase.execute(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                BaseLcePresenter.this.onNext(o);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                BaseLcePresenter.this.onError(throwable);
            }
        },requestValues);
    }

    protected void onNext(Object data){
        if(!isViewAttached())
            return;
        if(loadData != null)
            loadData.onNext(data);
    }

    protected void onError(Throwable e){
        if(!isViewAttached())
            return;
        if(loadData != null)
            loadData.onError(e);
    }

    @Override
    public void detachView() {
        super.detachView();
        useCase.unSubscribe("");
    }

    protected abstract UseCase.RequestValues generateRequestValue(LoadType loadType);
}
