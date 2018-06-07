package com.base.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.StatFs;

/**
 * 存储工具类
  * 类名: MemoryUtil
  * 描述
  * 作者 Comsys-linbinghuang
  * 时间 2014-11-3 下午1:59:34
  *
  */
@SuppressLint("NewApi")
public class MemoryUtil {

	/**
	 * 获取空闲的存储空间长度
	 * @return
	 */
	public static long getStorageFreeSize() {
		long systemFreesize = MemoryUtil.getSystemFreeSize();
		long sdFreesize = 0;
		if (MemoryUtil.ExistSDCard()) {
			sdFreesize = MemoryUtil.getSDFreeSize();
		}
		return (systemFreesize + sdFreesize) * 1024;
	}

	/**
	 * 获取总的存储空间
	 * @return
	 */
	public static long getStorageSize() {
		return getSystemSize() + getSDSize();
	}
	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean ExistSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	/**
	 * 判断总的剩余空间是否大于要下载的文件大小（单位：B）
	 * 
	 * 属性 contentLength
	 * @return
	 */
	public static boolean isEnoughMemorySize(int contentLength) {
		if (!isSDcardEnoughMemorySize(contentLength)) {
			return checkSystemFressSize(contentLength);
		}
		return true;

	}

	/**
	  * 
	  * 描述
	  * 属性 属性 contentLength
	  * 属性 @return
	  * @return boolean    
	  * 异常
	  */
	/**
	 * 判断文件是否大于外部存储空间
	 * 属性 contentLength
	 * @return
	 */
	public static boolean isSDcardEnoughMemorySize(int contentLength) {
		if (ExistSDCard()) {
			if (getSDFreeSize() * 1024 >= contentLength) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断文件是否大于内部存储空间
	 * 属性 contentLength
	 * @return
	 */
	public static boolean checkSystemFressSize(int contentLength) {
        if (MemoryUtil.getSystemFreeSize() * 1024 >= contentLength) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
	 *  判断内存空间是否足够
	 * 属性 contentLength
	 * @return
	 */
	public static int isEnoughMemorySizeToRetrunInt(int contentLength) {
		if (ExistSDCard()) {
			if (getSDFreeSize() * 1000 >= contentLength) {
				return 0;
			}
		}
		if (checkSystemFressSize(contentLength)) {
			return 1;
		}
		return -1;
	}

	

	
	/**
	 * SD卡总容量
	 * @return
	 */
	public long getSDAllSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		long allBlocks = sf.getBlockCount();
		// 返回SD卡大小
		// return allBlocks * blockSize; //单位Byte
		// return (allBlocks * blockSize)/1024; //单位KB
		return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
	}

	/**
	 * 获取SD卡剩余空间大小
	 * @return
	 */
	public static long getSDFreeSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		return (blockSize * freeBlocks) / 1024;// 单位kb
	}

	
	/**
	 * 获取手机内存剩余空间大小
	 * @return
	 */
	public static long getSystemFreeSize() {
		File root = Environment.getDataDirectory();
		StatFs sf = new StatFs(root.getPath());
		long blockSize = sf.getBlockSize();
		long availCount = sf.getAvailableBlocks();
		return (availCount * blockSize) / 1024; // 单位kb
	}


	/**
	 * 内部存储的总量
	 * @return
	 */
	public static long getSystemSize() {
		File root = Environment.getDataDirectory();
		return root.getTotalSpace();
	}


	/**
	 * sd卡的总量
	 * @return
	 */
	public static long getSDSize() {
		if (ExistSDCard()) {
			File path = Environment.getExternalStorageDirectory();
			return path.getTotalSpace();
		}
		return 0;
	}

	
}
