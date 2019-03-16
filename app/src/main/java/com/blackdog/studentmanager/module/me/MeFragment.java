package com.blackdog.studentmanager.module.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackdog.studentmanager.R;
import com.blackdog.studentmanager.config.Config;
import com.blackdog.studentmanager.module.user.UserCenterManager;
import com.blackdog.studentmanager.module.user.login.LoginActivity;
import com.blackdog.studentmanager.util.SpCenter;

import cn.bmob.v3.BmobUser;

/**
 * Created by 84412 on 2019/3/17.
 */

public class MeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_me,null,false);
        root.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setMessage("是否退出登录" )
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SpCenter.getInsatnce().putBoolean(Config.SP_IS_LOGIN,false);
                                BmobUser.logOut();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                getActivity().startActivity(intent);
                                getActivity().finish();
                                UserCenterManager.getInstance().clear();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        return root;
    }
}
