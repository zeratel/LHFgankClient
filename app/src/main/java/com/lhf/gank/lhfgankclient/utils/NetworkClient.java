package com.lhf.gank.lhfgankclient.utils;

import android.text.TextUtils;

import com.lhf.gank.lhfgankclient.app.GankApp;
import com.lhf.gank.lhfgankclient.beans.NormalData;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by baoyz on 14/11/16.
 */
public class NetworkClient {

    private NetworkApi mApi;
    private GankApp mApp = GankApp.getInstance();
    private final OkHttpClient mOkClient;
    private static NetworkClient networkClient = new NetworkClient();

    private NetworkClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getBaseUrl())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
//                        Timber.d(message);
                        LogUtil.d("LHF",message);
                    }
                })
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        addHeaders(request);
                    }
                })
                .build();
        mApi = restAdapter.create(NetworkApi.class);

        mOkClient = new OkHttpClient();
    }



    public static NetworkClient getIntance(){
        return networkClient;
    }

    private String getBaseUrl() {
        return Constants.GankURL;
    }

    private void addHeaders(RequestInterceptor.RequestFacade request) {
        request.addHeader("Keep-Alive", "300");
        request.addHeader("platform", "android");
        request.addHeader("Accept",
                "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("x-requested-with", " XMLHttpRequest");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Content-Encoding", "utf-8");
        request.addHeader("x-phoneType", "android");

        request.addHeader("x-version", InfoShouji.getVersion(GankApp.getInstance()) + "");// 应用版本
        request.addHeader("x-osVersion", InfoShouji.getAndroidVersion() + "");// 手机版本
        request.addHeader("x-phoneModel", InfoShouji.getShoujixinghao() + "");// 手机型号

        request.addHeader("x-osVersion", android.os.Build.VERSION.RELEASE + "");// 系统版本
        request.addHeader("x-phoneModel", android.os.Build.DEVICE + "");// 手机型号

        String imsi = InfoShouji.getImsi(GankApp.getInstance()
                .getApplicationContext()) + "";
        if (TextUtils.isEmpty(imsi)) {
            imsi = "";
        }
        request.addHeader("x-imsi", imsi);// imsi

        String imei = InfoShouji.getImei(GankApp.getInstance()
                .getApplicationContext()) + "";
        request.addHeader("x-imei", imei);// imei

        request.addHeader(
                "x-simNumber",
                InfoShouji.getPhoneNumber(GankApp.getInstance()
                        .getApplicationContext()) + "");

        request.addHeader("x-deviceToken", "");
    }

    public Observable<NormalData> getFuLiURL(String mode, String pages){
        return mApi.getFuLiURL(mode,pages);
    }

}
