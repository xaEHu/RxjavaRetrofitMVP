package com.xaehu.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xaehu.myapplication.R;
import com.xaehu.myapplication.base.BaseActivity;
import com.xaehu.myapplication.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener {

    private EditText etName;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.acticity_login;
    }

    @Override
    public void initView() {
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public LoginPresenter newP() {
        return new LoginPresenter();
    }

    @Override
    public void error() {

    }

    @Override
    public void onClick(View v) {
        getP().login(etName.getText().toString(),etPwd.getText().toString());
    }

    @Override
    protected boolean hasBackBtn() {
        return true;
    }

    public void loginSuccess(String name,String pwd){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("pwd",pwd);
        startActivity(intent);
    }

    public void loginFail(){
        showToast("用户名或密码错误");
    }

}
