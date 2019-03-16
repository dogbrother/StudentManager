package com.blackdog.studentmanager.module.base;

/**
 * MVP模式中的View
 */

public interface IBaseView<T> {
    void setPresenter(T presenter);                 //设置Presenter
    void showToast(String message);
}
