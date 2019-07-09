package com.xaehu.myapplication.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import com.xaehu.myapplication.App;
import com.xaehu.myapplication.BuildConfig;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.HttpException;

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

    /**
     * 复制到剪贴板
     * @param text
     */
    public static void copyText(String  text){
        ClipboardManager clipboardManager = (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText("MusicUrlClip", text);
        //添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);
    }

    public static String getErrorMsg(Throwable e){
        if (e instanceof UnknownHostException) {
            return ("无网络连接");
        } else if (e instanceof SocketTimeoutException) {
            return ("请求超时");
        } else if (e instanceof ConnectException) {
            return ("连接失败");
        } else if (e instanceof HttpException) {
            return ("请求超时");
        }else {
            return ("请求失败："+e.getMessage());
        }
    }
}
