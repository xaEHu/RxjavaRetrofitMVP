package com.xaehu.myapplication.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xaehu.myapplication.R;
import com.xaehu.myapplication.bean.KugouSearch;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<KugouSearch.DataBean.InfoBean, BaseViewHolder> {
    public SearchAdapter(@Nullable List<KugouSearch.DataBean.InfoBean> data) {
        super(R.layout.adapter_search,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KugouSearch.DataBean.InfoBean item) {
        helper.setText(R.id.num,String.valueOf(helper.getLayoutPosition()));
        helper.setText(R.id.filename,item.getFilename());
        helper.setText(R.id.songname,item.getSongname());
        helper.setText(R.id.singername,item.getSingername());
        helper.setText(R.id.duration,String.valueOf(item.getDuration()));
        helper.setText(R.id.price,String.valueOf(item.getPrice()));
        helper.setText(R.id.filesize,String.valueOf(item.getFilesize()));
        helper.setText(R.id.hash,item.getHash());
        helper.setText(R.id.mvhash,item.getMvhash());
        helper.setText(R.id.album,item.getAlbum_name());
    }
}
