package com.boyong.youhuishou.activity.login;

import com.boyong.youhuishou.data.user.UserRepository;
import com.eleven.mvp.base.domain.UseCase;

import io.reactivex.Observable;


public class SmsUseCase extends UseCase<SmsRequestValue> {

    private UserRepository mRepository;

    public SmsUseCase(UserRepository mRepository) {
        this.mRepository = mRepository;
    }


    @Override
    protected Observable buildUseCaseObservable(SmsRequestValue rv) {

        return null;
    }
}