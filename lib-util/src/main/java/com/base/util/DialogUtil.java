package com.base.util;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;

/**
 * 显示各种简单对话框
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogUtil {

    /**
     * 显示一个不确定结束进度的进度窗口
     * 
     * 属性 mProgDialog
     * 属性 title
     * 属性 message
     */
    public static synchronized void showSpinnerProgressDialog(ProgressDialog mProgDialog, String title, String message) {
        mProgDialog.setIndeterminate(true); // 不确定进度范围
        mProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgDialog.setTitle(title);
        mProgDialog.setMessage(message);
        mProgDialog.setCancelable(false);
        mProgDialog.show();
    }

    /**
     * 显示提示框
     * 
     * 属性 context
     * 属性 title
     * 属性 message
     * @return
     */
    public static synchronized AlertDialog.Builder showAlertDialog(Context context, int theme, String title, String message) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(context, theme);
        mAlertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        mAlertDialog.setTitle(title);
        mAlertDialog.setMessage(message);
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
        return mAlertDialog;
    }

    /**
     * 显示一个弹出框，传入确定后事件
     * 
     * 属性 context
     * 属性 title
     * 属性 message
     * @return
     */
    public static synchronized AlertDialog.Builder showAlertDialogWithBtnEvent(Context context, int theme, String title, String message,
            DialogInterface.OnClickListener positiveEvent) {
        AlertDialog.Builder mSimpleDialog = new AlertDialog.Builder(context, theme);
        mSimpleDialog.setTitle(title);
        mSimpleDialog.setMessage(message);
        mSimpleDialog.setPositiveButton("确定", positiveEvent);
        mSimpleDialog.setCancelable(false);
        mSimpleDialog.show();
        return mSimpleDialog;

    }

    /**
     * 显示事件提示框
     * 
     * 属性 context
     * 属性 title
     * 属性 message
     * 属性 positiveEvent
     * 属性 negativeEvent
     *        默认取消事件填null
     * @return
     */
    public static synchronized AlertDialog.Builder showConfirmDialog(Context context, int theme, String title, String message, DialogInterface.OnClickListener positiveEvent,
            String positiveBtnText, DialogInterface.OnClickListener negativeEvent, String negativeBtnText) {
        AlertDialog.Builder mEvenDialog = new AlertDialog.Builder(context, theme);
        mEvenDialog.setTitle(title);
        mEvenDialog.setMessage(message);
        mEvenDialog.setCancelable(false);
        mEvenDialog.setPositiveButton(positiveBtnText, positiveEvent);
        if (negativeEvent != null) {
            mEvenDialog.setNegativeButton(negativeBtnText, negativeEvent);
        } else {
            mEvenDialog.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        mEvenDialog.show();
        return mEvenDialog;
    }

    /**
     * 显示列表对话框
     * 
     * 属性 context
     * 属性 title
     * 属性 items
     * 属性 listener
     * @return
     */
    public static synchronized AlertDialog.Builder showListDialog(Context context, int theme, String title, String[] items, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder listDialog = new AlertDialog.Builder(context, theme);
        listDialog.setTitle(title);
        listDialog.setItems(items, listener);
        listDialog.show();
        return listDialog;
    }

    /**
     * 显示列表对话框
     * 
     * 属性 context
     * 属性 theme
     * 属性 title
     * 属性 message
     * @return
     */
    public static synchronized AlertDialog.Builder getProgressDialog(Context context, int theme, String title,String message) {
        ProgressDialog.Builder progressDialog = new ProgressDialog.Builder(context, theme);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
//        progressDialog.show();
        return progressDialog;
    }
    /**
     * 自定义控件对话框
     * 属性 context 上下文对象
     * 属性 theme 样式
     * 属性 view 自定义布局
     * @return
     */
    public static synchronized AlertDialog.Builder getCustomViewDialog(Context context, int theme, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setView(view);
        return builder;
    }
}
