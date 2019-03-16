package com.blackdog.studentmanager.module.student;

import android.support.annotation.Nullable;

import com.blackdog.studentmanager.R;
import com.blackdog.studentmanager.module.model.Student;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 84412 on 2019/3/16.
 */

public class StudentAdapter extends BaseQuickAdapter<Student,BaseViewHolder> {
    public StudentAdapter(@Nullable List<Student> data) {
        super(R.layout.item_student, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Student item) {
        String student = String.format("%s : %s",item.getNumber(),item.getName());
        helper.setText(R.id.tv_student, student);
        helper.addOnClickListener(R.id.btn_update);
        helper.addOnClickListener(R.id.btn_delete);
    }
}
