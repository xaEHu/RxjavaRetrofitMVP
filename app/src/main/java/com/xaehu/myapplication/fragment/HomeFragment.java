package com.xaehu.myapplication.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.xaehu.myapplication.R;
import com.xaehu.myapplication.activity.SearchActivity;
import com.xaehu.myapplication.activity.SearchDetailActivity;
import com.xaehu.myapplication.base.BaseFragment;
import com.xaehu.myapplication.presenter.HomeP;

import java.util.Objects;

/**
 * @author : xaeHu
 * @e-mail : hsfemail@qq.com
 * @date : 2019/7/9 10:27
 * @desc : 首页Fragment
 */
public class HomeFragment extends BaseFragment<HomeP> implements View.OnClickListener {
    private TextView tvMain;
    private TextView tvDetail;
    @Override
    protected void initView(View view) {
        tvMain = view.findViewById(R.id.tv_main);
        tvDetail = view.findViewById(R.id.tv_detail);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
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
    public HomeP newP() {
        return new HomeP();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v == tvMain){
            intent.setClass(Objects.requireNonNull(getActivity()), SearchActivity.class);
        }else if(v == tvDetail){
            intent.setClass(Objects.requireNonNull(getActivity()), SearchDetailActivity.class);
        }
        startActivity(intent);
    }
}
