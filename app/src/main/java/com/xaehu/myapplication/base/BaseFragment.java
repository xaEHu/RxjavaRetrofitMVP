package com.xaehu.myapplication.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xaehu.myapplication.mvp.IView;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView<P> {

    private P p;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        initListener();
        initSetting();
    }

    protected abstract void initView(View view);

    protected void initSetting(){

    }

    public P getP(){
        if(p == null){
            p = newP();
        }
        if(p != null){
            if(!p.isAttachedV()){
                p.attachView(this);
            }
        }
        return p;
    }

    public void error(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getP()!=null){
            getP().detachView();
        }
        p = null;
    }
}
