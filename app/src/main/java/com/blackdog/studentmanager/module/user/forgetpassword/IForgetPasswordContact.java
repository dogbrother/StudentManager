package com.blackdog.studentmanager.module.user.forgetpassword;


import com.blackdog.studentmanager.module.base.IBasePresenter;
import com.blackdog.studentmanager.module.base.IBaseView;

/**
 * 忘记密码
 */

public interface IForgetPasswordContact {
    public interface View extends IBaseView<Presenter> {
        void setTimerButtonEnabled();                                       //设置TimerButton可点击
        void setTimerButtonUnEnabled();                                     //设置TimerButton不可点击
        void startTimer();                                                  //开启timer
        void showProgressDialog();                                          //显示正在重置密码按钮
        void cancelProgressDialog();                                        //关闭正在重置密码按钮
        void stopTimer();                                                   //关闭timer
        void finishSelf();                                                  //关闭
    }



    public interface Presenter extends IBasePresenter {
        void doResetPassword(String vertify, String phone, String password);
        void getVertifyCode(String phone);
    }
}
