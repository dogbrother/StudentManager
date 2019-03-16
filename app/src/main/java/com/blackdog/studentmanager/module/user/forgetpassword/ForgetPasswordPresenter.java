package com.blackdog.studentmanager.module.user.forgetpassword;

import android.text.TextUtils;


import com.blackdog.studentmanager.config.Config;
import com.blackdog.studentmanager.util.ErrorCodeUtils;
import com.blackdog.studentmanager.util.StringUtils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 *
 */

public class ForgetPasswordPresenter implements IForgetPasswordContact.Presenter {

    private IForgetPasswordContact.View mView;
    private int mVertifyCode = 0;

    public ForgetPasswordPresenter(IForgetPasswordContact.View view){
        this.mView = view;
    }

    @Override
    public void doResetPassword(String vertify, String phone, String password) {
        if(TextUtils.isEmpty(phone)){
            mView.showToast("手机号码不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mView.showToast("密码不能为空");
            return;
        }
        if(!StringUtils.isMobileNo(phone)){
            mView.showToast("手机格式不正确");
            return;
        }
        if(mVertifyCode == 0){
            mView.showToast("请获取验证码");
            return;
        }
        mView.showProgressDialog();
        BmobUser.resetPasswordBySMSCode(vertify, password, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                mView.cancelProgressDialog();
                mView.stopTimer();
                if(e == null){
                    mView.showToast("重置密码成功");
                    mView.finishSelf();
                }else{
                    mView.showToast(ErrorCodeUtils.parseException(e.getErrorCode()));
                }
            }
        });
    }

    @Override
    public void getVertifyCode(String phone) {
        mView.setTimerButtonUnEnabled();
        if(TextUtils.isEmpty(phone)){
            mView.showToast("手机号码不能为空");
            mView.setTimerButtonEnabled();
            return;
        }
        if(!StringUtils.isMobileNo(phone)){
            mView.showToast("手机格式不正确");
            mView.setTimerButtonEnabled();
            return;
        }
        mView.startTimer();
        BmobSMS.requestSMSCode(phone, Config.SMS_TEMPLATE_NAME, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e == null){

                    mVertifyCode = integer;
                }else{
                    mView.showToast(ErrorCodeUtils.parseException(e.getErrorCode()));

                }
            }
        });
    }

    @Override
    public void start() {
    }
}
