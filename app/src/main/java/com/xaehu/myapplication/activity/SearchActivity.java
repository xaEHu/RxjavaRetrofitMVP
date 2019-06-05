package com.xaehu.myapplication.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xaehu.myapplication.R;
import com.xaehu.myapplication.adapter.SearchAdapter;
import com.xaehu.myapplication.base.BaseActivity;
import com.xaehu.myapplication.base.BaseConstant;
import com.xaehu.myapplication.bean.KugouSearch;
import com.xaehu.myapplication.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<SearchPresenter> implements View.OnClickListener {

    private final String TAG = "SearchActivity";
    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<KugouSearch.DataBean.InfoBean> list;
    private String name;
    private int page;

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.rv_list);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
    }

    @Override
    public int getLayoutId() {
        return R.layout.acticity_search;
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        adapter = new SearchAdapter(list);
        //这个代码不写列表不会显示
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
//        recyclerView.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerView);
        //打开可上拉加载
        adapter.setEnableLoadMore(true);
        //默认第一次加载会进入loadMore回调，如果不需要可以配置：
        adapter.disableLoadMoreIfNotFullPage();
        //滑动动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //The default animation for each item only once, if you want to repeat the animation method can be called at
        adapter.isFirstOnly(false);

    }

    @Override
    public void initListener() {
        button.setOnClickListener(this);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getP().search(name,page);
            }
        },recyclerView);
    }

    @Override
    public SearchPresenter newP() {
        return new SearchPresenter();
    }

    @Override
    public void onClick(View v) {
        page = 1;
        name = editText.getText().toString();
        showLoading();
        getP().search(name,page);
    }

    /**
     * 请求完成填充数据
     * @param kugouSearch 搜索到的内容
     */
    public void showData(KugouSearch kugouSearch) {
        if(kugouSearch.getErrcode() == 0){
            int count = kugouSearch.getData().getTotal();
            Log.i(TAG, "showData: count:"+count);
            adapter.setHeaderView(getHeaderView(count));
            if(kugouSearch.getData().getInfo()!=null){
                int size = kugouSearch.getData().getInfo().size();
                if(size != 0){
                    if(page == 1){
                        list.clear();
                        page++;
                    }else{
                        if(size< BaseConstant.PAGE_SIZE){
                            adapter.loadMoreEnd();
                        }else {
                            adapter.loadMoreComplete();
                            page++;
                        }
                    }
                    adapter.addData(kugouSearch.getData().getInfo());
                    adapter.notifyDataSetChanged();
                }else{
                    adapter.loadMoreEnd();
                    showEmpty();
                }
            }else{
                if(page == 1){
                    showEmpty();
                }else{
                    adapter.loadMoreEnd();
                }
            }
        }else{
            if(page != 1){
                adapter.loadMoreFail();
            }
            showToast("错误码："+kugouSearch.getErrcode());
        }
    }

    @SuppressLint("DefaultLocale")
    private View getHeaderView(int count) {
        TextView header = (TextView) getLayoutInflater().inflate(android.R.layout.simple_dropdown_item_1line,null);
        header.setText(String.format("搜索到歌曲总数量：%d",count));
        return header;
    }

    /**
     * 显示空布局（主要针对首次搜索的时候）
     */
    public void showEmpty(){
        showEmptyView(adapter);
    }
    /**
     * 显示加载布局（主要针对首次搜索的时候）
     */
    public void showLoading(){
        showLoadView(adapter);
    }

    public void loadFail() {
        adapter.loadMoreFail();
    }

    public void showError(String msg) {
        showErrorView(adapter,msg);
    }
}
