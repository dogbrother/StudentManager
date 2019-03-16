package com.blackdog.studentmanager.util;

import android.util.Log;

/**
 * 关于Log的工具类
 */

public class LogUtils {

    //默认的TAG
    public static final String TAG = "studengManager";

    public static void showILog(String message){
        Log.i(TAG,message);
    }

    public static void showILog(String tag, String message){
        Log.i(tag,message);
    }

    public static void showELog(String message){
        Log.e(TAG,message);
    }

    public static void showELog(String tag, String message){
        Log.e(tag,message);
    }
}
