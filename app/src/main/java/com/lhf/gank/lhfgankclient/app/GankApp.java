package com.lhf.gank.lhfgankclient.app;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

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

}
