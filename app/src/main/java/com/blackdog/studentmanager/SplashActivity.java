package com.blackdog.studentmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;


import com.blackdog.studentmanager.App;
import com.blackdog.studentmanager.MainActivity;
import com.blackdog.studentmanager.R;
import com.blackdog.studentmanager.config.Config;
import com.blackdog.studentmanager.module.user.login.LoginActivity;
import com.blackdog.studentmanager.util.SpCenter;

import java.lang.ref.WeakReference;

/**
 * Created by 84412 on 2017/12/3.
 */


public class SplashActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //做了优化过的代码
        final MyHandler handler = new MyHandler(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断是否已经登陆过了
                boolean isLogined = SpCenter.getInsatnce().getBoolean(Config.SP_IS_LOGIN,false);
                if (!isLogined) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                handler.handleMessage(null);
            }
        },3000);
    }



    private class MyHandler extends Handler {
        private WeakReference<SplashActivity> mActivity;
        public MyHandler(SplashActivity activity){
            mActivity = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity activity = mActivity.get();
            if(activity != null){
                activity.finish();
            }
        }
    }
}
