package com.blackdog.studentmanager.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理Activity实例的
 */

public class ActivityManagerUtils {
    private static List<Activity> activityList = new ArrayList<Activity>();

    public Activity getTopActivity(){
        return activityList.get(activityList.size()-1);
    }

    public static void addActivity(Activity ac){
        activityList.add(ac);
    }

    public static void removeActivity(Activity activity){
        if(activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

}
