package com.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 与手机相关的信息
 * 类名: TelephoneUtil
 * 描述
 * 作者 Comsys-linbinghuang
 * 时间 2014-11-3 下午4:34:28
 *
 */
public class TelephoneUtil {

    /**
     * 取得IMEI号
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * @Title: getMachineName
     * @return String
     * 异常
     */
    public static String getMachineName() {
        return Build.MODEL;
    }

    /**
     * 获取字符串型的固件版本，如1.5、1.6、2.1
     * 
     * @return
     */
    public static String getFirmWareVersion() {
        final String version_3 = "1.5";
        final String version_4 = "1.6";
        final String version_5 = "2.0";
        final String version_6 = "2.0.1";
        final String version_7 = "2.1";
        final String version_8 = "2.2";
        final String version_9 = "2.3";
        final String version_10 = "2.3.3";
        final String version_11 = "3.0";
        final String version_12 = "3.1";
        final String version_13 = "3.2";
        final String version_14 = "4.0";
        final String version_15 = "4.0.3";
        final String version_16 = "4.1.1";
        final String version_17 = "4.2";
        final String version_18 = "4.3";
        final String version_19 = "4.4.2";
        String versionName = "";
        try {

            @SuppressWarnings("deprecation")
            int version = Integer.parseInt(Build.VERSION.SDK);
            switch (version) {
                case 3:
                    versionName = version_3;
                    break;
                case 4:
                    versionName = version_4;
                    break;
                case 5:
                    versionName = version_5;
                    break;
                case 6:
                    versionName = version_6;
                    break;
                case 7:
                    versionName = version_7;
                    break;
                case 8:
                    versionName = version_8;
                    break;
                case 9:
                    versionName = version_9;
                    break;
                case 10:
                    versionName = version_10;
                    break;
                case 11:
                    versionName = version_11;
                    break;
                case 12:
                    versionName = version_12;
                    break;
                case 13:
                    versionName = version_13;
                    break;
                case 14:
                    versionName = version_14;
                    break;
                case 15:
                    versionName = version_15;
                    break;
                case 16:
                    versionName = version_16;
                    break;
                case 17:
                    versionName = version_17;
                    break;
                case 18:
                    versionName = version_18;
                    break;
                case 19:
                    versionName = version_19;
                    break;
                default:
                    versionName = version_19;
            }
        } catch (Exception e) {
            versionName = version_7;
        }
        return versionName;
    }

