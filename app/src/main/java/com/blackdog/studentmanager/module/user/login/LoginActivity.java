package com.blackdog.studentmanager.module.user.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blackdog.studentmanager.MainActivity;
import com.blackdog.studentmanager.R;
import com.blackdog.studentmanager.module.base.BaseActivity;
import com.blackdog.studentmanager.module.user.forgetpassword.ForgetPasswordActivity;
import com.blackdog.studentmanager.module.user.regedit.RegeditActivity;


/**
 * 登陆界面
 */

public class LoginActivity extends BaseActivity implements ILoginContact.View,View.OnClickListener{

    private LoginPresenter mPresenter;

    private Button mBtnLogin;
    private EditText mEtPhone;
    private EditText mEtPassword;
    private TextView mTvForgetPassword;
    private TextView mTvRegedit;
    private ProgressDialog mDialog;


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("正在登陆...");
        mDialog.setCanceledOnTouchOutside(false);

        mBtnLogin =  findViewById(R.id.btnLogin);
        mEtPhone =  findViewById(R.id.etPhone);
        mEtPassword = findViewById(R.id.etPassword);
        mTvForgetPassword =  findViewById(R.id.tvForgetPassword);
        mTvRegedit = findViewById(R.id.tvRegedit);
    }

    @Override
    protected void initValues() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected void addListeners() {
        mBtnLogin.setOnClickListener(this);
        mTvForgetPassword.setOnClickListener(this);
        mTvRegedit.setOnClickListener(this);
    }

    @Override
    protected String getTitleBarText() {
        return getResources().getString(R.string.login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dimissBack();
    }


    @Override
    public void intentToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showProgressDialog() {
        mDialog.show();
    }

    @Override
    public void cancelProgressDialog() {
        mDialog.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                String phone = mEtPhone.getText().toString();
                String password = mEtPassword.getText().toString();
                mPresenter.doLogin(phone,password);
                break;
            case R.id.tvForgetPassword:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.tvRegedit:
                startActivity(new Intent(LoginActivity.this, RegeditActivity.class));
                break;
        }
    }

    @Override
    public void setPresenter(ILoginContact.Presenter presenter) {

    }
}
