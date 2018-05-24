package com.boyong.youhuishou.activity.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.boyong.youhuishou.data.SPKey;
import com.boyong.youhuishou.data.bean.ActivityModel;
import com.boyong.youhuishou.data.bean.CommonListModel;
import com.boyong.youhuishou.data.bean.UserModel;
import com.boyong.youhuishou.Injection;
import com.boyong.youhuishou.R;
import com.eleven.mvp.base.BaseFragment;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/24.
 */

public class PasswordLoginFragment extends BaseFragment<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.yanjing)
    ImageView yanjing;
    @BindView(R.id.submit)
    Button submit;
    boolean ischakan;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_password_login;
    }

    @NonNull
    @Override
    public LoginContract.Presenter createPresenter() {
        //TODO Injection provide UseCase
        return new LoginPresenter(Injection.provideLoginUseCase(),Injection.provideActivityListUseCase());
    }

    @Override
    public void usernameRequestFocus() {
        requestFocus(username);
    }

    @Override
    public void passwordRequestFocus() {
        requestFocus(password);
    }


    public void requestFocus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    @Override
    public void loginSuccess() {
       /*startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();*/

        UserModel userModel = new GsonBuilder().serializeNulls().create().fromJson(SPUtils.getInstance().getString(SPKey.LOGIN,""), UserModel.class);
        if(userModel!=null){
            getPresenter().requestActivityList(userModel.getUserId(),"1","10");
        }
    }

    @Override
    public void requestActivityListSuccess(CommonListModel<ActivityModel> listModel) {
        submit.setText(listModel.getCount()+"---"+listModel.getList().get(0).getActivityId());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    public void initView() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isEmpty(s.toString())) {
                    delete.setVisibility(View.GONE);
                } else {
                    delete.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @OnClick(R.id.submit)
    void denglu() {
        getPresenter().login(username.getText().toString(), password.getText().toString());
    }

    @OnClick(R.id.yanjing)
    void yanjing(View view) {
        if (!ischakan) {
            // 显示为普通文本
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = password.getText();
            Selection.setSelection(etable, etable.length());
            yanjing.setImageResource(R.mipmap.open);
            ischakan = true;
        } else {
            yanjing.setImageResource(R.mipmap.close);
            ischakan = false;
            // 显示为密码
            password.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = password.getText();
            Selection.setSelection(etable, etable.length());
        }
    }

    @OnClick(R.id.delete)
    void delete() {
        username.setText("");
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }
}
