package com.eleven.mvp.base.lce;

/**
 * Created by Robert yao on 2016/11/9.
 */
public interface ErrorMessage {

    String getHintText();
    String getErrorProcessButtonText();
    int getHintImage();
    Runnable getErrorProcessRunnable();
    Runnable getLceErrorProcessRunnable();

}
