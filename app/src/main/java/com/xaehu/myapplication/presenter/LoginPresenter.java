package com.xaehu.myapplication.presenter;

import com.xaehu.myapplication.activity.LoginActivity;
import com.xaehu.myapplication.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginActivity> {

    public void login(String name,String pwd){
        if(name.isEmpty() || pwd.isEmpty()){
            getV().showToast("用户名和密码都不能为空");
        }else{
            //// TODO: 2019/6/3  请求

            //// TODO: 2019/6/3 模拟
            if(!name.equals("1")){
                getV().loginSuccess(name,pwd);
            }else{
                getV().loginFail();
            }
        }
    }
}
