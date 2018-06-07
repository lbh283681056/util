
package com.base.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;

/**
 * 跳转系统的一些页面
  * 类名: SystemIntentUtil
  * 描述 TODO
  * 作者 Comsys-linbinghuang
  * 时间 2014-11-3 下午2:44:58
  *
  */
public class SystemIntentUtil {
  
   /**
    * 跳转到详情页面
 * 属性 context
 * 属性 packageName
 * @return
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
   public static Intent getAppDetailSettingIntent(Context context, String packageName) {
       Intent localIntent = new Intent();
       localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       if (Build.VERSION.SDK_INT >= 9) {
           localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
           localIntent.setData(Uri.fromParts("package", packageName, null));
       } else if (Build.VERSION.SDK_INT <= 8) {
           localIntent.setAction(Intent.ACTION_VIEW);
           localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
           localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName);
       }
       return localIntent;
   }

   
    /**
     * 通过弹界面进行安装
     * 属性 context
     * 属性 filePath
     */
    public static void installFileWithActivity(Context context, String filePath) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    
    /**
     * 跳转相册（有回调函数 onActivityResult）4.3一下
     * 属性 context
     * 属性 request
     */
    public static void toPicActivity(Context context, int request) {
//        Intent intent = new Intent();
//        /* 开启Pictures画面Type设定为image */
//        intent.setType("image/*");
//        /* 使用Intent.ACTION_GET_CONTENT这个Action */
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);  
//        /* 取得相片后返回本画面 */
//        Activity activity = (Activity) context;
//        activity.startActivityForResult(intent, request);
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        /* 取得相片后返回本画面 */
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent, request);
    }
    /**
     * 跳转相册（有回调函数 onActivityResult）4.4以上
     * 属性 context
     * 属性 request
     */
    public static void toPicActivityFor4(Context context, int request) {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT  
        intent.addCategory(Intent.CATEGORY_OPENABLE);  
        intent.setType("image/jpeg");  
        Activity activity = (Activity) context;
        activity. startActivityForResult(intent, request);    
    }
   
//    public static void toPicActivity(Context context,int requestCode4,int rquestCode) {
//     
//        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){  
//            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT  
//            intent.addCategory(Intent.CATEGORY_OPENABLE);  
//            intent.setType("image/jpeg");  
//            Activity activity = (Activity) context;
//            activity. startActivityForResult(intent, requestCode4);    
//        }else{       
//            toPicActivity(context, rquestCode);
//        }
//    }

  
    /**
     * 跳转拍照
     * 属性 context
     * 属性 file
     * 属性 request
     */
    public static void toCapturePicture(Context context, File file, int request) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 构造intent
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(file));
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent, request);

    }

  
    /**
     * 系统进行图片的剪切
     * 属性 context
     * 属性 uri
     * 属性 request
     */
    public static void startPhotoZoom(Context context, Uri uri, int request) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent, request);
    }
   

    /**
     * 将进行剪裁后的图片传递到下一个界面上
     * 属性 picdata
     */
    public static void sentPicToNext(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] photodata = baos.toByteArray();
            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    
    
    /** 
     * final Activity activity  ：调用该方法的Activity实例 
     * long milliseconds ：震动的时长，单位是毫秒 
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒 
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次 
     */  
     public static void Vibrate(final Activity activity, long milliseconds) {   
            Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);   
            vib.vibrate(milliseconds);   
     }   
     public static void Vibrate(final Activity activity, long[] pattern,boolean isRepeat) {   
            Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);   
            vib.vibrate(pattern, isRepeat ? 1 : -1);   
     }   
  
}
