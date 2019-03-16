package com.blackdog.studentmanager.util;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理字符串的工具类
 */

public class StringUtils {

    /**
     * 判断字符串是否为合法的手机号码
     * @param moblie
     * @return
     */
    public static boolean isMobileNo(String moblie){
        String regExp = "^[1]([3][0-9]{1}|59|58|87|88|89)[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(moblie);
        return m.matches();
    }

    /**
     * 格式化日期
     * @param bmobData
     * @return
     */
    public static String formatTime(String bmobData){
        StringBuilder stringBuilder = new StringBuilder();
        int year = Integer.parseInt(bmobData.substring(0,4));
        int month = Integer.parseInt(bmobData.substring(5,7));
        int day = Integer.parseInt(bmobData.substring(8,10));
        int hour = Integer.parseInt(bmobData.substring(11,13));
        int minute = Integer.parseInt(bmobData.substring(14,16));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        int diffyear = calendar.get(Calendar.YEAR) - year;
        int diffmonth = calendar.get(Calendar.MONTH) - month + 1;
        int diffday = calendar.get(Calendar.DATE) - day;
        int diffhour = calendar.get(Calendar.HOUR)- hour;
        diffhour = (diffhour < 0 ? diffhour + 12 : diffhour);
        int diffminute = calendar.get(Calendar.MINUTE) - minute - 1;
        if(diffyear > 0){
            stringBuilder.append(diffyear + "年前");
        }else if(diffmonth > 0) {
            stringBuilder.append(diffmonth + "月前");
        }else if(diffday > 0){
            stringBuilder.append(diffday + "天前");
        }else if(diffhour > 0){
            stringBuilder.append(diffhour + "小时前");
        }else if(diffminute > 0){
            stringBuilder.append(diffminute + "分钟前");
        }else{
            stringBuilder.append("不到一分钟前");
        }
        return stringBuilder.toString();
    }
}
