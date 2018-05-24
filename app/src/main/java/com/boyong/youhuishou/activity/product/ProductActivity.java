package com.boyong.youhuishou.activity.product;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.boyong.youhuishou.Injection;
import com.boyong.youhuishou.R;
import com.eleven.mvp.base.BaseActivity;

public class ProductActivity extends BaseActivity<ProductContract.View, ProductContract.Presenter> implements ProductContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().produceList();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_product;
    }

    @NonNull
    @Override
    public ProductContract.Presenter createPresenter() {
        //TODO Injection provide UseCase
        return new ProductPresenter(Injection.provideProductUseCase());
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void produceListSuccess() {

    }
}
