package com.xaehu.myapplication.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xaehu.myapplication.R;
import com.xaehu.myapplication.mvp.IView;
import com.xaehu.myapplication.utils.StaticUtils;

import java.lang.reflect.ParameterizedType;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView {

    private P p;
    private View emptyView;
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
    }

    protected abstract void initView(View view);

    public P getP(){
        if(p == null){
            //实例化P层，类似于p = new P();
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<P> clazz = (Class<P>) parameterizedType.getActualTypeArguments()[0];
            try {
                p = clazz.newInstance();
            }catch (Exception e){
                StaticUtils.loge(e.getMessage());
            }
        }
        if(p != null){
            if(!p.isAttachedV()){
                p.attachView(this);
            }
        }
        return p;
    }

    public void onError(Throwable e){
        showToast(StaticUtils.getErrorMsg(e));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getP()!=null){
            getP().detachView();
        }
        p = null;
    }

    public void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public void showLongToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 设置适配器的空布局
     * @param adapter 适配器
     * @param msg 空布局文字提示
     * @param imgResId 空布局图片资源，若isLoad为true则不生效
     * @param isLoad 是否是加载中
     */
    public void setAdapterEmptyView(BaseQuickAdapter adapter, String msg, int imgResId, boolean isLoad){
        if(emptyView == null){
            emptyView = getLayoutInflater().inflate(R.layout.view_empty, null);
        }
        ((TextView)emptyView.findViewById(R.id.textView_msg)).setText(msg);
        if(isLoad){
            emptyView.findViewById(R.id.imageView_img).setVisibility(View.GONE);
            emptyView.findViewById(R.id.progressBar_loading).setVisibility(View.VISIBLE);
        }else{
            ((ImageView)emptyView.findViewById(R.id.imageView_img)).setImageResource(imgResId);
            emptyView.findViewById(R.id.imageView_img).setVisibility(View.VISIBLE);
            emptyView.findViewById(R.id.progressBar_loading).setVisibility(View.GONE);
        }
        adapter.getData().clear();
        adapter.setEmptyView(emptyView);
        adapter.notifyDataSetChanged();
    }

    /**
     * 显示错误布局
     * @param adapter recyclerView的适配器
     * @param msg 错误提示信息
     */
    public void showErrorView(BaseQuickAdapter adapter,String msg){
        //// TODO: 2019/6/5 替换错误布局图片
        setAdapterEmptyView(adapter,msg, R.mipmap.ic_launcher,false);
    }

    /**
     * 显示空布局
     * @param adapter recyclerView的适配器
     */
    public void showEmptyView(BaseQuickAdapter adapter){
        //// TODO: 2019/6/5 替换空布局图片
        setAdapterEmptyView(adapter,"空空如也",R.mipmap.ic_launcher,false);
    }

    /**
     * 显示加载中布局
     * @param adapter recyclerView的适配器
     */
    public void showLoadView(BaseQuickAdapter adapter){
        setAdapterEmptyView(adapter,"加载中……",0,true);
    }
}
