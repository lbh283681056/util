
package com.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

/**
 * 字符串工具类
  * 类名: StringUtil
  * 描述
  * 作者 Comsys-linbinghuang
  * 时间 2014-11-3 下午4:34:09
  *
  */
@SuppressLint("DefaultLocale")
public class StringUtil {

    public static String StrForResId(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 邮箱的正则表达式
     */
    private final static String EMAIL_PATTERN = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    private final static DecimalFormat FORMAT = new DecimalFormat("#00.0");
    public static final String CHARSET_UTF_8 = "UTF-8";

    /**
     * 是否包含空白
     * 
     * 属性 str
     * @return
     */
    public static boolean isContainBlank(CharSequence str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if ((isWhitespace(str.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是空白
     * 
     * 属性 str
     * @return
     */
    public static boolean isBlank(CharSequence str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断单个字符是否是空白
     * 
     * 属性 ch
     * @return
     */
    public static boolean isWhitespace(char ch) {
        if (ch == '　') {
            return true;
        }
        if (Character.isWhitespace(ch)) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(CharSequence s) {
        if ((s == null) || (s.length() <= 0)) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isAnyEmpty(String... strs) {
        final int N = strs.length;
        for (int i = 0; i < N; i++) {
            if (isEmpty(strs[i]))
                return true;
        }

        return false;
    }

    /**
     * 从通过column和cursor获取值
     * 
     * 属性 cursor
     * 属性 columnName
     * @return
     */
    public static String getString(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index != -1) {
            return cursor.getString(index);
        }
        return null;
    }

    /**
     * 判断两字符串是否相等
     * 
     * 属性 s1
     * 属性 s2
     * @return
     */
    public static boolean equal(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if ((s1 != null) && (s2 != null)) {
            return s1.equals(s2);
        }
        return false;
    }

    /**
     * 获取文件扩展名
     * 
     * 属性 fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        int end = fileName.lastIndexOf('.');
        if (end >= 0) {
            return fileName.substring(end + 1);
        }
        return null;
    }

    /**
     * 属性 path
     * @return
     */
    public static String renameRes(String path) {
        if (path == null) {
            return null;
        }
        return path.replace(".png", ".a").replace(".jpg", ".b");
    }

    /**
     * 转null为空
     * 
     * 属性 s
     * @return
     */
    public static String getNotNullString(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    /**
     * 解析int 
     * 属性 str
     * 属性 defaultValue
     * @return
     */
    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 解析long
     * 属性 str
     * 属性 defaultValue
     * @return
     */
    public static long parseLong(String str, Long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     *    文件大小转换
     * 属性 num
     * 属性 scale
     * @return
     */
    public static String parseLongToKbOrMb(long num, int scale) {
        try {
            float scaleNum;
            switch (scale) {
                case 0:
                    scaleNum = 1;
                    break;
                case 1:
                    scaleNum = 10f;
                    break;
                case 2:
                    scaleNum = 100f;
                    break;
                case 3:
                    scaleNum = 1000f;
                    break;
                case 4:
                    scaleNum = 10000f;
                    break;
                default:
                    scaleNum = 1;
            }
            float n = num;
            if (n < 1024) {
                if (scale == 0)
                    return (int) n + "B";
                return Math.round(n * scaleNum) / scaleNum + "B";
            }
            n = n / 1024;
            if (n < 1024) {
                if (scale == 0)
                    return (int) n + "KB";
                return Math.round(n * scaleNum) / scaleNum + "KB";
            }
            n = n / 1024;
            if (n < 1024) {
                if (scale == 0)
                    return (int) n + "MB";
                return Math.round(n * scaleNum) / scaleNum + "MB";
            }
            n = n / 1024;
            if (scale == 0)
                return (int) n + "GB";
            return Math.round(n * scaleNum) / scaleNum + "GB";
        } catch (Exception e) {
            e.printStackTrace();
            return -1 + "B";
        }
    }

    /**
     * 文件大小转换
     * 
     * 属性 num
     * 属性 scale
     * @return
     */
    public static String parseLongToKbOrMb(double num, int scale) {

        float scaleNum;
        switch (scale) {
            case 0:
                scaleNum = 1;
                break;
            case 1:
                scaleNum = 10f;
                break;
            case 2:
                scaleNum = 100f;
                break;
            case 3:
                scaleNum = 1000f;
                break;
            case 4:
                scaleNum = 10000f;
                break;
            default:
                scaleNum = 1;
        }
        if (num < 1024) {
            if (scale == 0)
                return (int) num + "B";
            return Math.round(num * scaleNum) / scaleNum + "B";
        }
        num = num / 1024;
        if (num < 1024) {
            if (scale == 0)
                return (int) num + "KB";
            return Math.round(num * scaleNum) / scaleNum + "KB";
        }
        num = num / 1024;
        if (num < 1024) {
            if (scale == 0)
                return (int) num + "MB";
            return Math.round(num * scaleNum) / scaleNum + "MB";
        }
        num = num / 1024;
        if (scale == 0)
            return (int) num + "GB";
        return Math.round(num * scaleNum) / scaleNum + "GB";
    }


    /**
     * 获得节点text
     * 
     * 属性 node
     * @return
     */
    public static String getNodeText(Node node) {
        String text = null;
        Node tNode = node.getFirstChild();
        if ((tNode != null) && "#text".equals(tNode.getNodeName())) {
            text = tNode.getNodeValue();
            if (text != null) {
                text = text.trim();
            }
        }
        return text;
    }

    /**
     * 判断传入的字符串是否是数字类型
     * 
     * 属性 sNum
     * @return
     */
    public static boolean isNumberic(String sNum) {
        try {
            Float.parseFloat(sNum);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 验证注册名称是否正确
     * 
     * 属性 str
     * @return
     */
    public static boolean checkOnlyContainCharaterAndNumbers(String str) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证邮件地址格式是否正确
     * 
     * 属性 str
     * @return
     */
    public static boolean checkValidMailAddress(String str) {
        Pattern p1 = Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
        Matcher m = p1.matcher(str);
        return m.matches();
    }

    /**
     * 验证是否正常
     * 
     * 属性 str
     * @return
     */
    public static boolean checkValidMobilePhoneNumber(String str) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 获取非null的字符串
     * 
     * 属性 str
     * @return 为null则返回空字符串，否则返回字符串本身
     */
    public static String checkString(String str) {
        if (null == str)
            return "";
        return str;
    }

    /**
     * OpenHome 2.6.2版本的key
     * 
     * 属性 actName
     * 属性 packName
     * 属性 id
     * @return
     */
    public static String parseKeyNew(String actName, String packName, String id) {
        actName = actName.toLowerCase();
        packName = packName.toLowerCase();
        id = id.toLowerCase(Locale.getDefault());
        int ret = id.hashCode() + (-25);
        try {
            char[] buf = new char[actName.length()]; // v0
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (char) (actName.toCharArray()[i] << 2);
            }

            for (int i = 0; i < buf.length; i++) {
                if (i % buf[i] == 0) {
                    ret += buf[i];
                } else {
                    ret -= buf[i];
                }
            }
            ret = ret << 2;
            ret = ret >> 3;
            char[] buf2 = new char[packName.length()];
            for (int i = 0; i < buf2.length - 2; i++) {
                buf2[i] = (char) ((packName.toCharArray()[i] + buf[i % buf.length]) << 3);
                if ((buf2[i] * 2 - buf[i] * 2) % 2 == 0) {
                    ret += buf2[i];
                } else {
                    ret -= buf2[i];
                }
            }
            ret = Math.abs(ret);
        } catch (Exception e) {
            return "";
        }
        return "i" + ret;
    }

    /**
     * getKeysByValue
     * 
     * 属性 map
     * 属性 value
     * @return
     */
    public static List<String> getKeysByValue(HashMap<String, String> map, String value) {
        ArrayList<String> list = new ArrayList<String>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String k = it.next();
            String v = map.get(k);
            if (equal(v, value)) {
                list.add(k);
            }
        }
        return list;
    }

    /**
     * 根据ResolveInfo获取AppKey 
     * 
     * 属性 info
     * @return
     */
    public static String getAppKey(ResolveInfo info) {
        String appKey = (info.activityInfo.packageName + "|" + info.activityInfo.name)
                .toLowerCase();
        return appKey;
    }

    /**
     *  根据ComponentName获取AppKey 
     * 
     * 属性 info
     * @return
     */
    public static String getAppKey(ComponentName info) {
        String appKey = (info.getPackageName() + "|" + info.getClassName()).toLowerCase();
        return appKey;
    }

    /**
     * 根据pck,class获取AppKey 
     * 
     * 属性 pck
     * 属性 clazz
     * @return
     */
    public static String getAppKey(String pck, String clazz) {
        String appKey = (pck.trim() + "|" + clazz.trim()).toLowerCase();
        return appKey;
    }

    /**
     *  根据ActivityInfo获取AppKey
     * 
     * 属性 info
     * @return
     */
    public static String getAppKey(ActivityInfo info) {
        String appKey = (info.packageName + "|" + info.name).toLowerCase();
        return appKey;
    }

    /**
   文件后缀名过滤 
     * 
     * 属性 name
     * 属性 arrayId
     * @return
     */
    public static boolean checkSuffixsWithInStringArray(Context context, String name, int arrayId) {
        String[] fileEndings = context.getResources().getStringArray(arrayId);
        for (String aEnd : fileEndings) {
            if (name.toLowerCase().endsWith(aEnd))
                return true;
        }
        return false;
    }

    /**
     * 过滤掉插入字符串中有特殊字符的字符换，返回合法的字符串
     */
    public static String filtrateInsertParam(CharSequence srcParam) {
        if (StringUtil.isEmpty(srcParam)) {
            return "";
        }
        String result = srcParam.toString().replace("'", "''");
        result = result.replace("?", "");
        return result;
    }

    /**
     * 获取随机数，0 和 max之间 ，不包含max
     * 
     * 属性 max
     * @return
     */
    public static int getRandom(int max) {
        return Integer.parseInt(String.valueOf(System.currentTimeMillis() % max));
    }

    public static String getPkgName(String component) {
        return component.substring(component.indexOf("{") + 1, component.lastIndexOf("/"));
    }

    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics localFontMetrics = paint.getFontMetrics();
        return (float) Math.ceil(localFontMetrics.descent - localFontMetrics.ascent);
    }

    public static void getChineseFontSize(Paint paint, Rect outRect) {
        paint.getTextBounds("桌", 0, 1, outRect);
    }

    /**
     * 绘制文本，自动换行
     * 属性 canvas
     * 属性 paint
     * 属性 text
     * 属性 paddingTop
     * 属性 paddingLeft
     */
    public static void drawText(Canvas canvas, Paint paint, String text, int width, int paddingTop,
            int paddingLeft) {
        int height = paint.getFontMetricsInt(null);
        int verticalSpace = 5;
        int lenght = text.length();
        int validateLength = paint.breakText(text, true, width, null);
        int count = (lenght - 1) / validateLength + 1;
        int start = 0;
        int top = paddingTop;
        for (int i = 0; i < count; i++) {
            int end = start + validateLength;
            if (end >= lenght) {
                end = lenght;
            }
            canvas.drawText(text.substring(start, end), paddingLeft, top + i
                    * (verticalSpace + height), paint);
            start = end;
        }
    }

    /**
   	颜色数值变成16进制
      * 属性 属性 color
      * 属性 @return
      * @return String    
      * 异常
      */
    public static String Color2String(int color) {
        String A = "";
        String R = "";
        String G = "";
        String B = "";
        try {
            A = Integer.toHexString(Color.alpha(color));
            A = A.length() < 2 ? ('0' + A) : A;
            R = Integer.toHexString(Color.red(color));
            R = R.length() < 2 ? ('0' + R) : R;
            G = Integer.toHexString(Color.green(color));
            G = G.length() < 2 ? ('0' + G) : G;
            B = Integer.toHexString(Color.blue(color));
            B = B.length() < 2 ? ('0' + B) : B;
        } catch (Exception e) {
            return "#FFFFFF";
        }
        return '#' + A + R + G + B;
    }

    public static String regularSymbolFilter(String input) {
        if (TextUtils.isEmpty(input))
            return "";

        return input.replaceAll("\\$|\\^|\\*|\\(|\\)|\\-|\\+|\\{|\\}|\\||\\.|\\?|\\[|\\]|\\&|\\\\",
                "");
    }

    /**
     * utf8
     * 
     * 属性 url
     * @return
     */
    public static String utf8URLencode(String url) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);
            if ((c >= 0) && (c <= 255)) {
                result.append(c);
            } else {
                byte[] b = new byte[0];
                try {
                    b = Character.toString(c).getBytes(CHARSET_UTF_8);
                } catch (Exception ex) {
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    result.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return result.toString();
    }

    /**
     *  UrlDecode是对字符串进行URL解码
      * 属性 属性 s
      * 属性 @return
      * @return String    
      * 异常
      */
    public static String utf8URLdecode(String s) {
        try {
            return URLDecoder.decode(s, CHARSET_UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 截短字符串
     * 
     * 属性 str
     * 属性 length
     * @return
     */
    public static String subString(String str, int length) {
        if (str != null && str.length() > length) {
            str = str.substring(0, length);
        }
        return str;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isLongType(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * InputStream转为String
     * 
     * 属性 is
     * @return
     */
    public static String inputStream2String(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 验证是否是邮箱
     * 
     * 属性 s
     * @return
     */
    public static boolean isEmail(String s) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * 获取字符串的真实长度，包括汉字
     * 
     * 属性 str
     * @return
     * 异常 Exception
     */
    public static int getStringRealLength(String str) throws Exception {
        String str1 = new String(str.getBytes("GBK"), "iso-8859-1");
        return str1.length();
    }

    /**
     * 返回百分比
     * 
     * 属性 percentage
     * @return
     */
    public static String getPercentage(float percentage) {
        return FORMAT.format(percentage).toString() + "%";
    }

    /**
     * 单位转换 字节 转换成 kb/mb/gb
     * 
     * 属性 b 需要转换的 字节
     * @return
     */
    static DecimalFormat df = new DecimalFormat("#.0");

    public static String btoKBorMBorGBForDecimals(long b) {
        double integer = 0;
        String unit = "";
        if (b / 1024 < 1) {
            integer = b;
            unit = "B";
        } else if (b / (1024 * 1024) < 1) {
            integer = (double) b / 1024;
            unit = "KB";
        } else if (b / (1024 * 1024 * 1024) < 1) {
            integer = (double) b / (1024 * 1024);
            unit = "MB";
        } else if (b / (1024 * 1024 * 1024 * 1024) < 1) {
            integer = (double) b / (1024 * 1024 * 1024);
            unit = "GB";
        }
        if (integer == 0) {
            return "0B";
        }
        return df.format(integer) + unit;
    }
    
    /**
     * 单位转换 字节 转换成 kb/mb/gb
     * 
     * 属性 b
     *        需要转换的 字节
     * @return
     */
    public static String btoKBorMBorGB(long b) {
        if (b / 1024 < 1) {
            return (b) + "B";
        } else if (b / (1024 * 1024) < 1) {
            return (b / 1024) + "KB";
        } else if (b / (1024 * 1024 * 1024) < 1) {
            return (b / (1024 * 1024)) + "MB";
        } else if (b / (1024 * 1024 * 1024 * 1024) < 1) {
            return b / (1024 * 1024 * 1024) + "GB";
        }
        return 0 + "B";
    }

     /**
      * getMemorySizeString
      * 描述 单位转换 字节 转换成 kb/mb/gb/tb
      * 属性 属性 size
      * 属性 @return
      * @return String    
      * 异常
      */
    public static String getMemorySizeString(long size) {
            float result = size;
            if (result < 1024) {
                BigDecimal temp = new BigDecimal(result);
                temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                return temp + "Bytes";
            } else {
                result = result / 1024;
                if (result < 1024) {
                    BigDecimal temp = new BigDecimal(result);
                    temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                    return temp + "KB";
                } else {
                    result = result / 1024;
                    if (result < 1024) {
                        BigDecimal temp = new BigDecimal(result);
                        temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                        return temp + "MB";
                    } else {
                        result = result / 1024;
                        if (result < 1024) {
                            BigDecimal temp = new BigDecimal(result);
                            temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                            return temp + "GB";
                        } else {
                            result = result / 1024;
                            BigDecimal temp = new BigDecimal(result);
                            temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                            return temp + "TB";
                        }
                    }
                }
            }
        }

        /**
         *  转成百分数值
          * 属性 属性 percent
          * 属性 @return
          * @return String    
          * 异常
          */
        public static String getMemoryPercentString(float percent) {
            BigDecimal result = new BigDecimal(percent * 100.0f);
            return result.setScale(2, BigDecimal.ROUND_HALF_UP) + "%";
        }
}
