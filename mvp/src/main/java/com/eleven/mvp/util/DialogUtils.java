package com.eleven.mvp.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eleven.mvp.R;
import com.eleven.mvp.widget.EditDialog;
import com.eleven.mvp.widget.RadioGroupDialog;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.ZoomEnter.ZoomInEnter;
import com.flyco.animation.ZoomExit.ZoomOutExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;


/**
 * Created by Administrator on 2016/11/21.
 */

public class DialogUtils {

    public static Dialog showHintMessageDialog(Context context, final View.OnClickListener clickListener, int resources){
        Builder builder = new Builder(context);
        builder.setConfirm(clickListener)
                .setCancelRes(R.string.cancel)
                .setConfirmRes(R.string.confirm)
                .setMessageRes(resources);
           //     .setImageRes(R.mipmap.avatar_robot_u);
        return builder.showDialog();
    }


    public static Dialog showWebViewDialog(Context context, String message, View.OnClickListener confirm){
        return new Builder(context)
                .setMessageStr(message)
                .setConfirmRes(R.string.confirm)
                .setConfirm(confirm).showDialog();
    }

    public static void showDeleteDialog(String title,String message,Context context, final View.OnClickListener listener) {
        final NormalDialog dialog = new NormalDialog(context);
        BaseAnimatorSet mBasIn = new ZoomInEnter();
        mBasIn.duration(300);
        BaseAnimatorSet mBasOut = new ZoomOutExit();
        mBasOut.duration(300);
        dialog.content(message)//
                .title(title)
                .style(NormalDialog.STYLE_TWO)//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        if(listener != null)
                            listener.onClick(null);
                        dialog.dismiss();
                    }
                });
        /*return new DialogUtils.Builder(context)
                .setTitleStr(title)
                .setMessageStr(message)
                .setConfirmRes(R.string.confirm)
                .setConfirm(listener)
                .setCancelRes(R.string.cancel)
                .showDialog();*/
    }

    public static void showEditDialog(String title,String message, boolean isNumber,Context context, final View.OnClickListener listener) {
        final EditDialog dialog = new EditDialog(context,message,isNumber);
        BaseAnimatorSet mBasIn = new ZoomInEnter();
        mBasIn.duration(300);
        BaseAnimatorSet mBasOut = new ZoomOutExit();
        mBasOut.duration(300);
        dialog.title(title)
                .style(NormalDialog.STYLE_TWO)//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        if(listener != null){
                            listener.onClick(dialog.getEditValue());

                        }

                        dialog.dismiss();
                    }
                });
        /*return new DialogUtils.Builder(context)
                .setTitleStr(title)
                .setMessageStr(message)
                .setConfirmRes(R.string.confirm)
                .setConfirm(listener)
                .setCancelRes(R.string.cancel)
                .showDialog();*/
    }

    public static void showRadioDialog(String title,String[] message,boolean[] checked,Context context, final View.OnClickListener listener) {
        final RadioGroupDialog dialog = new RadioGroupDialog(context,message,checked);
        BaseAnimatorSet mBasIn = new ZoomInEnter();
        mBasIn.duration(300);
        BaseAnimatorSet mBasOut = new ZoomOutExit();
        mBasOut.duration(300);
        dialog.title(title)
                .style(NormalDialog.STYLE_TWO)//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        if(listener != null){
                            listener.onClick(dialog.getRadiogroup());

                        }

                        dialog.dismiss();
                    }
                });
        /*return new DialogUtils.Builder(context)
                .setTitleStr(title)
                .setMessageStr(message)
                .setConfirmRes(R.string.confirm)
                .setConfirm(listener)
                .setCancelRes(R.string.cancel)
                .showDialog();*/
    }

    public static Dialog showRenZhengDialog(String title,String message,Context context, View.OnClickListener listener) {
        return new Builder(context)
                .setTitleStr(title)
                .setMessageStr(message)
                .setConfirmRes(R.string.confirm)
                .setConfirm(listener)
                .setCancelRes(R.string.cancel)
                .showDialog();
    }



    /**
     * 优驾行默认对话框样式
     */
    public static class Builder{
        private Context context;
        private int titleRes;
        private String titleStr;
        private int messageRes;
        private String messageStr;
        private int imageRes;
        private int confirmRes;
        private int cancelRes;
        private View.OnClickListener confirm;
        private View.OnClickListener cancel;
        private DialogInterface.OnDismissListener dismissListener;
        public Builder(Context context) {
            this.context = context;
        }
        public Builder setMessageRes(int messageRes) {
            this.messageRes = messageRes;
            return this;
        }
        public Builder setMessageStr(String messageStr) {
            this.messageStr = messageStr;
            return this;
        }
        public Builder setImageRes(int imageRes) {
            this.imageRes = imageRes;
            return this;
        }
        public Builder setConfirmRes(int confirmRes) {
            this.confirmRes = confirmRes;
            return this;
        }
        public Builder setCancelRes(int cancelRes) {
            this.cancelRes = cancelRes;
            return this;
        }
        public Builder setTitleRes(int titleRes) {
            this.titleRes = titleRes;
            return this;
        }
        public Builder setTitleStr(String titleStr) {
            this.titleStr = titleStr;
            return this;
        }
        public Builder setConfirm(View.OnClickListener confirm) {
            this.confirm = confirm;
            return this;
        }
        public Builder setCancel(View.OnClickListener cancel) {
            this.cancel = cancel;
            return this;
        }
        public Builder setDismissListener(DialogInterface.OnDismissListener dismissListener){
            this.dismissListener = dismissListener;
            return this;
        }
        public Dialog showDialog(){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.AlertDialog_AppCompat_TranslucentWindow);
            View view  = View.inflate(context, R.layout.dialog_common, null);
            dialogBuilder.setView(view);
            if(dismissListener != null)
                dialogBuilder.setOnDismissListener(dismissListener);
            final Dialog dialog = dialogBuilder.show();

            if(imageRes != 0) {
                ((ImageView) view.findViewById(R.id.image)).setImageResource(imageRes);
            }
            if(messageRes != 0) {
                ((TextView) view.findViewById(R.id.message)).setText(messageRes);
            }else if(!TextUtils.isEmpty(messageStr)){
                ((TextView) view.findViewById(R.id.message)).setText(messageStr);
            }

            if(confirmRes != 0) {
                ((TextView) view.findViewById(R.id.confirm)).setText(confirmRes);
            }else{
                view.findViewById(R.id.confirm).setVisibility(View.GONE);
            }

            if(cancelRes != 0) {
                ((TextView) view.findViewById(R.id.cancel)).setText(cancelRes);
            }else{
                view.findViewById(R.id.cancel).setVisibility(View.GONE);
            }

            if(titleRes != 0) {
                ((TextView) view.findViewById(R.id.title)).setText(titleRes);
            }else{
                ((TextView) view.findViewById(R.id.title)).setText(titleStr);
            }
            view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    if(confirm != null)
                        confirm.onClick(v);
                }
            });
            view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    if(cancel != null)
                        cancel.onClick(v);
                }
            });
            return dialog;
        }
    }

}
