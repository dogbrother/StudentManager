package com.blackdog.studentmanager.module.user.regedit;

import com.blackdog.studentmanager.module.base.IBasePresenter;
import com.blackdog.studentmanager.module.base.IBaseView;

/**
 * 注册的契约类
 */

public interface IRegeditContact {
    public interface View extends IBaseView<Presenter> {
        void showProgressDialog();                                          //显示正在注册按钮
        void cancelProgressDialog();                                        //关闭正在注册按钮
        void startTimer();                                                  //开启timer
        void stopTimer();                                                   //关闭timer
        void intentToMainActivity();                                        //跳转到主Activity
        void setTimerButtonEnabled();                                       //设置TimerButton可点击
        void setTimerButtonUnEnabled();                                     //设置TimerButton不可点击
    }

    public interface Presenter extends IBasePresenter {
        void doRegedit(String vertify, String phone, String password);
        void getVertifyCode(String phone);
    }
}
