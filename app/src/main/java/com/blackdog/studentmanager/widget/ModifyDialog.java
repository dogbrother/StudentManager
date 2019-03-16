package com.blackdog.studentmanager.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blackdog.studentmanager.R;


/**
 * Created by 84412 on 2017/12/23.
 */

public class ModifyDialog extends AlertDialog {

    public interface OnButtonClickListener{
         void onOkClick(Dialog dialog, String name, String number);
         void onCancelClick(Dialog dialog);
    }

    private boolean mIsAutoDimiss = true;

    private OnButtonClickListener mClickListener;
    private TextView mTvTitle;
    private EditText mEtName;
    private EditText mEtNumber;
    private Button mBtnOk;
    private Button mBtnCancel;

    private ModifyDialog(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_modify,null,false);
        initView(view);
        setView(view);
    }

    private void initView(View view) {
        mTvTitle = view.findViewById(R.id.tv_title);
        mEtName = view.findViewById(R.id.et_name);
        mEtNumber = view.findViewById(R.id.et_number);
        mBtnOk = view.findViewById(R.id.btn_ok);
        mBtnCancel = view.findViewById(R.id.btn_cancel);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener != null){
                    mClickListener.onOkClick(ModifyDialog.this,mEtName.getText().toString(),mEtNumber.getText().toString());
                }
                if(mIsAutoDimiss){
                    ModifyDialog.this.dismiss();
                }
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onCancelClick(ModifyDialog.this);
                if(mIsAutoDimiss){
                    ModifyDialog.this.dismiss();
                }
            }
        });
    }


    public static class Builder{
        private ModifyDialog mDialog;
        public Builder(Context context){
            mDialog = new ModifyDialog(context);
        }

        public Builder setTitle(String title){
            mDialog.mTvTitle.setText(title);
            return this;
        }

        public Builder setNameHint(String hint){
            mDialog.mEtName.setHint(hint);
            return this;
        }

        public Builder setName(String name){
            mDialog.mEtName.setText(name);
            mDialog.mEtName.setSelection(name.length());
            return this;
        }

        public Builder setNumberHint(String hint){
            mDialog.mEtNumber.setHint(hint);
            return this;
        }

        public Builder setNumber(String number){
            mDialog.mEtNumber.setText(number);
            return this;
        }

        public Builder setButtonClickListener(OnButtonClickListener clickListener){
            mDialog.mClickListener = clickListener;
            return this;
        }

        public Builder setIsAutoDimiss(boolean isAutoDimiss){
            mDialog.mIsAutoDimiss = isAutoDimiss;
            return this;
        }


        public ModifyDialog build(){
            return mDialog;
        }
    }
}
