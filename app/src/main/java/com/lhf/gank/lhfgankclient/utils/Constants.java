package com.lhf.gank.lhfgankclient.utils;

/**
 *
 */
public class Constants {
    /**
     * android
     */
    public static final String ANDROID = "android";

    // 默认文件缓存
    public static final String DEFAULT_FILE_DIR = "com.lhf.gank.lhfgankclient";

    //    分类数据: http://gank.avosapps.com/api/data/数据类型/请求个数/第几页
//    数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
//    请求个数： 数字，大于0
//    第几页：数字，大于0
    //Gank地址
    public static final String GankURL = "http://gank.avosapps.com/api/data";

    //福利
    public static final String FuLiURL = GankURL + "/福利";
    //Android
    public static final String AndroidURL = GankURL + "/Android";
    //ios
    public static final String iosURL = GankURL + "/iOS";
    //休息
    public static final String restURL = GankURL + "/休息视频";
    //拓展
    public static final String tuozhanURL = GankURL + "/拓展资源";
    //前端
    public static final String qianduanURL = GankURL + "/前端";
    //all
    public static final String allURL = GankURL + "/all";

    //福利
    public static final String FuLiStr = "福利";
    //Android
    public static final String AndroidStr = "Android";
    //ios
    public static final String iosStr = "iOS";
    //休息
    public static final String restStr = "休息视频";
    //拓展
    public static final String tuozhanStr = "拓展资源";
    //前端
    public static final String qianduanStr = "前端";
    //all
    public static final String allStr = "all";

    public static final String categorical_data = "分类数据";
    public static final String about_us = "干货是什么";
    public static final String random_data = "随机数据";
    public static final String every_data = "每日数据";

    public static final String WEB_URL = "WEB_URL";

    public static final String TITLE = "TITLE";
    public static final String NET_ERROR_RESPONSE = "网络异常，请稍后重试";
}