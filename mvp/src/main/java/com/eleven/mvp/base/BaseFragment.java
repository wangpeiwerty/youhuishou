package com.eleven.mvp.base;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Robert yao on 2016/10/17.
 */
public abstract class BaseFragment<V extends MvpView, P extends MvpPresenter<V>> extends MvpFragment<V, P> implements ActivityHintView, EasyPermissions.PermissionCallbacks{

    private ActivityHintView activityHintView;
    private Activity activity;
    private Unbinder unbinder;
    protected View mRootView;

    protected ImmersionBar mImmersionBar;
    /**
     * 随便赋值的一个唯一标识码
     */
    private static final int WRITE_EXTERNAL_STORAGE = 100;
    private static final int RECORD_AUDIO = 101;
    private static final int CAMERA = 102;
    private static final int READ_PHONE_STATE = 103;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activityHintView = new ActivityHintViewImpl(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder =  ButterKnife.bind(this, view);
        if (isImmersionBarEnabled())
            initImmersionBar();
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
    }


    /**
     * 找到activity的控件
     *
     * @param <T> the type parameter
     * @param id  the id
     * @return the t
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findActivityViewById(@IdRes int id) {
        return (T) activity.findViewById(id);
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null)
            mImmersionBar.init();
        if (hidden) {
            //隐藏时所作的事情
        } else {
            //显示时所作的事情
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
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
    public void showLongToast(String msg) {
        activityHintView.showLongToast(msg);
    }

    @Override
    public void showLongToast(int resId) {
        activityHintView.showLongToast(resId);
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
    public void showProgressDialog( String msg,String title) {
        activityHintView.showProgressDialog(msg,title);
    }
    @Override
    public void showProgressDialog(String msg, String title, View view) {
        activityHintView.showProgressDialog(msg,title,view);
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
                showToast("已拒绝读写权限");
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
        if (EasyPermissions.hasPermissions(activity, params)) {
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
        if (EasyPermissions.hasPermissions(activity, params)) {
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
        if (EasyPermissions.hasPermissions(activity, mPermissionList)) {
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
        if (EasyPermissions.hasPermissions(activity, mPermissionList)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(this, "需要手机状态的权限", READ_PHONE_STATE, mPermissionList);
            return false;
        }
    }
}
