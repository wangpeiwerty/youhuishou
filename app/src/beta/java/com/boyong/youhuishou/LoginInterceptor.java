package com.boyong.youhuishou;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.boyong.youhuishou.data.DataResultConvertT;
import com.boyong.youhuishou.data.SPKey;
import com.boyong.youhuishou.data.bean.UserModel;
import com.boyong.youhuishou.data.results.DataResult;
import com.boyong.youhuishou.data.results.DateDeserializer;
import com.boyong.youhuishou.data.user.UserApi;
import com.eleven.mvp.base.domain.CommonFailMsgException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        try {
            String bodyString = response.body().source().buffer().clone()
                    .readString(response.body().contentType().charset(Charset.forName("UTF-8")));
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
            DataResult dataResult = gson.fromJson(bodyString, new TypeToken<DataResult<UserModel>>() {
            }.getType());
            if (request.url().toString().contains(UserApi.LOGIN_API_URL) && response.code() == 200) {
                if (dataResult != null && dataResult.isSuccess()) {
                    SPUtils.getInstance().put(SPKey.LOGIN, new Gson().toJson(dataResult.getData()));
                }
            } else if ((request.url().toString().contains(UserApi.LOGOUT_API_URL) &&  response.code() == 200)  || response.code() == 401) {
                SPUtils.getInstance().remove(SPKey.LOGIN);
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return response;
    }
}
