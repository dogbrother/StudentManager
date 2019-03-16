package com.blackdog.studentmanager.module.named;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blackdog.studentmanager.R;
import com.blackdog.studentmanager.module.model.Student;
import com.blackdog.studentmanager.module.user.UserCenterManager;
import com.blackdog.studentmanager.util.ToastUtils;

import java.util.Random;

/**
 * Created by 84412 on 2019/3/16.
 */

public class NameFragment extends Fragment{

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private TextView mNamed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_named,null,false);
        FrameLayout layoutName = root.findViewById(R.id.layout_named);
        mNamed = root.findViewById(R.id.tv_named);
        layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setMessage("随机点名中");
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            if(!UserCenterManager.getInstance().isRequestSucc()) {
                                int i = 3;
                                while (i > 0) {
                                    Thread.sleep(1000);
                                    if(UserCenterManager.getInstance().isRequestSucc()){
                                        break;
                                    }
                                    UserCenterManager.getInstance().requestData(null);
                                    i--;
                                }
                            }
                            if(!UserCenterManager.getInstance().isRequestSucc()){
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToast("学生信息获取有无");
                                    }
                                });
                                return;
                            }
                            Random random = new Random();
                            int position = random.nextInt(UserCenterManager.getInstance().getStudents().size());
                            final Student student = UserCenterManager.getInstance().getStudents().get(position);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast("点名成功");
                                    mNamed.setText(String.format("被点到的同学学号：%s，姓名:%s",student.getNumber(),student.getName()));
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally{
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
        return root;
    }

}
