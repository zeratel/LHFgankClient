//package com.lhf.gank.lhfgankclient.utils;
//
//import android.content.ComponentName;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.pm.ProviderInfo;
//import android.content.pm.ResolveInfo;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.text.TextUtils;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//
///**
// * com.lhf.gank.lhfgankclient.utils
// * Created by zeratel3000
// * @author rxread
// * on 2015 12 15/12/3 21 18
// * description
// */
//public class LauncherUtil {
//
//    private static String mBufferedValue=null;
//    /**get the current Launcher's Package Name*/
//    public static String getCurrentLauncherPackageName(Context context) {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
//        if (res==null||res.activityInfo == null) {
//            // should not happen. A home is always installed, isn't it?
//            return "";
//        }
//        if (res.activityInfo.packageName.equals("android")) {
//            return "";
//        } else {
//            return res.activityInfo.packageName;
//        }
//    }
//
//    /**
//     *default permission is "com.android.launcher.permission.READ_SETTINGS"<br/>
//     * {@link #getAuthorityFromPermission(Context, String)}<br/>
//     * @param context
//     */
//    public  static String getAuthorityFromPermissionDefault(Context context) {
//        if(TextUtils.isEmpty(mBufferedValue))//we get value buffered
//            mBufferedValue= getAuthorityFromPermission(context, "com.android.launcher.permission.READ_SETTINGS");
//        return mBufferedValue;
//    }
//
//    /**
//     * be cautious to use this, it will cost about 500ms 此函数为费时函数，大概占用500ms左右的时间<br/>
//     * android系统桌面的基本信息由一个launcher.db的Sqlite数据库管理，里面有三张表<br/>
//     * 其中一张表就是favorites。这个db文件一般放在data/data/com.android.launcher(launcher2)文件的databases下<br/>
//     * 但是对于不同的rom会放在不同的地方<br/>
//     * 例如MIUI放在data/data/com.miui.home/databases下面<br/>
//     * htc放在data/data/com.htc.launcher/databases下面<br/
//     * @param context
//     * @param permission  读取设置的权限  READ_SETTINGS_PERMISSION
//     * @return
//     */
//    public  static String getAuthorityFromPermission(Context context, String permission) {
//        if (TextUtils.isEmpty(permission)) {
//            return "";
//        }
//
//        try {
//            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
//            if (packs == null) {
//                return "";
//            }
//            for (PackageInfo pack : packs) {
//                ProviderInfo[] providers = pack.providers;
//                if (providers != null) {
//                    for (ProviderInfo provider : providers) {
//                        if (permission.equals(provider.readPermission)|| permission.equals(provider.writePermission)) {
//                            return provider.authority;
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static final String ACTION_MY_GAME="com.sogou.appmall.intent.action.FROM_MY_GAME_SHORTCUT";
//    public static final String TAG = "ShortcutUtil";
//    private final static String ONE_KEY_CLEAN_NAME = "一键加速";
//    private final static String MY_GAME="我的游戏";
//
//    /** 一键加速 */
//    private final static int SHORTCUT_TYPE_OKC = 1;
//    /**我的游戏*/
//    private final static int SHORTCUT_TYPE_MY_GAME=4;
//
//
//
////    /**
////     * 为程序创建程序桌面快捷方式<br/>
////     * 之前创建过则不创建<br/>
////     */
////    public static void addApplicationShortcut(Context context) {
////        // 此处分开主要是考虑到替换安装可能就没有后面的快捷方式了
////        if (!PreferencesUtil.hasCreateShortcut()) {
////            if (!isShortCutExist(context, context.getString(R.string.app_name))) {
////                addShortcutByPackageName(context, context.getPackageName());
////                // MLogUtil.i("ShortCut", "app shortcut not exist");
////            } else {
////                // MLogUtil.i("ShortCut", "app shortcut  exists");
////            }
////            PreferencesUtil.setCreateShortCut(true);
////        }
////    }
////
////    /**
////     * 为程序创建一键清理快捷方式<br/>
////     * 之前创建过则不创建<br/>
////     */
////    public static void addOneKeyCleanShortcut(Context context) {
////        if(!ChannelUtil.isShouldGenerateShortCut(context))//部分渠道，不创建快捷方式
////            return;
////        // 一键清理以及
////        if (!PreferencesUtil.hasCreateShortcutOneKeyClean()) {
////            Intent oneKeyClenIntent = getShortCutIntent(context,SHORTCUT_TYPE_OKC);
////            if (!isShortCutExist(context, ONE_KEY_CLEAN_NAME, oneKeyClenIntent)) {
////                addOneKeyCleanShortCut(context, oneKeyClenIntent, false);
////            }
////            PreferencesUtil.setCreateShortCutOneKeyClean(true);
////        }
////    }
////
////
////    /**
////     * 增加桌面一键清理快捷方式
////     *
////     * @param allowRepeat
////     *            是否判断重复 ，如果之前有，则不重复生成
////     */
////    public static void addOneKeyCleanShortCut(Context context,
////                                              boolean allowRepeat) {
////        Intent oneKeyCleanIntent = getShortCutIntent(context, SHORTCUT_TYPE_OKC);
////        if (!allowRepeat) {
////            if (!isShortCutExist(context, ONE_KEY_CLEAN_NAME, oneKeyCleanIntent)) {
////                addOneKeyCleanShortCut(context, oneKeyCleanIntent, false);
////            }
////        } else {
////            addOneKeyCleanShortCut(context, oneKeyCleanIntent, true);
////        }
////    }
////
////
////    /** 增加一键清理快捷方式 */
////    private static void addOneKeyCleanShortCut(Context context, Intent intent,
////                                               boolean allowRepeat) {
////        ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
////                context, R.drawable.shortcut_one_key_clean);
////        addShortcut(context, ONE_KEY_CLEAN_NAME, intent, iconRes, allowRepeat);
////    }
////
////    /**
////     * 为程序创建重磅推荐方式<br/>
////     * 之前创建过则不创建<br/>
////     */
////    public static void addMyGameShortcut(Context context,Bitmap bitmap) {
////        if(!ChannelUtil.isShouldGenerateShortCut(context))//部分渠道，不创建快捷方式
////            return;
////        // 一键清理以及
////        if (!PreferencesUtil.hasCreateShortcutMyGame()) {
////            Intent gameIntent = getShortCutIntent(context,SHORTCUT_TYPE_MY_GAME);
////            addShortcut(context, MY_GAME, gameIntent, bitmap, false);
////            PreferencesUtil.setCreateShortcutMyGame(true);
////        }
////    }
////
////    /**更新我的游戏快捷方式<br/>
////     * 异步,如果没有快捷方式不会创建新的，需要注意<br/>*/
////    public  static void updateMyGameShortcut(final Context context,final Bitmap bitmap){
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                Intent gameIntent=getShortCutIntent(context, SHORTCUT_TYPE_MY_GAME);
////                updateShortcutIcon(context, MY_GAME, gameIntent, bitmap);
////            }
////        }).start();
////    }
////
////    private static Intent getShortCutIntent(Context context, int type) {
////        Intent intent;
////        intent = new Intent(Intent.ACTION_MAIN);//使用MAIN，可以避免部分手机(比如华为、HTC部分机型)删除应用时无法删除快捷方式的问题
////        switch (type) {
////            case SHORTCUT_TYPE_OKC:
////                intent.putExtra(Constants4UI.EXTRA_FROM,
////                        Constants4UI.EXTRA_FROM_SHORTCUT_ONE_KEY_CLEAN);
////                intent.setClassName(context, ActivityOneKeyClean.class.getName());
////                break;
////            case  SHORTCUT_TYPE_MY_GAME:
////                //Note that for an IntentFilter to match an Intent, three conditions must hold: the action and category must match
////			/*需要在相关的activity添加以下声明
////			    <intent-filter>
////	            	<action android:name="com.sogou.appmall.intent.action.FROM_MY_GAME_SHORTCUT" />
////	                <action android:name="android.intent.action.VIEW" />
////	                <action android:name="android.intent.action.MAIN" />
////	    			<category android:name="android.intent.category.DEFAULT" />
////                    <data android:scheme="luna_shortcut" />
////            	</intent-filter>
////			 */
////                intent.addCategory(Intent.CATEGORY_DEFAULT);
////                intent.setAction(ACTION_MY_GAME);
////                intent.setPackage(Constants4UI.MY_PACKAGENAME);
////                intent.putExtra(Constants4UI.EXTRA_FROM, Constants4UI.EXTRA_FROM_SHORTCUT_MY_GAME);
////                intent.setData(Uri.parse("luna_shortcut://game"));
////                break;
////            default:
////                break;
////        }
////        return intent;
////    }
//
//
//
//    /**
//     * @param context
//     *            执行者。
//     * @params pkg 待添加快捷方式的应用包名，其值不可为null。
//     * @return 返回true为正常执行完毕，<br/>
//     *         返回false为pkg值为null或者找不到该应用或者该应用无用于Launch的MainActivity 。
//     * @author sodino
//     * */
//    public static boolean addShortcutByPackageName(Context context, String pkg) {
//        // 快捷方式名
//        String title = "unknown";
//        // MainActivity完整名
//        String mainAct = null;
//        // 应用图标标识
//        int iconIdentifier = 0;
//        // 根据包名寻找MainActivity
//        PackageManager pkgMag = context.getPackageManager();
//        Intent queryIntent = new Intent(Intent.ACTION_MAIN, null);
//        queryIntent.addCategory(Intent.CATEGORY_LAUNCHER);// 重要，添加后可以进入直接已经打开的页面
//        queryIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        queryIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
//
//        List<ResolveInfo> list = pkgMag.queryIntentActivities(queryIntent,
//                PackageManager.GET_ACTIVITIES);
//        for (int i = 0; i < list.size(); i++) {
//            ResolveInfo info = list.get(i);
//            if (info.activityInfo.packageName.equals(pkg)) {
//                title = info.loadLabel(pkgMag).toString();
//                mainAct = info.activityInfo.name;
//                iconIdentifier = info.activityInfo.applicationInfo.icon;
//                break;
//            }
//        }
//        if (mainAct == null) {
//            // 没有启动类
//            return false;
//        }
//        Intent shortcut = new Intent(
//                "com.android.launcher.action.INSTALL_SHORTCUT");
//        // 快捷方式的名称
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
//        // 不允许重复创建
//        shortcut.putExtra("duplicate", false);
//        ComponentName comp = new ComponentName(pkg, mainAct);
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
//                queryIntent.setComponent(comp));
//        // 快捷方式的图标
//        Context pkgContext = null;
//        if (context.getPackageName().equals(pkg)) {
//            pkgContext = context;
//        } else {
//            // 创建第三方应用的上下文环境，为的是能够根据该应用的图标标识符寻找到图标文件。
//            try {
//                pkgContext = context.createPackageContext(pkg,
//                        Context.CONTEXT_IGNORE_SECURITY
//                                | Context.CONTEXT_INCLUDE_CODE);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        if (pkgContext != null) {
//            Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource
//                    .fromContext(pkgContext, iconIdentifier);
//            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
//        }
//        // 发送广播，让接收者创建快捷方式
//        // 需权限<uses-permission
//        // android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
//        context.sendBroadcast(shortcut);
//        return true;
//    }
//
//    /**
//     * 新建快捷方式到桌面<br/>
//     * 请在对应Intent的Activity中添加<br/>
//     * <font color="red"> intent-filter action
//     * android:name="android.intent.action.MAIN"</font> <br/>
//     *
//     * @param shortcutName
//     *            快捷方式名
//     * @param actionIntent
//     *            快捷方式操作
//     * @param icon
//     *            快捷方式图标
//     * @param allowRepeat
//     *            是否允许重复生成
//     *
//     *
//     */
//    public static void addShortcut(Context context, String shortcutName,
//                                   Intent actionIntent, Intent.ShortcutIconResource icon, boolean allowRepeat) {
//        Intent shortcutIntent = new Intent(
//                "com.android.launcher.action.INSTALL_SHORTCUT");
//        shortcutIntent.putExtra("duplicate", allowRepeat);// 是否允许重复创建
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);// 快捷方式的标题
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);// 快捷方式的图标
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);// 快捷方式的动作
//        context.sendBroadcast(shortcutIntent);// 发送广播
//    }
//
//    /**
//     * 新建快捷方式到桌面<br/>
//     * 请在对应Intent的Activity中添加<br/>
//     * <font color="red"> intent-filter action
//     * android:name="android.intent.action.MAIN"</font> <br/>
//     *
//     * @param shortcutName
//     *            快捷方式名
//     * @param actionIntent
//     *            快捷方式操作
//     * @param icon
//     *            快捷方式图标
//     * @param allowRepeat
//     *            是否允许重复生成
//     *
//     *
//     */
//    public static void addShortcut(Context context, String shortcutName,
//                                   Intent actionIntent, Bitmap icon, boolean allowRepeat) {
//        Intent shortcutIntent = new Intent(
//                "com.android.launcher.action.INSTALL_SHORTCUT");
//        shortcutIntent.putExtra("duplicate", allowRepeat);// 是否允许重复创建
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);// 快捷方式的标题
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);// 快捷方式的图标
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);// 快捷方式的动作
//        context.sendBroadcast(shortcutIntent);// 发送广播
//    }
//
//    /**
//     * 删除桌面快捷方式 <br/>
//     *
//     * @param shortcutName
//     *            快捷方式名
//     * @param actionIntent
//     *            快捷方式操作
//     * @param isDuplicate
//     *            为true时循环删除快捷方式（即存在很多相同的快捷方式）
//     *
//     *
//     */
//    public static void deleteShortcut(Context context, String shortcutName,
//                                      Intent actionIntent, boolean isDuplicate) {
//        Intent shortcutIntent = new Intent(
//                "com.android.launcher.action.UNINSTALL_SHORTCUT");
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);// 快捷方式的标题
//        shortcutIntent.putExtra("duplicate", isDuplicate);// 是否循环删除，比如有很多一样的快捷方式
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);// 快捷方式的动作
//        context.sendBroadcast(shortcutIntent);// 发送广播
//    }
//
//    /**
//     * 检查快捷方式是否存在 <br/>
//     * <font color=red>注意：</font> 有些手机无法判断是否已经创建过快捷方式<br/>
//     * 因此，在创建快捷方式时，请添加<br/>
//     * shortcutIntent.putExtra("duplicate", false);// 不允许重复创建<br/>
//     * 最好使用{@link #isShortCutExist(Context, String, Intent)}
//     * 进行判断，因为可能有些应用生成的快捷方式名称是一样的的<br/>
//     * 	此处需要在AndroidManifest.xml中配置相关的桌面权限信息<br/>
//     * 错误信息已捕获<br/>
//     */
//    public static boolean isShortCutExist(Context context, String title) {
//        boolean result = false;
//        try {
//            final ContentResolver cr = context.getContentResolver();
//            StringBuilder uriStr = new StringBuilder();
//            String authority = LauncherUtil.getAuthorityFromPermissionDefault(context);
//            if(authority==null||authority.trim().equals("")){
//                authority = LauncherUtil.getAuthorityFromPermission(context,LauncherUtil.getCurrentLauncherPackageName(context)+".permission.READ_SETTINGS");
//            }
//            uriStr.append("content://");
//            if (TextUtils.isEmpty(authority)) {
//                int sdkInt = android.os.Build.VERSION.SDK_INT;
//                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
//                    uriStr.append("com.android.launcher.settings");
//                } else if (sdkInt < 19) {// Android 4.4以下
//                    uriStr.append("com.android.launcher2.settings");
//                } else {// 4.4以及以上
//                    uriStr.append("com.android.launcher3.settings");
//                }
//            } else {
//                uriStr.append(authority);
//            }
//            uriStr.append("/favorites?notify=true");
//            LogUtil.e("LHF","L:"+uriStr);
//            Uri uri = Uri.parse(uriStr.toString());
//            Cursor c = cr.query(uri, new String[] { "title" },
//                    "title=? ",
//                    new String[] { title }, null);
//            if (c != null && c.getCount() > 0) {
//                result = true;
//            }
//            if (c != null && !c.isClosed()) {
//                c.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            result=false;
//        }
//        return result;
//    }
//
//    /**
//     * 不一定所有的手机都有效，因为国内大部分手机的桌面不是系统原生的<br/>
//     * 更多请参考{@link #isShortCutExist(Context, String)}<br/>
//     * 桌面有两种，系统桌面(ROM自带)与第三方桌面，一般只考虑系统自带<br/>
//     * 第三方桌面如果没有实现系统响应的方法是无法判断的，比如GO桌面<br/>
//     * 	此处需要在AndroidManifest.xml中配置相关的桌面权限信息<br/>
//     * 错误信息已捕获<br/>
//     */
//    public static boolean isShortCutExist(Context context, String title, Intent intent) {
//        boolean result = false;
//        try{
//            final ContentResolver cr = context.getContentResolver();
//            StringBuilder uriStr = new StringBuilder();
//            String authority = LauncherUtil.getAuthorityFromPermissionDefault(context);
//            if(authority==null||authority.trim().equals("")){
//                authority = LauncherUtil.getAuthorityFromPermission(context,LauncherUtil.getCurrentLauncherPackageName(context)+".permission.READ_SETTINGS");
//            }
//            uriStr.append("content://");
//            if (TextUtils.isEmpty(authority)) {
//                int sdkInt = android.os.Build.VERSION.SDK_INT;
//                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
//                    uriStr.append("com.android.launcher.settings");
//                } else if (sdkInt < 19) {// Android 4.4以下
//                    uriStr.append("com.android.launcher2.settings");
//                } else {// 4.4以及以上
//                    uriStr.append("com.android.launcher3.settings");
//                }
//            } else {
//                uriStr.append(authority);
//            }
//            uriStr.append("/favorites?notify=true");
//            Uri uri = Uri.parse(uriStr.toString());
//            Cursor c = cr.query(uri, new String[] { "title", "intent" },
//                    "title=?  and intent=?",
//                    new String[] { title, intent.toUri(0) }, null);
//            if (c != null && c.getCount() > 0) {
//                result = true;
//            }
//            if (c != null && !c.isClosed()) {
//                c.close();
//            }
//        }catch(Exception ex){
//            result=false;
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * 更新桌面快捷方式图标，不一定所有图标都有效<br/>
//     * 如果快捷方式不存在，则不更新<br/>.
//     */
//    public static void updateShortcutIcon(Context context, String title, Intent intent,Bitmap bitmap) {
//        if(bitmap==null){
//            LogUtil.i(TAG, "update shortcut icon,bitmap empty");
//            return;
//        }
//        try{
//            final ContentResolver cr = context.getContentResolver();
//            StringBuilder uriStr = new StringBuilder();
//            String urlTemp="";
//            String authority = LauncherUtil.getAuthorityFromPermissionDefault(context);
//            if(authority==null||authority.trim().equals("")){
//                authority = LauncherUtil.getAuthorityFromPermission(context,LauncherUtil.getCurrentLauncherPackageName(context)+".permission.READ_SETTINGS");
//            }
//            uriStr.append("content://");
//            if (TextUtils.isEmpty(authority)) {
//                int sdkInt = android.os.Build.VERSION.SDK_INT;
//                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
//                    uriStr.append("com.android.launcher.settings");
//                } else if (sdkInt < 19) {// Android 4.4以下
//                    uriStr.append("com.android.launcher2.settings");
//                } else {// 4.4以及以上
//                    uriStr.append("com.android.launcher3.settings");
//                }
//            } else {
//                uriStr.append(authority);
//            }
//            urlTemp=uriStr.toString();
//            uriStr.append("/favorites?notify=true");
//            Uri uri = Uri.parse(uriStr.toString());
//            Cursor c = cr.query(uri, new String[] {"_id", "title", "intent" },
//                    "title=?  and intent=? ",
//                    new String[] { title, intent.toUri(0) }, null);
//            int index=-1;
//            if (c != null && c.getCount() > 0) {
//                c.moveToFirst();
//                index=c.getInt(0);//获得图标索引
//                ContentValues cv=new ContentValues();
//                cv.put("icon", flattenBitmap(bitmap));
//                Uri uri2=Uri.parse(urlTemp+"/favorites/"+index+"?notify=true");
//                int i=context.getContentResolver().update(uri2, cv, null,null);
//                context.getContentResolver().notifyChange(uri,null);//此处不能用uri2，是个坑
//                LogUtil.i(TAG, "update ok: affected "+i+" rows,index is"+index);
//            }else{
//                LogUtil.i(TAG, "update result failed");
//            }
//            if (c != null && !c.isClosed()) {
//                c.close();
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//            LogUtil.i(TAG, "update shortcut icon,get errors:"+ex.getMessage());
//        }
//    }
//
//
//    private static byte[] flattenBitmap(Bitmap bitmap) {
//        // Try go guesstimate how much space the icon will take when serialized
//        // to avoid unnecessary allocations/copies during the write.
//        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
//        ByteArrayOutputStream out = new ByteArrayOutputStream(size);
//        try {
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.flush();
//            out.close();
//            return out.toByteArray();
//        } catch (IOException e) {
//            LogUtil.w(TAG, "Could not write icon");
//            return null;
//        }
//    }
//}
