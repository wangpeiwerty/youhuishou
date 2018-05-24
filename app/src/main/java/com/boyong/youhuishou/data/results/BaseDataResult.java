package com.boyong.youhuishou.data.results;
import java.io.Serializable;

public class BaseDataResult implements Serializable {
    String msg = "";
    String code = "";

    public Boolean isSuccess() {
        return  this.code!=null && !this.code.equals("F") && !this.code.equals("N");
    }

    public String getMsg() {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }

    public BaseDataResult toLzyResponse() {
        BaseDataResult lzyResponse = new BaseDataResult();
        lzyResponse.code = code;
        lzyResponse.msg = msg;
        return lzyResponse;
    }

}
