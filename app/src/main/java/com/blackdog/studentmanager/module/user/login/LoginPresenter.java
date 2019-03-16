package com.blackdog.studentmanager.module.user.login;

import android.text.TextUtils;
import android.util.Log;


import com.blackdog.studentmanager.config.Config;
import com.blackdog.studentmanager.module.user.UserCenterManager;
import com.blackdog.studentmanager.util.ErrorCodeUtils;
import com.blackdog.studentmanager.util.LogUtils;
import com.blackdog.studentmanager.util.SpCenter;
import com.blackdog.studentmanager.util.StringUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 登陆的Presenter
 */

public class LoginPresenter implements ILoginContact.Presenter {

    private ILoginContact.View mView;

    public LoginPresenter(ILoginContact.View view){
        mView = view;
    }

    @Override
    public void start() {

    }

    /**
     * 处理登陆
     * @param phone
     * @param passord
     */
    @Override
    public void doLogin(String phone, String passord) {
        if(TextUtils.isEmpty(phone)){
            mView.showToast("手机号/用户名不能为空");
        }
        else if(TextUtils.isEmpty(passord)){
            mView.showToast("密码不能为空");
        }else{
            mView.showProgressDialog();
            doNetworkLogin(phone,passord);
        }
    }

    /**
     * 处理网络登陆
     * @param phone
     * @param password
     */
    private void doNetworkLogin(String phone, String password){
        BmobUser.loginByAccount(phone, password, new LogInListener<BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                mView.cancelProgressDialog();
               if(e == null){
                   UserCenterManager.getInstance().requestData(null);
                   LogUtils.showILog("登录成功");
                    //登录成功
                   SpCenter.getInsatnce().putBoolean(Config.SP_IS_LOGIN,true);
                   mView.intentToMainActivity();
                }else{
                    mView.showToast(ErrorCodeUtils.parseException(e.getErrorCode()));
                }
            }
        });
    }
}
