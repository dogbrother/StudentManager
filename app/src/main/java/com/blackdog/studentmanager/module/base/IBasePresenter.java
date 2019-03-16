package com.blackdog.studentmanager.module.base;

/**
 * MVP模式中的Presenter
 */

public interface IBasePresenter {
    void start();                       //在View运行时加载数据，可用在onResume()中
}
