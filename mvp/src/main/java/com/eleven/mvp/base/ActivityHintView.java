package com.eleven.mvp.base;

import android.view.View;

/**
 * Created by Robert yao on 2016/10/17.
 */

public interface ActivityHintView {

    void showToast(String msg);
    void showToast(int resId);
    void showLongToast(String msg);
    void showLongToast(int resId);
    void showProgressDialog(int msgRes);
    void showProgressDialog(String msg);
    void showProgressDialog(String msg, String title);
    void showProgressDialog(String msg, String title, View view);
    void hideProgressDialogIfShowing();

}