    /**
     * 获取软件版本名称
     */
    public static String getVersionName(Context ctx) {
        String versionName = "";
        try {
            PackageInfo packageinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_INSTRUMENTATION);
            versionName = packageinfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取软件版本号 code
     */
    public static int getVersionCode(Context ctx) {
        int versionCode = 0;
        try {
            PackageInfo packageinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_INSTRUMENTATION);
            versionCode = packageinfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 比较versionName,是否存在新版本
     * 
     * 属性 newVersionName 新版本号
     * 属性 oldVersionName
     * @return 新版本号> 旧版本号 return true
     */
    @SuppressLint("DefaultLocale")
    public static boolean isExistNewVersion(String newVersionName, String oldVersionName) {
        if (oldVersionName.toLowerCase().startsWith("v")) {
            oldVersionName = oldVersionName.substring(1, oldVersionName.length());
        }
        if (newVersionName.toLowerCase().startsWith("v")) {
            newVersionName = newVersionName.substring(1, oldVersionName.length());
        }

        if (oldVersionName == null || newVersionName == null) {
            return false;
        }
        if (oldVersionName.trim().length() == 0 || newVersionName.trim().length() == 0) {
            return false;
        }
        try {
            List<String> codes = parser(oldVersionName.trim(), '.');
            List<String> versionCodes = parser(newVersionName.trim(), '.');
            for (int i = 0; i < codes.size(); i++) {
                if (i > (versionCodes.size() - 1)) {
                    return false;
                }
                int a = Integer.parseInt(codes.get(i).trim());
                int b = Integer.parseInt(versionCodes.get(i).trim());
                if (a < b) {
                    return true;
                } else if (a > b) {
                    return false;
                }
            }
            if (codes.size() < versionCodes.size()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 2.60.3=>[2,60,3]
     * 
     * 属性 value
     * 属性 c
     * @return
     */
    private static List<String> parser(String value, char c) {
        List<String> ss = new ArrayList<String>();
        char[] cs = value.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cs.length; i++) {
            if (c == cs[i]) {
                ss.add(sb.toString());
                sb = new StringBuffer();
                continue;
            }
            sb.append(cs[i]);
        }
        if (sb.length() > 0) {
            ss.add(sb.toString());
        }
        return ss;
    }

    /**
     * sim卡是否存在
     */
    public static boolean isSimExist(Context ctx) {
        TelephonyManager manager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getSimState() == TelephonyManager.SIM_STATE_ABSENT)
            return false;
        else
            return true;
    }

    /**
     * 查询系统广播
     */
    public static boolean queryBroadcastReceiver(Context ctx, String actionName) {
        PackageManager pm = ctx.getPackageManager();
        try {
            Intent intent = new Intent(actionName);
            List<ResolveInfo> apps = pm.queryBroadcastReceivers(intent, 0);
            if (apps.isEmpty())
                return false;
            else
                return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取IP地址
     */
    public static String getWifiAddress(Context ctx) {

        try {
            // 获取wifi服务
            WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            // 判断wifi是否开启
            if (wifiManager.isWifiEnabled()) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                String ip = intToIp(ipAddress);
                return ip;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取数字型API_LEVEL 如：4、6、7
     * 
     * @return
     */
    public static int getApiLevel() {
        int apiLevel = 7;
        try {
            apiLevel = Build.VERSION.SDK_INT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiLevel;
        // android.os.Build.VERSION.SDK_INT Since: API Level 4
        // return android.os.Build.VERSION.SDK_INT;
    }

    public static String getCPUABI() {
        String abi = Build.CPU_ABI;
        abi = (abi == null || abi.trim().length() == 0) ? "" : abi;
        // 检视是否有第二类型，1.6没有这个字段
        try {
            String cpuAbi2 = Build.class.getField("CPU_ABI2").get(null).toString();
            cpuAbi2 = (cpuAbi2 == null || cpuAbi2.trim().length() == 0) ? null : cpuAbi2;
            if (cpuAbi2 != null) {
                abi = abi + "," + cpuAbi2;
            }
        } catch (Exception e) {
        }
        return abi;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
                + (i >> 24 & 0xFF);
    }

    /**
     * 是否中文环境
     */
    public static boolean isZh(Context ctx) {
        Locale lo = ctx.getResources().getConfiguration().locale;
        if (lo.getLanguage().equals("zh"))
            return true;
        return false;
    }

    /**
     * 是否拥有root权限
     * 
     * @return
     */
    public static boolean hasRootPermission() {
        boolean rooted = true;
        try {
            File su = new File("/system/bin/su");
            if (su.exists() == false) {
                su = new File("/system/xbin/su");
                if (su.exists() == false) {
                    rooted = false;
                }
            }
        } catch (Exception e) {
            rooted = false;
        }
        return rooted;
    }

    /**
     * 获取当前语言
     * 
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * <br>
     * Description: 获取手机mac地址 <br>
     * 
     * 属性 ctx
     * @return
     */
    public static String getLocalMacAddress(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * <br>
     * Description: 获取手机上网类型(cmwap/cmnet/wifi/uniwap/uninet) <br>
     * 
     * 属性 ctx
     * @return
     */
    public static String getNetworkTypeName(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null == info || null == info.getTypeName()) {
            return "unknown";
        }
        return info.getTypeName();
    }

    /**
     * 是否是小米2手机
     * 
     * @return
     */
    public static boolean isMI2Moble() {
        return TelephoneUtil.getMachineName().contains("MI 2");
    }

    /**
     * 通过域名获取IP
     * 
     * 属性 host
     * @return
     */
    public static String GetInetAddress(String host) {
        String IPAddress = "";
        InetAddress ReturnStr1 = null;
        try {
            ReturnStr1 = InetAddress.getByName(host);
            IPAddress = ReturnStr1.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return IPAddress;
        }
        return IPAddress;
    }
    
    /**
      * 获取android设备号
      * 描述
      * 属性 属性 context
      * 属性 @return
      * @return String    
      * 异常
      */
    public static String getAndroidId(Context context) {
        if (context == null) {
            return null;
        }
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }
    /**
     * 属性 propName
     * @return
     * 描述 获取系统属性
     */
    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try
        {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex)
        {
            ex.printStackTrace();
            // Log.e("FFF", "Unable to read sysprop " + propName, ex);
            return "";
        } finally
        {
            if (input != null)
            {
                try
                {
                    input.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }
    
   
    /**
     * 获取状态栏高度
     * 属性 context
     * @return
     */
    public static float getStatusBarHeight(Context context) {

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Log.e("FFF", "获取状态栏高度失败");
            e1.printStackTrace();
        }
        return sbar;
    }
}
