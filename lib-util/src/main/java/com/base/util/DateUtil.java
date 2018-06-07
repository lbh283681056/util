
package com.base.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;


/**
 * 日期工具类
  * 类名: DateUtil
  * 描述
  * 作者 Comsys-linbinghuang
  * 时间 2014-11-3 下午5:34:40
  *
  */
public class DateUtil {

   
    /**
     * string转成Date类型
     * 属性 dateString
     * 属性 format
     * @return
     */
    public static Date StrToDate(String dateString,String format) {
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
        
    }
    
    /**
     * date转成string类型
     * 属性 date
     * 属性 format
     * @return
     */
    public static String DateToStr(Date date, String format) {

        SimpleDateFormat sdf=new SimpleDateFormat(format);  
        return sdf.format(date);

    }

   
    /**
     * js时间更是转换
     * 属性 time
     * 属性 format
     * @return
     */
    public static String getMilliToDateForTime(String time,String format){
    	time =    time.substring(6, time.length()-2);
    	Date date = new Date(Long.valueOf(time));
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	return formatter.format(date);
    }
  
  
    /**
     * string 转成毫秒
     * 属性 date
     * 属性 format
     * @return
     */
    public static long stringToLong(String date,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dt = null;
        try {
            dt = sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        return dt.getTime();
    } 

    public static final String SHORT_DATE_FORMAT_1 = "yyyy年MM月dd日";

    public static final String SHORT_DATE_FORMAT_2 = "yyyy-MM-dd";

    public static final String LONG_DATE_FORMAT_1 = "yyyyMMddHHmmss";

    /**
     * 24小时制
     */
    public static final String C_TIME_PATTON_24HHMM = "HH:mm";

    /**
     * 12小时制
     */
    public static final String C_TIME_PATTON_12HHMM = "hh:mm";

    /**
     * 中文月份数组
     */
    public static final String[] monthsZh = { "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月" };

    /**
     * 英文月份数组
     */
    public static final String[] monthsEn = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

    /**
     * 获取现在时间
     * 
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     * 
     * 属性 format
     * @return 返回对应格式的字符串
     */
    public static String getStringDateByFormat(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     * 
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     * 
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间
     * 
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDate(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间
     * 
     * @return 返回短时间字符串格式 MM月dd日
     */
    public static String getUpdateDate(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);
        String dateString = date.split("-")[1] + "月" + date.split("-")[2] + "日";
        return dateString;
    }

    /**
     * 获取时间
     * 
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDate(long time, SimpleDateFormat format) {
        Date currentTime = new Date(time);
        String dateString = format.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     * 
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd
     * 
     * 属性 strDate
     * @return
     */
    public static Date strToDateShort(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * 
     * 属性 strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     * 
     * 属性 dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyyMM
     * 
     * 属性 dateDate
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyyMM
     * 
     * 属性 strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 得到现在时间
     * 
     * @return
     */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 提取一个月中的最后一天
     * 
     * 属性 day
     * @return
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 得到现在时间
     * 
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     * 
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     * 
     * 属性 sformat
     *        yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 
     * <br>
     * Description: 
     * 
     * 属性 yearMonthTag
     * 属性 length
     * @return
     */
    /**
     * 根据年月标识，返回前length个月的年月信息
     * 属性 yearMonthTag
     * 属性 length
     * 属性 isZh
     * @return
     */
    public static List<YearMonthInfo> getYearMonthInfo(String yearMonthTag, int length, boolean isZh) {
        List<YearMonthInfo> yearMonthInfoList = new ArrayList<YearMonthInfo>();
        if (6 != yearMonthTag.length())
            return null;
        int month = Integer.parseInt(yearMonthTag.substring(4));
        Date date = strToDate(yearMonthTag);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (int i = 0; i < length; i++) {
            YearMonthInfo yearMonthInfo = new YearMonthInfo();
            yearMonthInfo.tag = dateToStr(calendar.getTime());
            if (isZh) {
                yearMonthInfo.label = monthsZh[month - 1];
            } else {
                yearMonthInfo.label = monthsEn[month - 1];
            }
            month--;
            if (month == 0) {
                month = 12;
            }
            calendar.add(Calendar.MONTH, -1);
            yearMonthInfoList.add(yearMonthInfo);
        }
        return yearMonthInfoList;
    }

    /**
     * 是否在两个日期之间
     * 属性 dateStr1
     * 属性 dateStr2
     * @return
     */
    public static boolean isBetweenDate(String dateStr1, String dateStr2) {
        try {
            long date1 = strToDateShort(dateStr1).getTime();
            long date2 = strToDateShort(dateStr2).getTime();
            long now = new Date().getTime();
            if (date1 < now && now < date2) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

  
    /**
     * 年月信息
     * 作者 linbinghuang
     *
     */
    public static class YearMonthInfo {
        /**
         * 年月标识，例201108,199912
         */
        public String tag;
        /**
         * 年月显示标签，中文:3月；英文：Mar
         */
        public String label;
    };

    
    /**
     * 获取今天日期的毫秒数
     * @return
     */
    public static long getTodayTime() {
        try {
            Date date = new Date();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = dateformat.format(date);
            return dateformat.parse(dateStr).getTime();
        } catch (Exception e) {
           
            return 0;
        }
    }

    /**
     * 日期字符串转换 pattern可以从R.string中获取，datetime_pattern_yyyymmddhhmmss, datetime_pattern_yyyy_mm_dd_hhmmss， date_pattern_chinese,datetime_pattern_chinese等
     * 
     * 属性 context
     * 属性 originalPatternStrId
     * 属性 targetPatternStrId
     * 属性 datetime
     *        日期字符串
     * @return
     */
    public static String formatDateTime(Context context, int originalPatternStrId, int targetPatternStrId, String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(originalPatternStrId), Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            sdf = new SimpleDateFormat(context.getString(targetPatternStrId), Locale.getDefault());
            String target = sdf.format(date);
            return target;
        }
        return null;

    }
    
   
    /**
     *  从公元元年算起
     * 属性 type
     * 属性 time
     * @return
     */
    public static String getTimeByJavaToADZero(String type, String time) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(type);
            Date data = dateFormat.parse(time);
            return data.getTime() + (long) (1970 * 365 + (25 * 20 - 8) - 19 + 5) * 24 * 60 * 60 * 1000 + "0000";

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 时间性能测试
     * 
     * 属性 a
     *        标记的 时间
     * @return
     */
    public static String testDate(long a) {
        long c = System.currentTimeMillis() - a;
        if (c < 1000) {
            return c + "毫秒";
        } else if (c > 1000 && c <= 60000) {
            return c / 1000 + "秒" + c % 1000 + "毫秒";
        } else if (c > 60000 && c <= 60000 * 60) {
            return c / 60000 + "分钟" + c % 60000 / 1000 + "秒" + c % 1000 + "毫秒";
        }
        return "0毫秒";
    }

    
}
