package com.lhf.gank.lhfgankclient.utils;

import com.lhf.gank.lhfgankclient.beans.NormalData;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by baoyz on 14/11/24.
 */
public interface NetworkApi {

    @GET("/{mode}/20/{pages}/")
    public Observable<NormalData> getFuLiURL(@Path("mode") String mode, @Path("pages") String pages);

}
