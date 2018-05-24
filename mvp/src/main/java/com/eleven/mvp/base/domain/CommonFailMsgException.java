package com.eleven.mvp.base.domain;

public class CommonFailMsgException extends Exception {
    String msg;

    public CommonFailMsgException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
