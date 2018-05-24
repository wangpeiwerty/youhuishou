package com.boyong.youhuishou.data;

import com.boyong.youhuishou.data.results.DataResult;
import com.boyong.youhuishou.data.results.DateDeserializer;
import com.eleven.mvp.base.domain.CommonFailMsgException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;

import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class DataResultConvertT<T> implements Converter<T>{

    Type typeOfT;

    public DataResultConvertT(Type typeOfT) {
        this.typeOfT = typeOfT;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        DataResult dataResult;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        try {
            JsonReader jsonReader = new JsonReader(body.charStream());
            dataResult = gson.fromJson(jsonReader, new TypeToken<DataResult<T>>(){}.getType());
        }catch (Exception e){
            throw new CommonFailMsgException("数据解析异常");
        }
        if(dataResult==null)return null;
        if(dataResult.isSuccess()){
            return (T)gson.fromJson(gson.toJson(dataResult.getData()),typeOfT);
        }else{
            throw new CommonFailMsgException(dataResult.getMsg());
        }
    }
}
