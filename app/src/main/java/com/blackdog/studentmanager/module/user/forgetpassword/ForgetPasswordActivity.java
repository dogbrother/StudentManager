package com.blackdog.studentmanager.module.user.forgetpassword;

import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blackdog.studentmanager.R;
import com.blackdog.studentmanager.module.base.BaseActivity;


/**
 * 忘记密码的Activity
 */

public class ForgetPasswordActivity extends BaseActivity implements IForgetPasswordContact.View,View.OnClickListener{

    public static final String TAG = "forgetpassword";


    private MyCountTimer mTimer;
    private ForgetPasswordPresenter mPresenter;

    private ProgressDialog mDialog;
    private EditText mEtPhone;
    private EditText mEtPassword;
    private EditText mEtVertify;
    private TextView mTvVertify;
    private Button mBtnReset;

    @Override
    protected int getContentView() {
        return R.layout.activity_regedit_and_forget_password;
    }

    @Override
    protected void initViews() {
        mEtPhone = (EditText) findViewById(R.id.etPhone);
        mEtPassword = (EditText) findViewById(R.id.etPassword);
        mEtPassword.setHint("请输入新密码");
        mEtVertify = (EditText) findViewById(R.id.etVertify);
        mTvVertify = (TextView) findViewById(R.id.tvVertify);
        mBtnReset = (Button) findViewById(R.id.btnRegeditAndLogin);
        mBtnReset.setText("重置");
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("正在重置密码");
        mDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initValues() {
        mPresenter = new ForgetPasswordPresenter(this);
    }

    @Override
    protected void addListeners() {
        mTvVertify.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);
    }

    @Override
    protected String getTitleBarText() {
        return getResources().getString(R.string.find_password);
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
    public void startTimer() {
        mTimer = new MyCountTimer(60000,1000);
        mTimer.start();
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
    public void stopTimer() {
        mTimer.cancel();
    }

    @Override
    public void finishSelf() {
        finish();
    }

    @Override
    public void setPresenter(IForgetPasswordContact.Presenter presenter) {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvVertify:
                mPresenter.getVertifyCode(mEtPhone.getText().toString());
                break;
            case R.id.btnRegeditAndLogin:
                mPresenter.doResetPassword(mEtVertify.getText().toString(),mEtPhone.getText().toString(),mEtPassword.getText().toString());
                break;
        }
    }
}
