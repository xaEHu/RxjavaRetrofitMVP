package com.xaehu.myapplication.base;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

/**
 * @author : xaeHu
 * @e-mail : hsfemail@qq.com
 * @date : 2019/7/9 10:10
 * @desc : 静态活动：不需要P层的界面基类
 */
public abstract class BaseStaticActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        initListener();
        initSetting();
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();

    protected void initSetting(){
        //左侧添加一个默认的返回图标
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(hasBackBtn());
            //设置返回键可用
            getSupportActionBar().setHomeButtonEnabled(hasBackBtn());
        }
    }

    /**
     * @return 是否有返回按钮
     * 需要有的话重写此方法返回true
     */
    protected boolean hasBackBtn(){
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回
        if(item.getItemId() == android.R.id.home){
            if(hasBackBtn()){
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示吐司提示
     * @param msg 提示内容
     */
    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void showLongToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 弹出框
     */
    public void showAlertDialog(String title, String msg, View view, String btnText, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(title !=null){
            builder.setTitle(title);
        }
        if(msg!=null){
            builder.setMessage(msg);
        }
        if(view!=null){
            builder.setView(view);
        }
        builder.setNegativeButton("关闭",null);
        if(btnText != null && listener != null){
            builder.setPositiveButton(btnText,listener);
        }
        builder.show();
    }
}
