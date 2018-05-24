package com.boyong.youhuishou.data.results;

public class DataResult<T> extends BaseDataResult {

    private final static String TAG = DataResult.class.getSimpleName();

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
