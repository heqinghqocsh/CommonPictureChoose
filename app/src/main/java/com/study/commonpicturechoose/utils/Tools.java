package com.study.commonpicturechoose.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by bossien on 2016/1/13.
 */
public class Tools {
    public static int dip2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, int spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    public static int px2dip(Context context, int pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnectedNet(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 比较两个日期的先后
     * @param dest
     * @param source
     * @param format
     * @return 如果dest代表的时间比source代表的时间早返回true
     */
    public static boolean verifyDate(String dest, String source
            , SimpleDateFormat format) {
        if (dest == null || source == null || format == null) {
            return false;
        }
        Date destDate = null;
        Date sourceDate = null;
        try {
            destDate = format.parse(dest);
            sourceDate = format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        if (destDate.getTime() == sourceDate.getTime()) {
            return true;
        }
        if (destDate.before(sourceDate)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否挂载sd卡
     * @return
     */
    public static boolean hasSDCard(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_UNMOUNTED) &&
                Environment.isExternalStorageRemovable()){
            return false;
        }
        return true;
    }

}
