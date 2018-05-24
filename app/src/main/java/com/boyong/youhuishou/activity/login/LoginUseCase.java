package com.boyong.youhuishou.activity.login;

import com.boyong.youhuishou.data.user.UserRepository;
import com.eleven.mvp.base.domain.UseCase;


import io.reactivex.Observable;


public class LoginUseCase extends UseCase<LoginRequestValue> {

    // TODO setup Repository
    UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(LoginRequestValue rv) {
        // TODO Repository method
        return userRepository.login(rv.getAccount(),rv.getPassword());
    }
}
