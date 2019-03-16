package com.blackdog.studentmanager.module.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.blackdog.studentmanager.R;
import com.blackdog.studentmanager.util.ActivityManagerUtils;

import static android.view.View.GONE;

/**
 * baseActivity
 */

public abstract class BaseActivity extends Activity {

    private ImageButton mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());
        ActivityManagerUtils.addActivity(this);


        //初始化
        initValues();
        initViews();
        addListeners();

        //绑定
        ((TextView)findViewById(R.id.tvTitleBar)).setText(getTitleBarText());
        mBack = (ImageButton) findViewById(R.id.ibBack);

        //监听事件
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtils.removeActivity(this);
    }

    protected abstract int getContentView();

    protected abstract void initViews();

    protected abstract void initValues();

    protected abstract void addListeners();

    protected abstract String getTitleBarText();

    /**
     * show toast
     */
    public void showToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏返回按钮
     */
    protected void dimissBack(){
        mBack.setVisibility(GONE);
    }

    /**
     * 设置返回按钮的监听事件
     */
    protected void setBackClickListeners(View.OnClickListener listener){
        mBack.setOnClickListener(listener);
    }


}
