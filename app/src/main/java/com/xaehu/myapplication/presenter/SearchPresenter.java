package com.xaehu.myapplication.presenter;

import com.xaehu.myapplication.activity.SearchActivity;
import com.xaehu.myapplication.api.Api;
import com.xaehu.myapplication.base.BaseConstant;
import com.xaehu.myapplication.base.BasePresenter;
import com.xaehu.myapplication.bean.KugouSearch;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter extends BasePresenter<SearchActivity> {

    public void search(String name,final int page){
        if(name.isEmpty()){
            getV().showToast("内容不能为空");
            return;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("keyword",name);
        map.put("page",page);
        map.put("pagesize", BaseConstant.PAGE_SIZE);
        Api.getApiService().searchKugou(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KugouSearch>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(KugouSearch kugouSearch) {
                        getV().showData(kugouSearch);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getV().onError(e);
                        if(page == 1){
                            getV().showError(e.getMessage());
                        }else{
                            getV().loadFail();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
