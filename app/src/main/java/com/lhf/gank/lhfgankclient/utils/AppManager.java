package com.lhf.gank.lhfgankclient.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.squareup.picasso.Picasso;

import java.util.Stack;

/**
 * com.lhf.gank.lhfgankclient.utils
 * Created by zeratel3000
 * on 2015 09 15/9/17 10 00
 * description 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    };

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
            activityStack = new Stack<Activity>();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            // activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity(堆栈中最后一个压入的)
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 获取当前Activity(堆栈中最后一个压入的)
     *
     * @return
     */
    public Activity beforeCurrentActivity() {
        Activity activity = activityStack.get(activityStack.size()-2);
        return activity;
    }

    /**
     * 结束当前activity(堆栈中最后一个压入的)
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);

    }

    public void removeActivity(Activity activity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定的activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有的activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(0)) {
                activityStack.get(0).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     *
     * @param context
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
//            ShareTools.removeAccount(context);
//            SingleThreadPool.getIntance().shutDownThreadPool();
            Picasso.with(context).shutdown();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }

    /**
     * @function 通过名字查找
     */
    public Activity findActivity(String classname){
        if(classname == null)
            return null;

        for(int index = 0; index < activityStack.size(); index++){
            Activity ac = activityStack.get(index);
            if(ac != null){
                if(ac.getComponentName().getClassName().equals(classname)){
                    return ac;
                }
            }
        }
        return null;
    }

}
