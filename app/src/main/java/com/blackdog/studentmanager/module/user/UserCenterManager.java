package com.blackdog.studentmanager.module.user;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.blackdog.studentmanager.App;
import com.blackdog.studentmanager.config.Config;
import com.blackdog.studentmanager.module.model.Student;
import com.blackdog.studentmanager.util.ErrorCodeUtils;
import com.blackdog.studentmanager.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 84412 on 2019/3/16.
 */

public class UserCenterManager {

    private List<Student> mStudents = new ArrayList<>();
    private boolean mIsRequestSucc;
    private volatile boolean mIsRequesting;
    private static UserCenterManager sInstance = new UserCenterManager();
    private UserCenterManager(){

    }

    public List<Student> getStudents(){
        return mStudents;
    }

    public static UserCenterManager getInstance(){
        return sInstance;
    }

    public boolean isRequestSucc(){
        return mIsRequestSucc;
    }

    public interface RequestDataListener{
        void onResut(boolean isSucc);
    }

    public void clear(){
        mStudents.clear();
        mIsRequestSucc = false;
    }

    public void requestData(final RequestDataListener listener){
        if(mIsRequesting){
            return;
        }
        mIsRequesting = true;
        if(!isNetworkAvailable()){
            if(listener != null){
                listener.onResut(false);
            }
            mIsRequesting = false;
            return;
        }
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser());
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                mIsRequesting = false;
                if(e == null){
                    mIsRequestSucc = true;
                    mStudents = list;
                    if(listener != null){
                        listener.onResut(true);
                    }
                }else{
                    if(listener != null){
                        listener.onResut(false);
                    }
                }
            }
        });
    }

    public void addStudent(Student student){
        mStudents.add(student);
    }

    public void deleteStudent(Student student){
        mStudents.remove(student);
    }

    public void updateStudent(Student student){
        for(Student s : mStudents){
            if(s.getObjectId().endsWith(student.getObjectId())){
                s.setNumber(student.getNumber());
                s.setName(student.getName());
            }
        }
    }


    private  boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) App.getInstance()
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }
}
