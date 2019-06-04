package com.xaehu.myapplication.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xaehu.myapplication.R;
import com.xaehu.myapplication.base.BaseActivity;
import com.xaehu.myapplication.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener {

    private TextView tvMain;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        tvMain = findViewById(R.id.tv_main);
    }

    @Override
    public void initData() {
        String name = getIntent().getStringExtra("name");
        if(name!=null && !"".equals(name)){
            String pass = getIntent().getStringExtra("pwd");
            tvMain.setText(String.format("name:%s\npassword:%s",name,pass));
        }else{
            tvMain.setText("点击去登录");
        }
    }

    private void gotoLogin() {
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void initListener() {
        tvMain.setOnClickListener(this);
    }

    @Override
    public MainPresenter newP() {
        return new MainPresenter();
    }

    @Override
    public void onClick(View v) {
        gotoLogin();
    }

}
