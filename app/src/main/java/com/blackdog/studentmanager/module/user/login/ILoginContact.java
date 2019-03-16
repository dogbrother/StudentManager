package com.blackdog.studentmanager.module.user.login;


import com.blackdog.studentmanager.module.base.IBasePresenter;
import com.blackdog.studentmanager.module.base.IBaseView;

/**
 * 登陆契约
 */

public interface ILoginContact {

    int TYPE_TEACHER_OR_STUDENT = 1;
    int TYPE_ADMIN = 2;

    public interface View extends IBaseView<Presenter> {
        void intentToMainActivity();                                    //跳转到主Activity
        void showProgressDialog();                                          //显示正在登陆按钮
        void cancelProgressDialog();                                        //关闭正在登陆按钮
    }

    public interface Presenter extends IBasePresenter {
        void doLogin(String phone, String passord);                          //处理登陆
    }

}
