package com.base.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 屏幕显示相关 <br>
 */
public class ScreenUtil {

	/**
	 * 返回屏幕尺寸(宽)
	 * 
	 * 属性 context
	 * @return
	 */
	public static int getCurrentScreenWidth(Context context) {
		DisplayMetrics metrics = getDisplayMetrics(context);
		boolean isLand = isOrientationLandscape(context);
		if (isLand) {
			return metrics.heightPixels;
		}
		return metrics.widthPixels;
	}
    /**
     * 返回屏幕尺寸(宽)
     * 
     * 属性 context
     * @return
     */
    public static int getCurrentScreenWidth1(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return metrics.widthPixels;
    }

    /**
     * 返回屏幕尺寸(高)
     * 
     * 属性 context
     * @return
     */
    public static int getCurrentScreenHeight(Context context) {
    	DisplayMetrics metrics = getDisplayMetrics(context);
    	boolean isLand = isOrientationLandscape(context);
    	if (isLand) {
    		return metrics.widthPixels;
    	}
    	return metrics.heightPixels;
    }
    /**
     * 返回屏幕尺寸(高)
     * 
     * 属性 context
     * @return
     */
    public static int getCurrentScreenHeight1(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return metrics.heightPixels;
    }

    /**
     * 返回屏幕尺寸
     * 
     * 属性 context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 判断是否横屏
     * 
     * 属性 context
     * @return
     */
    public static boolean isOrientationLandscape(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        }
        return false;
    }

 
    /**
     * dp转px
     * 属性 context
     * 属性 dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        float currentDensity = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * currentDensity + 0.5f);
    }
    
 
    /**
     * px转dp
     * 属性 context
     * 属性 pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
    /**
     * 返回屏幕密度
     */
    public static float getScreenDensity(Context ctx) {
        return ctx.getResources().getDisplayMetrics().density;
    }

    /**
     * 返回屏幕分辨率,数组型。
     */
    public static int[] getScreenResolutionXY(Context ctx) {
        int[] resolutionXY = new int[2];
        if (resolutionXY[0] != 0) {
            return resolutionXY;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) ctx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        resolutionXY[0] = metrics.widthPixels;
        resolutionXY[1] = metrics.heightPixels;
        return resolutionXY;
    }
    /**
     * 返回屏幕分辨率,字符串型。如 320x480
     */
    public static String getScreenResolution(Context ctx) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) ctx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        String resolution = width + "x" + height;
        return resolution;
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = -1;

        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return statusHeight;
    }
}
