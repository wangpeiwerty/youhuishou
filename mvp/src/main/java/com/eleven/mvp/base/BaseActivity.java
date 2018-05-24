package com.eleven.mvp.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.eleven.mvp.R;
import com.gyf.barlibrary.ImmersionBar;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by Robert yao on 2016/10/17.
 */
public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> implements ActivityHintView, EasyPermissions.PermissionCallbacks {

    private ActivityHintView activityHintView;
    Unbinder unbinder;
    private static final int WRITE_EXTERNAL_STORAGE = 100;
    private static final int RECORD_AUDIO = 101;
    private static final int CAMERA = 102;
    private static final int READ_PHONE_STATE = 103;

    private InputMethodManager imm;
    protected ImmersionBar mImmersionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        activityHintView = new ActivityHintViewImpl(this);
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
    }


    protected abstract int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder!=null)
            unbinder.unbind();
        this.imm = null;
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true)
                .flymeOSStatusBarFontColor(R.color.black)  //修改flyme OS状态栏字体颜色
                .init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    public void startActivity(Class classs) {
        startActivity(new Intent(this, classs));
    }

    public void startActivity(Class classs, Object object) {
        startActivity(new Intent(this, classs).putExtra("data", (Serializable) object));
    }

    @Override
    public void showToast(String msg) {
        activityHintView.showToast(msg);
    }

    @Override
    public void showToast(int resId) {
        activityHintView.showToast(resId);
    }

    @Override
    public void showLongToast(int resId) {
        activityHintView.showLongToast(resId);
    }

    @Override
    public void showLongToast(String msg) {
        activityHintView.showLongToast(msg);
    }

    @Override
    public void showProgressDialog(int msgRes) {
        activityHintView.showProgressDialog(msgRes);
    }

    @Override
    public void showProgressDialog(String msg) {
        activityHintView.showProgressDialog(msg);
    }

    @Override
    public void showProgressDialog(String msg, String title) {
        activityHintView.showProgressDialog(msg, title);
    }

    @Override
    public void showProgressDialog(String msg, String title, View view) {
        activityHintView.showProgressDialog(msg, title, view);
    }

    @Override
    public void hideProgressDialogIfShowing() {
        activityHintView.hideProgressDialogIfShowing();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE:
                showToast("已获取读取权限");
                break;
            case RECORD_AUDIO:
                showToast("已获取语音权限");
                break;
            case CAMERA:
                showToast("已获取相机权限");
                break;
            case READ_PHONE_STATE:
                showToast("已获取手机状态权限");
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE:
                showToast("已拒绝读取权限");
                break;
            case RECORD_AUDIO:
                showToast("已拒绝语音权限");
                break;
            case CAMERA:
                showToast("已拒绝相机权限");
                break;
            case READ_PHONE_STATE:
                showToast("已拒绝手机状态权限");
                break;
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                    .setTitle("必需权限")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .build()
                    .show();
        }
    }

    /**
     * 检查权限
     */
    @AfterPermissionGranted(RECORD_AUDIO)
    public boolean checkAudioPerm() {
        String[] params = {Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, params)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(this, "需要语音权限", RECORD_AUDIO, params);
            return false;
        }
    }

    /**
     * 检查权限
     */
    @AfterPermissionGranted(CAMERA)
    public boolean checkCameraPerm() {
        String[] params = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, params)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(this, "需要拍照权限", CAMERA, params);
            return false;
        }
    }

    /**
     * 检查权限
     */
    @AfterPermissionGranted(WRITE_EXTERNAL_STORAGE)
    public boolean checkfenxiangPerm() {
        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, mPermissionList)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(this, "需要分享的权限", WRITE_EXTERNAL_STORAGE, mPermissionList);
            return false;
        }
    }

    /**
     * 检查权限
     */
    @AfterPermissionGranted(READ_PHONE_STATE)
    public boolean checkphoneStatePerm() {
        String[] mPermissionList = new String[]{Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(this, mPermissionList)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(this, "需要手机状态的权限", READ_PHONE_STATE, mPermissionList);
            return false;
        }
    }
}
