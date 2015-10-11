package com.lhf.gank.lhfgankclient.utils;

import java.net.URLEncoder;

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

    //福利 "/福利"

    public static String FuLiURL = GankURL + "/" + encode("福利");
    //Android
    public static final String AndroidURL = GankURL + "/Android";
    //ios
    public static final String iosURL = GankURL + "/iOS";
    //休息
    public static final String restURL = GankURL + "/" + encode("休息视频");
    //拓展
    public static final String tuozhanURL = GankURL + "/" + encode("拓展资源");
    //前端
    public static final String qianduanURL = GankURL + "/" + encode("前端");
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
    public static final String IS_ALL_LOAD = "没有更多内容啦~~~";
    public static final String API_ERROR_RESPONSE = "API异常，请稍后重试";
    private static String a = "";
    public static final String about_us_title_url = "http://gank.io/";
    public static final String about_us_mailbox_url = "http://gank.io/subscribe";
    public static final String about_us_tools_url = "http://gank.io/tools";
    public static final String about_us_code_url = "http://gank.io/download";

    public static String encode(String temp) {
        try {
            temp = URLEncoder.encode(temp, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return temp;
        }
    }

}
