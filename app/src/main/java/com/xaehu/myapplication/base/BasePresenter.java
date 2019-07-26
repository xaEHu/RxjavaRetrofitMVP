package com.xaehu.myapplication.base;

import com.xaehu.myapplication.mvp.IPresenter;
import com.xaehu.myapplication.mvp.IView;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<V extends IView> implements IPresenter<V> {

    private WeakReference<V> v;

    @Override
    public void attachView(V view) {
        v = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (v.get() != null) {
            v.clear();
        }
        v = null;
    }

    @Override
    public V getV() {
        if(v == null || v.get() == null){
            return null;
        }
        return v.get();
    }

    @Override
    public boolean isAttachedV() {
        return v != null && v.get() != null;
    }

    /**
     * 网络请求的封装
     */
    protected <M> void request(Observable<M> api, final OnRespListener<M> listener){
        api.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<M>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(M value) {
                        if(getV() != null) {
                            listener.onSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(getV() != null){
                            listener.onFailed(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public interface OnRespListener<M>{
        void onSuccess(M value);
        void onFailed(Throwable e);
    }
}
