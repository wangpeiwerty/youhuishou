package com.boyong.youhuishou;


import android.content.Context;

import com.eleven.mvp.base.lce.ErrorMessage;

import io.reactivex.functions.Consumer;

public abstract class ThrowableConsumer implements Consumer<Throwable> {

    Context context;

    public ThrowableConsumer(Context context) {
        this.context=context;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        ErrorMessage errorMessage = ThrowableToErrorMessage.toErrorMessage(throwable,context);
        if (errorMessage.getErrorProcessRunnable() != null)
            errorMessage.getErrorProcessRunnable().run();
    }
}
