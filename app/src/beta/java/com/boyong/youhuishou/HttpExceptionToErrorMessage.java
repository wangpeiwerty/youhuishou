package com.boyong.youhuishou;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.boyong.youhuishou.activity.login.LoginActivity;
import com.eleven.mvp.R;
import com.eleven.mvp.base.lce.ErrorMessage;
import com.eleven.mvp.base.lce.view.TokenErrorMessage;
import com.lzy.okgo.exception.HttpException;


public class HttpExceptionToErrorMessage {

     static ErrorMessage httpException2ErrorMsg(final Context context, HttpException t){
        ErrorMessage errorMessage;
        switch (t.code()){
            case 400:
                ServerErrorResponse serverErrorResponse = makeServerErrorResponse(t, context.getString(R.string.maintain_service));
                errorMessage = new ServiceErrorMessage(context, serverErrorResponse.getSemantic(), makeErrorRunnable(context, serverErrorResponse));
                break;
            case 401:
                errorMessage = new TokenExpirationErrorMessage(context);
                break;
            case 404:
                ServerErrorResponse serverErrorResponse2 = makeServerErrorResponse(t, context.getString(R.string.not_found));
                errorMessage = new ServiceErrorMessage(context, serverErrorResponse2.getSemantic(), makeErrorRunnable(context, serverErrorResponse2));
                break;
            case 500:
                errorMessage = new ServiceErrorMessage(context, context.getString(R.string.error_server));
                break;
            default:
                errorMessage = new ServiceErrorMessage(context, t.getMessage());
                break;
        }

        return errorMessage;
    }

    private static Runnable makeErrorRunnable(Context context, ServerErrorResponse serverErrorResponse) {
        Runnable runnable;
        try {

            switch (serverErrorResponse.getCode()){
                default:
                    runnable = null;
                    break;
            }
            return runnable;
        }catch (Exception e){

        }
        return null;
    }


    private static ServerErrorResponse makeServerErrorResponse(HttpException httpException, String defaultHint) {
        ServerErrorResponse serverErrorResponse = new ServerErrorResponse();
        try {
            serverErrorResponse.setSemantic("未知错误");
        }catch (Exception e){
            LogUtils.e(e.getMessage());
            serverErrorResponse = new ServerErrorResponse();
            serverErrorResponse.setSemantic(TextUtils.isEmpty(defaultHint)?httpException.getMessage():defaultHint);
            serverErrorResponse.setCode("unknown error");
        }
        return serverErrorResponse;
    }


    private static class ServiceErrorMessage implements ErrorMessage {

        private Context context;
        private String error;
        private Runnable runnable;

        private ServiceErrorMessage(Context context, String error) {
            this(context, error, null);
        }

        private ServiceErrorMessage(Context context, String error, Runnable runnable) {
            this.context = context;
            this.error = error;
            this.runnable = runnable;
        }

        @Override
        public String getHintText() {
            return error;
        }

        @Override
        public String getErrorProcessButtonText() {
            return context.getString(R.string.retry);
        }

        @Override
        public int getHintImage() {
            return 0;
        }

        @Override
        public Runnable getErrorProcessRunnable() {
            return new Runnable() {
                @Override
                public void run() {

                    if(runnable !=  null){
                        runnable.run();
                    }else{
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                    }
                }
            };
        }

        @Override
        public Runnable getLceErrorProcessRunnable() {
            return null;
        }
    }

    public static class TokenExpirationErrorMessage implements TokenErrorMessage {

        private Context context;
        private TokenExpirationErrorMessage(Context context) {
            this.context = context;
        }

        @Override
        public String getLoginAction() {
            return LoginActivity.ACTION_KEY_LOGIN_SUCCESS;
        }

        @Override
        public Runnable getErrorProcessRunnable() {
            return new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, context.getString(R.string.token_error), Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context,LoginActivity.class));
                }
            };
        }

        @Override
        public int getHintImage() {
            return R.mipmap.ic_launcher;
        }

        @Override
        public String getErrorProcessButtonText() {
            return context.getString(R.string.retry_login);
        }

        @Override
        public String getHintText() {
            return context.getString(R.string.token_error);
        }

        @Override
        public Runnable getLceErrorProcessRunnable() {
            return new Runnable() {
                @Override
                public void run() {
                    context.startActivity(new Intent(context,LoginActivity.class));
                }
            };
        }
    }

}
