package com.blackdog.studentmanager.module.student;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackdog.studentmanager.R;
import com.blackdog.studentmanager.config.Config;
import com.blackdog.studentmanager.module.model.Student;
import com.blackdog.studentmanager.util.ToastUtils;
import com.blackdog.studentmanager.widget.ModifyDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 84412 on 2019/3/16.
 */

public class StudentFragment extends Fragment implements StudentContract.IView{
    private View mRootView;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRv;
    private FloatingActionButton mAddButton;

    private StudentContract.IPresenter mPresenter;
    private List<Student> mDatas;
    private StudentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_student,null,false);
        mRefreshLayout = mRootView.findViewById(R.id.refreshStudent);
        mRv = mRootView.findViewById(R.id.rvStudent);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAddButton = mRootView.findViewById(R.id.btnAdd);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initValues();
        addListeners();
        mRv.setAdapter(mAdapter);
        mPresenter.requesNewData();
    }

    private void addListeners() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final Student student = (Student) adapter.getData().get(position);
                switch (view.getId()){
                    case R.id.btn_update:
                        new ModifyDialog.Builder(getActivity())
                                .setTitle("添加学生信息")
                                .setNameHint("请输入姓名")
                                .setName(student.getName())
                                .setNumberHint("请输入学号")
                                .setNumber(student.getNumber())
                                .setButtonClickListener(new ModifyDialog.OnButtonClickListener() {
                                    @Override
                                    public void onOkClick(Dialog dialog, String name, String number) {
                                        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(number)){
                                            ToastUtils.showToast("姓名或学号不能为空");
                                            return;
                                        }
                                        student.setName(name);
                                        student.setNumber(number);
                                        mPresenter.updateStudent(student);
                                    }

                                    @Override
                                    public void onCancelClick(Dialog dialog) {

                                    }
                                })
                                .build()
                                .show();
                        break;
                    case R.id.btn_delete:
                       new AlertDialog.Builder(getContext())
                                .setMessage("是否删除" + student.getName())
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.deleteStudent(student);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                        break;
                }
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.requestMoreData();
            }
        },mRv);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requesNewData();
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ModifyDialog.Builder(getActivity())
                        .setTitle("添加学生信息")
                        .setNameHint("请输入姓名")
                        .setNumberHint("请输入学号")
                        .setButtonClickListener(new ModifyDialog.OnButtonClickListener() {
                            @Override
                            public void onOkClick(Dialog dialog, String name, String number) {
                                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(number)){
                                    ToastUtils.showToast("姓名或学号不能为空");
                                    return;
                                }
                                Student student = new Student();
                                student.setName(name);
                                student.setNumber(number);
                                mPresenter.addStudent(student);
                            }

                            @Override
                            public void onCancelClick(Dialog dialog) {

                            }
                        })
                        .build()
                        .show();
            }
        });
    }

    private void initValues() {
        mPresenter = new StudentPresenter(this);
        mDatas = new ArrayList<>();
        mAdapter = new StudentAdapter(mDatas);
    }


    @Override
    public void addNewData(List<Student> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        mAdapter.setNewData(mDatas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addMoreData(List<Student> datas) {
        mDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showRefreshing(boolean isShow) {
        mRefreshLayout.setRefreshing(isShow);
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return mAdapter;
    }
}
