package com.base.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

/**
 * 日志输出
 * 作者 linbinghuang
 *
 */
public class LogUtil {

  
    
    /**
     * 保存日志
     * 属性 path
     * 属性 msgID
     * 属性 log
     * 属性 isLog
     * 属性 fileName
     * @return
     */
    public static String saveLog(String path,String msgID, String log, boolean isLog, String fileName) {
        if (!isLog) {
            return null;
        }
        SimpleDateFormat logTime = new SimpleDateFormat("yyyyMMddHHmmss");
        Date logD = new Date(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        if (msgID != null && !msgID.equals("")) {
            sb.append(logTime.format(logD) + "【" + msgID + "】\n");
        } else {
            sb.append(logTime.format(logD) + "\t\t");
        }
        String logStr = "";
        String splitStr = "body";
        String[] tempStr = log.split(splitStr);
        if (tempStr.length > 1) {
            logStr += tempStr[0] + "\n" + splitStr;
            String body = tempStr[1].replaceAll(",", "");
            tempStr = body.split("]");
            for (int i = 0; i < tempStr.length; i++) {
                if (i != tempStr.length - 2) {
                    logStr += tempStr[i] + "],\n";
                } else {
                    logStr += tempStr[i] + "";
                }
            }
            sb.append(logStr.substring(0, logStr.length() - 4));
        } else {
            sb.append(log);
        }
        sb.append("\n");
        SimpleDateFormat logFt = new SimpleDateFormat("yyyyMMdd");
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.close();
        try {
            long timestamp = System.currentTimeMillis();
            Date d = new Date(timestamp);
            String time = logFt.format(d);
            if (null == fileName || fileName.equals("")) {
                fileName = "log";
            }
            fileName += ("-" + time + ".txt");
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File f = new File(path + fileName);
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream fost = new FileOutputStream(f, true);
                BufferedWriter myo = new BufferedWriter(new OutputStreamWriter(fost, "GBK"));
                myo.write(sb.toString());
                myo.close();
            }
            return fileName;
        } catch (Exception e) {
        }
        return null;
    }

}
