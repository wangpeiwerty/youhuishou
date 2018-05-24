package com.boyong.youhuishou.activity.product;


import com.boyong.youhuishou.data.user.UserApi;
import com.boyong.youhuishou.ThrowableConsumer;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import io.reactivex.functions.Consumer;

public class ProductPresenter extends MvpNullObjectBasePresenter<ProductContract.View> implements ProductContract.Presenter {


    private ProductUseCase useCase;

    public ProductPresenter(ProductUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void detachView() {
        super.detachView();
        useCase.unSubscribe();
    }

    @Override
    public void produceList() {
        useCase.unSubscribe(UserApi.PRODUCT_LIST);
        useCase.execute(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {

            }
        }, new ThrowableConsumer(getView().getActivityContext()) {
            @Override
            public void accept(Throwable throwable) throws Exception {
                super.accept(throwable);
            }
        },null);
    }
}
