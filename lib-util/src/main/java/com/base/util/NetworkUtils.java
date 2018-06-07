package com.base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

/**
 *  网络工具类
  * 类名: NetworkUtils
  * 描述
  * 作者 Comsys-linbinghuang
  * 时间 2014-11-3 下午4:31:44
  *
  */
public class NetworkUtils {

    /**
     * 是否是空的url
     * 属性 url
     * @return
     */
    public static boolean checkURL(String url) {
        if (url == null) {
            return false;
        }

        url = url.trim();
        if (url.equals("")) {
            return false;
        }

        return true;
    }

    /**
     * 判断是否有网络
     * 属性 context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否WIFI
     * 属性 context
     * @return
     */
    public static boolean isWifiAvailable(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi.isAvailable())
            return true;
        else
            return false;
    }

    /**
     * 判断是否手机网络
     * 属性 context
     * @return
     */
    public static boolean isMobileNetworkAvailable(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobile.isAvailable())
            return true;
        else
            return false;
    }
    
    /**
      * 返回网络连接方式
      * 描述 TODO
      * 属性 属性 ctx
      * 属性 @return
      * @return int    
      * 异常
      */
    public static int getNetworkState(Context ctx) {
        if (isWifiEnable(ctx)) {
            return 0;
        } else {
            return 1;
        }
    }
    

    /**
      * wifi是否启动
      * 描述 TODO
      * 属性 属性 ctx
      * 属性 @return
      * @return boolean    
      * 异常
      */
    public static boolean isWifiEnable(Context ctx) {
        ConnectivityManager tele = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (tele.getActiveNetworkInfo() == null || !tele.getActiveNetworkInfo().isAvailable()) {
            return false;
        }
        int type = tele.getActiveNetworkInfo().getType();
        return type == ConnectivityManager.TYPE_WIFI;
    }
    
    /**
     * 获取网络类型值，获取服务器端数据URL中需要用到
     * 
     * 属性 ctx
     * @return
     */
    public static String getNT(Context ctx) {
        /**
         * 0 未知
         * 
         * 10 WIFI网络
         * 
         * 20 USB网络
         * 
         * 31 联通
         * 
         * 32 电信
         * 
         * 53 移动
         * 
         * IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)
         * 
         * IMSI共有15位，其结构如下： MCC+MNC+MIN MNC:Mobile NetworkCode，移动网络码，共2位
         * 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
         */
        String imsi = getIMSI(ctx);
        String nt = "0";
        if (isWifiEnable(ctx)) {
            nt = "10";
        } else if (imsi == null) {
            nt = "0";
        } else if (imsi.length() > 5) {
            String mnc = imsi.substring(3, 5);
            if (mnc.equals("00") || mnc.equals("02")) {
                nt = "53";
            } else if (mnc.equals("01")) {
                nt = "31";
            } else if (mnc.equals("03")) {
                nt = "32";
            }
        }
        return nt;
    }
    
    /**
     * 取得IMSI号
     */
    public static String getIMSI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String result = tm.getSubscriberId();
        if (result == null)
            return "";

        return result;
    }
    
    /**
     * 判定是否网络是否连接
     * 
     * @return 无网络 2 3G 1 wifi 0
     */
    public static int checkNetworkToInt(Context context) {

        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr.getActiveNetworkInfo() != null) {

            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            State wifi = wifiNetInfo.getState();
            if (wifi == State.CONNECTED) {
                return 0;
            }
            NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            State mobile = mobNetInfo.getState();
            if (mobile == State.CONNECTED) {
                return 1;
            }
        }
        return 2;
    }
//    /**
//     * 网络是否可用
//     */
//    public synchronized static boolean isNetworkAvailable(Context context) {
//        boolean result = false;
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = null;
//        if (null != connectivityManager) {
//            networkInfo = connectivityManager.getActiveNetworkInfo();
//            if (null != networkInfo && networkInfo.isAvailable() && networkInfo.isConnected()) {
//                result = true;
//            }
//        }
//        return result;
//    }
}
