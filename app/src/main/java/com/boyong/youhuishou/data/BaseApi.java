package com.boyong.youhuishou.data;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;

public abstract class BaseApi {

    public <T> Observable<T> requestPost(String api_url, Map<String,String> params, Type typeOfT){
        return OkGo.<T>post(getApiService()+api_url)
                .cacheKey(api_url)
                .tag(api_url)
                .params(params)
                .converter(new DataResultConvertT<T>(typeOfT))
                .adapt(new ObservableBody<T>());
    }

    public <T> Observable<T> requestGet(String api_url,Map<String,String> params, Type typeOfT){
        return OkGo.<T>get(getApiService()+api_url)
                .cacheKey(api_url)
                .tag(api_url)
                .params(params)
                .converter(new DataResultConvertT<T>(typeOfT))
                .adapt(new ObservableBody<T>());
    }

    public abstract String getApiService();

}
