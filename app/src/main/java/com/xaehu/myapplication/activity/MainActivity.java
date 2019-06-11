package com.xaehu.myapplication.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xaehu.myapplication.R;
import com.xaehu.myapplication.base.BaseActivity;
import com.xaehu.myapplication.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener {

    private TextView tvMain;
    private TextView tvDetail;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        tvMain = findViewById(R.id.tv_main);
        tvDetail = findViewById(R.id.tv_detail);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvMain.setOnClickListener(this);
        tvDetail.setOnClickListener(this);
    }

    @Override
    public MainPresenter newP() {
        return new MainPresenter();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v == tvMain){
            intent.setClass(this,SearchActivity.class);
        }else if(v == tvDetail){
            intent.setClass(this,SearchDetailActivity.class);
        }
        startActivity(intent);
    }
}
