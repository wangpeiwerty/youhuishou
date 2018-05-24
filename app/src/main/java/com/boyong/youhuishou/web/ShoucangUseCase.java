package com.boyong.youhuishou.web;



import com.boyong.youhuishou.data.user.UserRepository;
import com.eleven.mvp.base.domain.UseCase;

import io.reactivex.Observable;


public class ShoucangUseCase extends UseCase<ShoucangRequestValue> {

    private UserRepository mRepository;

    public ShoucangUseCase(UserRepository mRepository) {
        this.mRepository = mRepository;
    }


    @Override
    protected Observable buildUseCaseObservable(ShoucangRequestValue rv) {
        // TODO
        return null;
    }
}