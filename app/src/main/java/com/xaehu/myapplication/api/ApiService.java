package com.xaehu.myapplication.api;

import com.xaehu.myapplication.bean.KugouDetail;
import com.xaehu.myapplication.bean.KugouSearch;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {

    /**
     * 酷狗搜索接口
     */
    @GET("http://mobilecdn.kugou.com/api/v3/search/song?format=json")
    Observable<KugouSearch> searchKugou(@QueryMap Map<String,Object> map);

    /**
     * 酷狗音乐详情
     * @param hash 歌曲的hash
     * @return 详情
     */
    @FormUrlEncoded
    @POST("app/i/getSongInfo.php?cmd=playInfo")
    Observable<KugouDetail> getDetail(@Field("hash") String hash);

}
