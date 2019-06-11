package com.xaehu.myapplication.activity;

import com.xaehu.myapplication.base.BaseActivity;
import com.xaehu.myapplication.presenter.DetailPresenter;

public class DetailActivity extends BaseActivity<DetailPresenter> {
    @Override
    protected void initView() {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public DetailPresenter newP() {
        return new DetailPresenter();
    }
}
