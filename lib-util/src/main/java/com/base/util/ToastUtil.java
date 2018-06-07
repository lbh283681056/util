package com.base.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Toast工具类
  * 类名: ToastUtil
  * 描述
  * 作者 Comsys-linbinghuang
  * 时间 2014-11-3 下午4:34:43
  *
  */
public class ToastUtil {

    private static Toast mToast;

    /**
     * toast
     * 
     * 属性 text
     */
    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 居中的Toast
     * 
     * 属性 context
     * 属性 text
     */
    private static Toast mmToast;

    public static void showMidToast(Context context, String text) {
        if (mmToast == null) {
            mmToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mmToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mmToast.setText(text);
            mmToast.setDuration(Toast.LENGTH_SHORT);
        }
        mmToast.show();
    }

    /**
     * 自定义布局居中Toast
     * 
     * 属性 context
     * 属性 text
     */
    private static Toast mmcToast;

    public static void showCostomerMidToast(Context context, View view, String text) {
        if (mmcToast == null) {
            mmcToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mmcToast.setGravity(Gravity.CENTER, 0, 0);
            ((ViewGroup) mmcToast.getView()).addView(view, 0);
        } else {
            mmcToast.setText(text);
            mmcToast.setDuration(Toast.LENGTH_SHORT);
        }
        mmcToast.show();
    }

 
}
