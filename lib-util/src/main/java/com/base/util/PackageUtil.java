package com.base.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;


/**
 *  包相关工具类
  * 类名: PackageUtil
  * 描述 TODO
  * 作者 Comsys-linbinghuang
  * 时间 2014-11-3 下午3:58:39
  *
  */
public class PackageUtil {

	/** BLUE STACKS 自带应用标志 */
	private final static String BLUE_STACK_FLAG = "bluestacks";


    /**
     * 获取versionName 
     * 属性 context
     * 属性 packageName
     * @return
     */
    public static String getVersionName(Context context, String packageName) {
        PackageInfo pInfo = null;
        String rs = "";
        try {
            pInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
            rs = pInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

   /**
    * apk是否安装
 * 属性 context
 * 属性 packageName
 * @return
 */
public static  boolean checkAPP(Context context,String packageName) {
       final PackageManager packageManager = context.getPackageManager();
       // 获取所有已安装程序的包信息
       List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
       for (int i = 0; i < pinfo.size(); i++) {
           if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
               return true;
       }
       return false;
   }
	/**
	 * 获取用户程序列表
	 * 
	 * 属性 context
	 * @return
	 */
	public static List<PackageInfo> getSystemApp(Context context) {
		PackageManager pManager = context.getPackageManager();
		List<PackageInfo> userInfos = new ArrayList<PackageInfo>();
		List<PackageInfo> packageInfos = pManager.getInstalledPackages(0);
		for (PackageInfo pi : packageInfos) {
			if (pi.packageName.contains(BLUE_STACK_FLAG))
				// 过滤掉BLUE STACKS 的应用
				continue;
			if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				userInfos.add(pi);
			}
		}
		return userInfos;
	}

	/**
	 * 获取用户程序列表
	 * 
	 * 属性 context
	 * @return
	 */
	public static List<PackageInfo> getUserApp(Context context) {
		PackageManager pManager = context.getPackageManager();
		List<PackageInfo> userInfos = new ArrayList<PackageInfo>();
		List<PackageInfo> packageInfos = pManager.getInstalledPackages(0);
		for (PackageInfo pi : packageInfos) {
			if (pi.packageName.contains(BLUE_STACK_FLAG))
				// 过滤掉BLUE STACKS 的应用
				continue;
			if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && (pi.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
				userInfos.add(pi);
			}
		}
		return userInfos;
	}

	/**
	 * 通过包名获取版本号
	 * 
	 * 属性 packageName
	 * 属性 context
	 * @return
	 */
	public static String getVersionNameFromPackageName(String packageName, Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pinfo;
		try {
			pinfo = pm.getPackageInfo(packageName, 0);
			return pinfo.versionName;
		} catch (NameNotFoundException e) {
			// e.printStackTrace();
			return null;
		}
	}

	
	
	/**
	 * 安装应用程序,普通安装方式
	 * 属性 ctx
	 * 属性 path
	 * @return
	 */
	public static boolean installApplicationNormal(Context ctx, String path) {
		return installApplicationNormal(ctx, new File(path));
	}

	

	/**
	 * 安装应用程序,普通安装方式
	 * 属性 ctx
	 * 属性 mainFile
	 * @return
	 */
	public static boolean installApplicationNormal(Context ctx, File mainFile) {
		try {
			Uri data = Uri.fromFile(mainFile);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(data, "application/vnd.android.package-archive");
			ctx.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 卸载应用程序，普通卸载方式
	 * 
	 * 属性 ctx
	 * 属性 packageName
	 * @return
	 */
	public static boolean unInstallApplicationNormal(Context ctx, String packageName) {
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		ctx.startActivity(uninstallIntent);
		return true;
	}

	/**
	 * 启动APK
	 * 
	 * 属性 context
	 * 属性 packageName
	 */
	public static void lanuchApk(Context context, String packageName) {
		Intent intent = new Intent();
		PackageManager packageManager = context.getPackageManager();
		intent = packageManager.getLaunchIntentForPackage(packageName);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	
	/**
	 * 软件是否可以更新
	 * 属性 context
	 * 属性 pkgName
	 * 属性 versionCode
	 * @return
	 */
	public static boolean isAppUpdata(Context context, String pkgName, int versionCode) {
		List<PackageInfo> infos = getUserApp(context);
		for (PackageInfo info : infos) {
			if (info.packageName.equals(pkgName)) {
				if (versionCode > info.versionCode) {
					return true;
				}
				break;
			}

		}
		return false;
	}


	/**
	 * 应用状态
	 * 
	 * 属性 context
	 *        上下文
	 * 属性 packageName
	 *        包名
	 * 属性 versionCode
	 *        版本号
	 * @return 0表示 可更新 1 安装且版本最新 -1未安装
	 */
	public static int isApkStatueToInt(Context context, String packageName, int versionCode) {
		PackageManager pm;
		PackageInfo packageInfo;
		try {
			pm = context.getPackageManager();
			packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			if (versionCode > packageInfo.versionCode) {
				return 0;
			} else {
				return 1;
			}
		} catch (NameNotFoundException e) {
			return -1;
		} catch (Exception e) {
			return -1;
		}
	}
	
    /**
     * 查看程序是否已经启动
     * 
     * 属性 context
     * 属性 PackageName
     * @return
     */
    public static boolean isServiceStarted(Context context, String PackageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        for (RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(PackageName) && info.baseActivity.getPackageName().equals(PackageName)) {
                isAppRunning = true;
                // find it, break
                break;
            }
        }
        return isAppRunning;
    }
    
    /**
     * 判断类表示的Service是否在运行
     * 
     * 属性 context
     * 属性 clazz
     * @return
     */
    public static boolean isServiceRunning(Context context, Class clazz) {
        if (context == null || clazz == null) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> list = am.getRunningServices(Integer.MAX_VALUE);
        for (RunningServiceInfo info : list) {
            if (info.service.getClassName().equals(clazz.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断类表示的Service是否在运行
     * 
     * 属性 context
     * 属性 clazz
     * @return
     */
    public static boolean isServiceRunning(Context context, String clazz) {
        if (context == null || clazz == null) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> list = am.getRunningServices(Integer.MAX_VALUE);
        for (RunningServiceInfo info : list) {
            if (info.service.getClassName().equals(clazz)) {
                return true;
            }
        }
        return false;
    }
    
  
    
    /**
     * 静默卸载 需要root
     * 属性 packageName
     * @return
     */
    public static int uninstallPackage(String packageName) {
        int result = -1;
        DataOutputStream dos = null;

        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());

            dos.writeBytes("pm uninstall " + packageName + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    /**
     * 静默安装 需要root
     * 属性 packagePath
     */
    public static void installPackage(final String packagePath) {
        new Thread() {
            public void run() {
                Process process = null;
                OutputStream out = null;
                InputStream in = null;
                try {
                    // 请求root
                    process = Runtime.getRuntime().exec("su");
                    out = process.getOutputStream(); // 调用安装
                    out.write(("pm install -r " + packagePath + "\n").getBytes());
                    out.flush();
                    in = process.getInputStream();
                    int len = 0;
                    byte[] bs = new byte[256];
                    while (-1 != (len = in.read(bs))) {
                        String state = new String(bs, 0, len);
                        if (state.equals("Success\n")) {
                            // 安装后的要干嘛
                        } else {

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.flush();
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
  
    /**
     * 手机是否root
     * @return
     */
    public static boolean isRooted() {

        // get from build info
        String buildTags = Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }
        // try executing commands
        return canExecuteCommand("/system/xbin/which su") || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
    }
    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }

        return executedSuccesfully;
    }
    /**
     * 手机是否root
     * 
     * @return
     */
    public static boolean isRoot() {
        boolean isRoot = false;
        String sys = System.getenv("PATH");
        ArrayList<String> commands = new ArrayList<String>();
        String[] path = sys.split(":");
        for (int i = 0; i < path.length; i++) {
            String commod = "ls -l " + path[i] + "/su";
            commands.add(commod);
            System.out.println("commod : " + commod);
        }
        ArrayList<String> res = run("/system/bin/sh", commands);
        String response = "";
        for (int i = 0; i < res.size(); i++) {
            response += res.get(i);
        }
        int inavailableCount = 0;
        String root = "-rwsr-sr-x root root";
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).contains("No such file or directory") || res.get(i).contains("Permission denied")) {
                inavailableCount++;
            }
        }
        return inavailableCount < res.size();
    }
    
    /**
     * 批量运行命令行
     * 属性 shell
     * 属性 commands
     * @return
     */
    private static ArrayList run(String shell, ArrayList<String> commands) {
        ArrayList output = new ArrayList();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(shell);
            BufferedOutputStream shellInput = new BufferedOutputStream(process.getOutputStream());
            BufferedReader shellOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            for (String command : commands) {
                shellInput.write((command + " 2>&1\n").getBytes());
            }

            shellInput.write("exit\n".getBytes());
            shellInput.flush();

            String line;
            while ((line = shellOutput.readLine()) != null) {
                output.add(line);
            }

            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            process.destroy();
        }

        return output;
    }
    /**
     * 打开应用
     * 
     * 属性 context
     *        上下文对象
     * 属性 packageName
     *        包名
     * 异常 NameNotFoundException
     *         没找到包名
     */
    public static void getOpenApp(Context context, String packageName) throws NameNotFoundException {
        PackageInfo pi = null;
        pi = context.getPackageManager().getPackageInfo(packageName, 0);
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String packageName1 = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName1, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }

    }
    
 
    /**
     * 获取包的详细信息
     * 属性 context
     * 属性 packageName
     * @return
     */
    public static PackageInfo getPackageInfo(Context context, String packageName) {
        if (context == null || packageName == null) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
 
    /**
     *  过滤系统应用
     * 属性 lpi
     * @return
     */
    public static List<PackageInfo> getNonSysAppFromList(List<PackageInfo> lpi) {
        Iterator<PackageInfo> ite = lpi.iterator();
        PackageInfo pi = null;
        while (ite.hasNext()) {
            pi = ite.next();
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                ite.remove();
            }
        }
        return lpi;
    }

  
    /**
     * 应用是否安装
     * 属性 context
     * 属性 packageName
     * @return
     */
    public static boolean isPackageInstalled(Context context, String packageName) {
        if (packageName == null) {
            return false;
        }

        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);

        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
  
    /**
     *  获取已安装的应用
     * 属性 context
     * @return
     */
    public static List<PackageInfo> getInstalledPackages(Context context) {
        return context.getPackageManager().getInstalledPackages(0);
    }
 
    /**
     * 应用安装的位置
     * 作者 linbinghuang
     *
     */
    public static enum InstallLocation {
        PHONE_UNKNOW, ROM, SDCARD, PHONE;
    }
public static InstallLocation getInstalledLocation(Context context, String packageName) {
  if (Build.VERSION.SDK_INT < 8) {
      return InstallLocation.PHONE_UNKNOW;
  }
  PackageManager pm = context.getPackageManager();
  try {
      PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
      if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
          return InstallLocation.ROM;
      }
      Field field = null;
      try {
          field = PackageInfo.class.getField("installLocation");
      } catch (Exception e2) {
          try {
              field = ApplicationInfo.class.getField("installLocation");
          } catch (Exception e) {
          }
      }
      if (field != null) {
          int installLocation = 0;
          try {
              installLocation = field.getInt(packageInfo);
          } catch (Exception e) {
              try {
                  installLocation = field.getInt(packageInfo.applicationInfo);
              } catch (Exception e1) {
              }
          }

          if (installLocation != -1 && installLocation != 1) {
              String location = packageInfo.applicationInfo.sourceDir;
              if (location.startsWith("/data/app") || location.startsWith("/system/app")) {
                  return InstallLocation.PHONE;
              } else {
                  return InstallLocation.SDCARD;
              }
          }
      }
  } catch (Exception e) {
  }
  return InstallLocation.PHONE_UNKNOW;
}


public static final int internalOnly = 1;

/**
 * 是否可以移动应用
 * 属性 packageInfo
 * @return
 */
private static boolean isAppMovable(PackageInfo packageInfo) {
if (packageInfo == null) {
  return false;
}
Field field = null;
try {
  field = PackageInfo.class.getField("installLocation");
} catch (Exception e2) {
  try {
      field = ApplicationInfo.class.getField("installLocation");
  } catch (Exception e) {
  }
}
// API小于8则都不能移动，都返回FALSE
if (field == null) {
  return false;
}
int installLocation = 0;
try {
  installLocation = field.getInt(packageInfo);
} catch (Exception e) {
  try {
      installLocation = field.getInt(packageInfo.applicationInfo);
  } catch (Exception e1) {
      return false;
  }
}
if (installLocation == internalOnly) {
  return false;
}
return true;
}

/**
 * 
 * 属性 context
 * 属性 packageName
 * @return
 */
public static boolean isAppMovable(Context context, String packageName) {
PackageManager pm = context.getPackageManager();
PackageInfo packageInfo = null;
try {
  packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
} catch (NameNotFoundException e) {
  e.printStackTrace();
}
return isAppMovable(packageInfo);
}
}
