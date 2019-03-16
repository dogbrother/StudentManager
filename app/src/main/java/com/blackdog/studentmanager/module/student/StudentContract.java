package com.blackdog.studentmanager.module.student;

import android.app.Activity;

import com.blackdog.studentmanager.module.model.Student;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by 84412 on 2019/3/16.
 */

public interface StudentContract {
    interface IView{
        void addNewData(List<Student> datas);
        void addMoreData(List<Student> datas);
        void showRefreshing(boolean isShow);
        BaseQuickAdapter getAdapter();
        Activity getActivity();
    }

    interface IPresenter{
        void requesNewData();
        void requestMoreData();
        void addStudent(Student student);
        void deleteStudent(Student student);
        void updateStudent(Student student);
    }
}
