package com.boyong.youhuishou.activity.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.boyong.youhuishou.R;
import com.eleven.mvp.base.BaseActivity;
import com.eleven.mvp.base.domain.BlankPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;




public class LoginActivity extends BaseActivity {

    public static final String ACTION_KEY_LOGIN_SUCCESS = "ACTION_KEY_LOGIN_SUCCESS";
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,new PasswordLoginFragment()).commit();
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return BlankPresenter.INSTANCE;
    }
}
