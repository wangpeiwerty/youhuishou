package com.boyong.youhuishou.activity.login;

import com.boyong.youhuishou.data.user.UserRepository;
import com.eleven.mvp.base.domain.UseCase;

import io.reactivex.Observable;

public class ActivityListUseCase extends UseCase<ActivityListRequestValue> {

    private UserRepository mRepository;

    public ActivityListUseCase(UserRepository mRepository) {
        this.mRepository = mRepository;
    }


    @Override
    protected Observable buildUseCaseObservable(ActivityListRequestValue rv) {
        // TODO
        return mRepository.activityList(rv.getUserId(),rv.getPageNo(),rv.getPageSize());
    }
}