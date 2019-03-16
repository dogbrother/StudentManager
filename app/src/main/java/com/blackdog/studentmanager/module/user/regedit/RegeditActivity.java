package com.blackdog.studentmanager.module.user.regedit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blackdog.studentmanager.MainActivity;
import com.blackdog.studentmanager.R;
import com.blackdog.studentmanager.module.base.BaseActivity;


/**
 * 注册界面
 */

public class RegeditActivity extends BaseActivity implements IRegeditContact.View,View.OnClickListener{

    public static final String TAG = "regedit";

    private RegeditPresenter mPresenter;
    private MyCountTimer mTimer;

    private EditText mEtPhone;
    private EditText mEtPassword;
    private EditText mEtVertify;
    private TextView mTvVertify;
    private Button mBtnRegeditAndLogin;
    private ProgressDialog mDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_regedit_and_forget_password;
    }

    @Override
    protected void initViews() {
        mEtPhone = (EditText) findViewById(R.id.etPhone);
        mEtPassword = (EditText) findViewById(R.id.etPassword);
        mEtVertify = (EditText) findViewById(R.id.etVertify);
        mTvVertify = (TextView) findViewById(R.id.tvVertify);
        mBtnRegeditAndLogin = (Button) findViewById(R.id.btnRegeditAndLogin);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("注册中...");
        mDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initValues() {
        mPresenter = new RegeditPresenter(this);
    }

    @Override
    protected void addListeners() {
        mTvVertify.setOnClickListener(this);
        mBtnRegeditAndLogin.setOnClickListener(this);
    }

    @Override
    protected String getTitleBarText() {
        return getResources().getString(R.string.regedit);
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
    public void startTimer() {
        mTimer = new MyCountTimer(60000,1000);
        mTimer.start();
    }

    @Override
    public void stopTimer() {
        mTimer.cancel();
    }

    @Override
    public void intentToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void setTimerButtonEnabled() {
        mTvVertify.setEnabled(true);
    }

    @Override
    public void setTimerButtonUnEnabled() {
        mTvVertify.setEnabled(false);
    }

    @Override
    public void setPresenter(IRegeditContact.Presenter presenter) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvVertify:
                if(mTvVertify.isEnabled()){
                    mPresenter.getVertifyCode(mEtPhone.getText().toString());
                }
                break;
            case R.id.btnRegeditAndLogin:
                mPresenter.doRegedit(mEtVertify.getText().toString(),mEtPhone.getText().toString(),mEtPassword.getText().toString());
                break;
        }
    }

    class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTvVertify.setText((millisUntilFinished / 1000) + "秒后重发");
        }

        @Override
        public void onFinish() {
            mTvVertify.setEnabled(true);
            mTvVertify.setText("重发验证码");
        }
    }
}
