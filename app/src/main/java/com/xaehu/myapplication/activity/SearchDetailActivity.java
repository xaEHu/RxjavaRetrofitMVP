package com.xaehu.myapplication.activity;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.xaehu.myapplication.R;
import com.xaehu.myapplication.adapter.SearchDetailAdapter;
import com.xaehu.myapplication.base.BaseActivity;
import com.xaehu.myapplication.base.BaseConstant;
import com.xaehu.myapplication.bean.KugouDetail;
import com.xaehu.myapplication.presenter.SearchDetailPresenter;
import com.xaehu.myapplication.utils.ActivityUtils;
import com.xaehu.myapplication.utils.StaticUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xaeHu
 * 搜索详细数据界面
 */
public class SearchDetailActivity extends BaseActivity<SearchDetailPresenter> implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private SearchDetailAdapter adapter;
    private List<KugouDetail> list;
    private String name;
    private int page;
    private boolean isEdit = false;

    @Override
    protected void initView() {
        setTitle("详细搜索");
        recyclerView = findViewById(R.id.rv_list);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
    }

    @Override
    public int getLayoutId() {
        return R.layout.acticity_search;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_edit){
            if(isEdit){
                isEdit = false;
                item.setIcon(android.R.drawable.ic_menu_edit);
                adapter.disableSwipeItem();
            }else{
                isEdit = true;
                item.setIcon(android.R.drawable.ic_menu_save);
                // 开启滑动删除
                adapter.enableSwipeItem();

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        adapter = new SearchDetailAdapter(list);
        ActivityUtils.getInstance().bindToRecyclerView(adapter,recyclerView);
        //打开可上拉加载
        adapter.setEnableLoadMore(true);
        //默认第一次加载会进入loadMore回调，如果不需要可以配置：
        adapter.disableLoadMoreIfNotFullPage();

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        // 开启拖拽
        adapter.enableDragItem(itemTouchHelper,R.id.ll_drag,true);
    }

    /**
     * 拖拽监听
     */
    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos){}
        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {}
        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {}
    };

    /**
     * 滑动删除监听
     */
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
                getP().searchDetail(name,page);
            }
        },recyclerView);
        adapter.setOnItemDragListener(onItemDragListener);
        adapter.setOnItemSwipeListener(onItemSwipeListener);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public SearchDetailPresenter newP() {
        return new SearchDetailPresenter();
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
        getP().searchDetail(name,page);
    }

    /**
     * 请求完成填充数据
     * @param kugouDetails 搜索到的内容
     */
    public void showData(List<KugouDetail> kugouDetails) {
        if(page != 1){
            adapter.loadMoreEnd();
        }
        int size = kugouDetails.size();
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
            adapter.addData(kugouDetails);
            adapter.notifyDataSetChanged();
            }else{
                adapter.loadMoreEnd();
                showEmpty();
            }
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

    /**
     * item点击事件
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        KugouDetail detail = (KugouDetail) adapter.getItem(position);
        String url = detail.getUrl();
        if(url != null && !"".equals(url)){
            StaticUtils.copyText(url);
            showToast("外链已复制");
        }else{
            showToast("该首歌曲没有外链资源");
        }

    }
}
