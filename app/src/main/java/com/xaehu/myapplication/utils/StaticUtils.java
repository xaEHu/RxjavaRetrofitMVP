package com.xaehu.myapplication.utils;

import android.util.Log;

import com.xaehu.myapplication.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
* @author xaeHu
* 静态模式工具类：不含外部引用对象的公共方法写到这里
*/
public class StaticUtils {

    /**
     * 打印日志
     * @param msg 日志内容
     */
    public static void log(String msg){
        if(BuildConfig.DEBUG){
            Log.i("myout", "---myout---------------------------------------------------");
            Log.i("myout", "|\t\t"+msg);
            Log.i("myout", "-----------------------------------------------------------");
        }
    }

    /**
     * 秒转换分钟
     * @param s 秒
     * @return 分钟字符串
     */
    public static String sToMin(int s){
        return msToMin(s*1000);
    }

    /**
     * 格式化方式让long毫秒转分钟字符串
     * @return xx:xx
     */
    public static String msToMin(long times){
        Date dates = new Date(times);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return sdf.format(dates);
    }
}
