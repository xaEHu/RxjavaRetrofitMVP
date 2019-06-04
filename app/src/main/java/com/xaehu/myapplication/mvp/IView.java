package com.xaehu.myapplication.mvp;

public interface IView<P extends IPresenter> {

    /**
     * @return 布局id
     */
    int getLayoutId() ;

    void initData();

    void initListener();

    P newP();

}
