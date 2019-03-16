package com.blackdog.studentmanager.util;

import android.widget.Toast;

import com.blackdog.studentmanager.App;


/**
 * 处理Toast的工具类
 */

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(String content){
        if(mToast == null){
            mToast = Toast.makeText(App.getInstance(),content, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }
}
