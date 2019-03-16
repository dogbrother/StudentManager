package com.blackdog.studentmanager.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.blackdog.studentmanager.App;
import com.blackdog.studentmanager.config.Config;

/**
 * Created by 84412 on 2019/3/16.
 */

public class SpCenter {

    private static SpCenter sInsatnce = new SpCenter();

    private  SharedPreferences mSp;

    private SpCenter(){
        mSp = App.getInstance().getSharedPreferences(Config.SP_CONFIG_NAME, Context.MODE_PRIVATE);
    }

    public static SpCenter getInsatnce(){
        return sInsatnce;
    }

    public void putBoolean(String key,boolean value){
        SharedPreferences.Editor editor = mSp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public boolean getBoolean(String key,boolean defaultValue){
        return mSp.getBoolean(key,defaultValue);
    }


}
