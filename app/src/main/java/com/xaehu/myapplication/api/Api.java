package com.xaehu.myapplication.api;

import com.xaehu.myapplication.base.BaseApi;

public class Api {
    private static ApiService apiService;

    public static ApiService getApiService(){
        if(apiService == null){
            synchronized (Api.class){
                if(null == apiService){
                    apiService = BaseApi.getInstance().getRetrofit().create(ApiService.class);
                }
            }
        }
        return apiService;
    }
}
