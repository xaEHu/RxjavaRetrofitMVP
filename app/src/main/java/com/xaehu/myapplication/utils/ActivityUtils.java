package com.xaehu.myapplication.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xaehu.myapplication.App;

/**
 * @author xaeHu
 * 单例模式工具类：含有外部引用对象的公共方法写到这里
 */
public class ActivityUtils {
    private static ActivityUtils utils;
    public static ActivityUtils getInstance(){
        if(null == utils){
            synchronized (ActivityUtils.class){
                if(null == utils){
                    utils = new ActivityUtils();
                }
            }
        }
        return utils;
    }

    /**
     * 适配器绑定纵向列表控件
     * @param adapter 适配器
     * @param recyclerView 列表控件
     */
    public void bindToRecyclerView(BaseQuickAdapter adapter, RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        adapter.bindToRecyclerView(recyclerView);
    }

    /**
     * 适配器绑定列表控件
     * @param adapter 适配器
     * @param recyclerView 列表控件
     * @param isHorizontal 是否横向，否为纵向
     */
    public void bindToRecyclerView(BaseQuickAdapter adapter, RecyclerView recyclerView, boolean isHorizontal){
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext(),
                isHorizontal?LinearLayoutManager.HORIZONTAL:LinearLayoutManager.VERTICAL,false));
        adapter.bindToRecyclerView(recyclerView);
    }

}
