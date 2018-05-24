package com.boyong.youhuishou;
import com.boyong.youhuishou.activity.login.ActivityListUseCase;
import com.boyong.youhuishou.activity.login.LoginUseCase;
import com.boyong.youhuishou.activity.product.ProductUseCase;
import com.boyong.youhuishou.data.user.UserApi;
import com.boyong.youhuishou.data.user.UserRepository;
import com.boyong.youhuishou.data.user.UserRepositoryImpl;


public class Injection {

    static final String BASE_URL = "";
    static UserApi userApi;


    /******************************* API *************************/
    public static UserApi provideUserApi() {
        if (userApi == null)
            userApi = new UserApi(BASE_URL);
        return userApi;
    }

    /******************************* Repository接口 *************************/

    private static UserRepository provideUserRepo() {
        return new UserRepositoryImpl(provideUserApi());
    }

    /******************************UserCase用例******************************/


    public static LoginUseCase provideLoginUseCase() {
        return new LoginUseCase(provideUserRepo());
    }

    public static ActivityListUseCase provideActivityListUseCase(){
        return new ActivityListUseCase(provideUserRepo());
    }

    public static ProductUseCase provideProductUseCase() {
        return new ProductUseCase(provideUserRepo());
    }
}
