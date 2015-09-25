package com.lhf.gank.lhfgankclient.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.lhf.gank.lhfgankclient.utils.LogUtil;

/**
 * com.lhf.gank.lhfgankclient.app
 * Created by zeratel3000
 * on 2015 09 15/9/15 00 15
 * description
 */
public class GankApp extends Application {
    public int screenWidth;
    public int screenHeigth;

    private static GankApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        LogUtil.getInstance().setContext(this);

        measureInit();

    }


    private void measureInit() {
        // 获取屏幕宽度并设置
        WindowManager wm = (WindowManager) getBaseContext().getSystemService(
                Context.WINDOW_SERVICE);

        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeigth = wm.getDefaultDisplay().getHeight();
    }

    public static GankApp getInstance() {
        return instance;
    }


    /*******************************************/
    /********* volley网络模块 *********/
    /*******************************************/

    /**
     * Log or request TAG
     */
    public static final String TAG = "VolleyPatterns";

    /**
     * Volley查询队列FIFO
     */
    private static RequestQueue mRequestQueue;

//    private static DefaultHttpClient mHttpClient;

    /**
     * @return Volley 查询队列
     */
    public static RequestQueue getRequestQueue() {

        // Cookie存到在mHttpClient中

        if (mRequestQueue == null) {
//            mHttpClient = new DefaultHttpClient();
             mRequestQueue = Volley.newRequestQueue(instance);
//            mRequestQueue = Volley.newRequestQueue(instance, null);
        }

        return mRequestQueue;
    }

    /**
     * 添加Request到Volley队列中，使用自定义标记
     *
     * @param req
     * @param tag
     *            标记request
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * 添加Request到Volley队列中，使用默认标记
     *
     * @param req
     */
    public static <T> void addToRequestQueue(Request<T> req) {

        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * 根据tag停止request
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public RequestQueue getmRequestQueue() {
        return getRequestQueue();
    }

    public void setmRequestQueue(RequestQueue mRequestQueue) {
        GankApp.mRequestQueue = mRequestQueue;
    }

    public static void setsInstance(GankApp sInstance) {
        GankApp.instance = sInstance;
    }


}
