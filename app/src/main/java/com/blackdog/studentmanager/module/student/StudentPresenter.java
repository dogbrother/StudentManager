package com.blackdog.studentmanager.module.student;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.view.menu.MenuView;
import android.telecom.ConnectionService;

import com.blackdog.studentmanager.config.Config;
import com.blackdog.studentmanager.module.model.Student;
import com.blackdog.studentmanager.module.user.UserCenterManager;
import com.blackdog.studentmanager.util.ErrorCodeUtils;
import com.blackdog.studentmanager.util.ToastUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 84412 on 2019/3/16.
 */

public class StudentPresenter implements StudentContract.IPresenter {

    private StudentContract.IView mView;
    private int mCurrentOffset;

    public StudentPresenter(StudentContract.IView view){
        this.mView = view;
        if(!UserCenterManager.getInstance().isRequestSucc()){
            UserCenterManager.getInstance().requestData(null);
        }
    }

    @Override
    public void requesNewData() {
        if(!isNetworkAvailable()){
            ToastUtils.showToast("网络链接不可用");
            mView.showRefreshing(false);
            return;
        }
        mView.getAdapter().setPreLoadNumber(Config.REQUEST_COUNT);
        mView.showRefreshing(true);
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser());
        query.setLimit(Config.REQUEST_COUNT);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                mCurrentOffset = list.size();
                mView.showRefreshing(false);
                if(e == null){
                    if( list.size() < Config.REQUEST_COUNT){
                        mView.getAdapter().setEnableLoadMore(false);
                    }else{
                        mView.getAdapter().setEnableLoadMore(true);
                    }
                    mView.addNewData(list);
                }else{
                    ToastUtils.showToast("获取失败：" + ErrorCodeUtils.parseException(e.getErrorCode()));
                }
            }
        });
    }

    private  boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) mView.getActivity()
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

    @Override
    public void requestMoreData() {
        if(!isNetworkAvailable()){
            ToastUtils.showToast("网络链接不可用");
            return;
        }
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser());
        query.setLimit(Config.REQUEST_COUNT);
        query.setSkip(mCurrentOffset);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                if(e == null){
                    mCurrentOffset += list.size();
                    if( list.size() < Config.REQUEST_COUNT){
                        mView.getAdapter().loadMoreEnd();
                    }else{
                        mView.getAdapter().loadMoreComplete();
                    }
                    mView.addMoreData(list);
                }else{
                    mView.getAdapter().loadMoreFail();
                    ToastUtils.showToast("获取失败：" + ErrorCodeUtils.parseException(e.getErrorCode()));
                }
            }
        });
    }

    @Override
    public void addStudent(final Student student) {
        if(!isNetworkAvailable()){
            ToastUtils.showToast("网络链接不可用");
            return;
        }
        student.setUser(BmobUser.getCurrentUser());
        final ProgressDialog progressDialog = new ProgressDialog(mView.getActivity());
        progressDialog.setMessage("添加中...");
        progressDialog.show();
        student.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                UserCenterManager.getInstance().addStudent(student);
                progressDialog.dismiss();
                if(e == null){
                    student.setObjectId(s);
                    mView.getAdapter().addData(student);
                    mView.getAdapter().notifyDataSetChanged();
                }else{
                    ToastUtils.showToast(ErrorCodeUtils.parseException(e.getErrorCode()));
                }
            }
        });
    }

    @Override
    public void deleteStudent(final Student student) {
        if(!isNetworkAvailable()){
            ToastUtils.showToast("网络链接不可用");
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(mView.getActivity());
        progressDialog.setMessage("删除中...");
        progressDialog.show();
        student.delete(student.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                progressDialog.dismiss();
                if(e == null){
                    UserCenterManager.getInstance().deleteStudent(student);
                    mView.getAdapter().getData().remove(student);
                    mView.getAdapter().notifyDataSetChanged();
                }else{
                    ToastUtils.showToast(ErrorCodeUtils.parseException(e.getErrorCode()));
                }
            }
        });
    }

    @Override
    public void updateStudent(final Student student) {
        if(!isNetworkAvailable()){
            ToastUtils.showToast("网络链接不可用");
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(mView.getActivity());
        progressDialog.setMessage("更新中...");
        progressDialog.show();
        student.update(student.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                progressDialog.dismiss();
                if(e == null){
                    UserCenterManager.getInstance().updateStudent(student);
                    mView.getAdapter().notifyDataSetChanged();
                }else{
                    ToastUtils.showToast(ErrorCodeUtils.parseException(e.getErrorCode()));
                }
            }
        });
    }
}
