package com.blackdog.studentmanager.util;

/**
 * Bmob错误码工具类
 */

public class ErrorCodeUtils {
    public static String parseException(int errorcode){
        LogUtils.showELog("errorcode : " + errorcode);
        switch (errorcode){
            case 101:
                return "账号存在或密码错误";
            case 9016:
                return "无网络链接，请检查网络";
            default:
                return "未知错误";
        }
    }
}
