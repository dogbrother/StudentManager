package com.blackdog.studentmanager;

import android.app.Application;
import android.content.SharedPreferences;

import com.blackdog.studentmanager.config.Config;
import com.blackdog.studentmanager.module.user.UserCenterManager;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by 84412 on 2019/3/16.
 */

public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initBmob();
        UserCenterManager.getInstance().requestData(null);
    }

    public static App getInstance(){
        return sInstance;
    }

    private void initBmob(){
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
                ////设置appkey
                .setApplicationId(Config.BMOB_APP_ID)
                ////请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(20)
                ////文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(218*1024)
                ////文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }
}
