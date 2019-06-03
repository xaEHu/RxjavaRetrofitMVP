package com.xaehu.myapplication.mvp;

public interface IPresenter<V extends IView> {
    /**
     *  关联P与V（绑定，VIEW销毁适合解绑）
     */
    void attachView(V view);

    /**
     *  取消关联P与V（防止内存泄漏）
     */
    void detachView();

    /**
     *  获取view
     */
    V getV();

    /**
     * 是否已经绑定了view
     */
    boolean isAttachedV();

}
