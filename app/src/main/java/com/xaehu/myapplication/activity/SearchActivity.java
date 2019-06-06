package com.xaehu.myapplication.activity;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
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
        setTitle("搜索");
        recyclerView = findViewById(R.id.rv_list);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
    }

    @Override
    public int getLayoutId() {
        return R.layout.acticity_search;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        return true;
//    }

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
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //The default animation for each item only once, if you want to repeat the animation method can be called at
        adapter.isFirstOnly(false);
        //前4条不显示动画
        adapter.setNotDoAnimationCount(4);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // 开启拖拽
        adapter.enableDragItem(itemTouchHelper, R.id.filename, true);
        adapter.setOnItemDragListener(onItemDragListener);

        // 开启滑动删除
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(onItemSwipeListener);

    }

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos){}
        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {}
        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {}
    };

    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {}
        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {}
        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {}
        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };

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
    protected boolean hasBackBtn() {
        return true;
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
            if(page == 1){
                int count = kugouSearch.getData().getTotal();
                adapter.setHeaderView(getHeaderView(count));
            }else{
                adapter.loadMoreEnd();
            }
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
