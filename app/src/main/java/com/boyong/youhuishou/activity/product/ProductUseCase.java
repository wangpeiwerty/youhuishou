package com.boyong.youhuishou.activity.product;



import com.boyong.youhuishou.data.user.UserRepository;
import com.eleven.mvp.base.domain.UseCase;

import io.reactivex.Observable;

public class ProductUseCase extends UseCase<ProductRequestValue> {

    // TODO setup Repository
    UserRepository userRepository;

    public ProductUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(ProductRequestValue rv) {
        // TODO Repository method
        return userRepository.productList();
    }
}
