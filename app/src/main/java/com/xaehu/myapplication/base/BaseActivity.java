package com.xaehu.myapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.xaehu.myapplication.mvp.IView;

import java.util.Objects;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView<P> {

    private P p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        initListener();
        initSetting();
    }

    protected void initSetting(){
        //左侧添加一个默认的返回图标
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(hasBackBtn());
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(hasBackBtn());
    }

    /**
     * @return 是否有返回按钮
     */
    protected boolean hasBackBtn(){
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toolbar的事件---返回
        if(item.getItemId() == android.R.id.home){
            if(hasBackBtn()){
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void showLongToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 获取Presenter
     */
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getP()!=null){
            getP().detachView();
        }
        p = null;
    }

}
