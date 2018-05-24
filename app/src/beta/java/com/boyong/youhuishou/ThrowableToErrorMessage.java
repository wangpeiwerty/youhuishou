package com.boyong.youhuishou;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.eleven.mvp.R;
import com.eleven.mvp.base.domain.CommonFailMsgException;
import com.eleven.mvp.base.lce.ErrorMessage;
import com.lzy.okgo.exception.HttpException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;





public class ThrowableToErrorMessage {


    public static ErrorMessage toErrorMessage(Throwable t, Context context){
        if (t instanceof HttpException){
            return HttpExceptionToErrorMessage.httpException2ErrorMsg(context, (HttpException)t);
        }else
        if (t instanceof TimeoutException || t instanceof SocketException || t instanceof SocketTimeoutException){//网络连接失败,请稍后再试
            return new NoNetWorkErrorMessage(context);
        }

        if(t instanceof NullPointerException){
            throw new NullPointerException(t.getMessage());
        }

        if(t instanceof CommonFailMsgException){
            return new CommonFailMsgErrorMessage(((CommonFailMsgException) t).getMsg());
        }

        return new UnKnownErrorMessage(context, t.getMessage());
    }

    private static class CommonFailMsgErrorMessage implements ErrorMessage{
        String message;

        public CommonFailMsgErrorMessage(String message) {
            this.message = message;
        }

        @Override
        public String getHintText() {
            return message;
        }

        @Override
        public String getErrorProcessButtonText() {
            return message;
        }

        @Override
        public int getHintImage() {
            return R.mipmap.ic_launcher;
        }

        @Override
        public Runnable getErrorProcessRunnable() {
            return new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showLong(message);
                }
            };
        }

        @Override
        public Runnable getLceErrorProcessRunnable() {
            return null;
        }
    }


    private static class UnKnownErrorMessage implements ErrorMessage {

        private Context context;
        private String error;

        private UnKnownErrorMessage(Context context, String error) {
            this.context = context;
            this.error = error;
        }

        @Override
        public String getHintText() {
            return error;
        }

        @Override
        public String getErrorProcessButtonText() {
            return context.getString(R.string.report_error);
        }

        @Override
        public int getHintImage() {
            return R.mipmap.ic_launcher;
        }

        @Override
        public Runnable getErrorProcessRunnable() {
            return new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                }
            };
        }

        @Override
        public Runnable getLceErrorProcessRunnable() {
            return new Runnable() {
                @Override
                public void run() {
                    if(context instanceof Activity){
                        ((Activity) context).finish();
                    }

                }
            };
        }
    }

    private static class NoNetWorkErrorMessage implements ErrorMessage {

        private Context context;

        private NoNetWorkErrorMessage(Context context) {
            this.context = context;
        }

        @Override
        public String getHintText() {
            return context.getString(R.string.connect_fail_toast);
        }

        @Override
        public String getErrorProcessButtonText() {
            return context.getString(R.string.refresh);
        }

        @Override
        public int getHintImage() {
            return R.mipmap.ic_launcher;
        }

        @Override
        public Runnable getErrorProcessRunnable() {
            return new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, context.getString(R.string.connect_fail_toast), Toast.LENGTH_LONG).show();
                }
            };
        }

        @Override
        public Runnable getLceErrorProcessRunnable() {
            return null;
        }
    }
}
