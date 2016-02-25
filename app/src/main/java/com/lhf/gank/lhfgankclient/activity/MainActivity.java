package com.lhf.gank.lhfgankclient.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.badoo.mobile.util.WeakHandler;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.lhf.gank.lhfgankclient.R;
import com.lhf.gank.lhfgankclient.fragments.HomeFragment;
import com.lhf.gank.lhfgankclient.utils.Constants;
import com.lhf.gank.lhfgankclient.utils.LogUtil;
import com.lhf.gank.lhfgankclient.utils.PreferencesSaver;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * com.lhf.gank.lhfgankclient.activity
 * Created by zeratel3000
 * on 2015 09 15/9/15 00 15
 * description 主页
 */
public class MainActivity extends BaseActivity {

    private DrawerLayout drawer_layout;
    private NavigationView nv_left_drawer;
    private boolean open = false;

    private MaterialMenuDrawable materialMenuDrawable;
    private ArrayList<String> viewpagerTitleStr;
    private Toolbar toolbar;

    // Action 添加Shortcut
    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar的设置
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //title的设置
        toolbar.setTitle("");
        toolbar.setTitleTextColor(0xffffffff);

        //注意顺序！！！放在之前
        setSupportActionBar(toolbar);

        //使用NavigationView，使用theme，而不是style和background
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nv_left_drawer = (NavigationView) findViewById(R.id.nv_left_drawer);

        //设置关闭效果包括按钮的动画等
        if (nv_left_drawer != null) {
            setupDrawerContent(nv_left_drawer);
        }

        //viewPage，使用的SmartTabLayout
        initTitleStr(Constants.categorical_data);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.tabs);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                toolbar.setTitle(viewpagerTitleStr.get(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        toolbar.setTitle(viewpagerTitleStr.get(0));

        //一开始是关闭的
        drawer_layout.closeDrawer(nv_left_drawer);
        //关闭与打开设置
        drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
                open = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.BURGER);
                open = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //按钮的效果
        materialMenuDrawable = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenuDrawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (open) {
                    //打开的
                    drawer_layout.closeDrawer(nv_left_drawer);
                } else {
                    //关闭的
                    drawer_layout.openDrawer(nv_left_drawer);
                }
            }
        });

        //没有快捷方式则加上快捷方式
        //擦原生的都不好使
        //有时候原生的也靠不住啊。。。
//        if (hasShortcut(this,Constants.app_name)) {
//        if (new LauncherUtil().isShortCutExist(this, Constants.app_name)) {
        if (!PreferencesSaver.getBooleanAttr(this,Constants.short_cut)) {
//            addShortcut(this,);
            creatShortCut(this, Constants.app_name, R.mipmap.ic_launcher);
            PreferencesSaver.setBooleanAttr(this,Constants.short_cut,true);
        }

    }

    private void initTitleStr(String mode) {

        if (viewpagerTitleStr == null) {

            viewpagerTitleStr = new ArrayList<String>();
            if (mode.equals(Constants.categorical_data)) {
                //all
                viewpagerTitleStr.add(Constants.allStr);
            }
            //福利
            viewpagerTitleStr.add(Constants.FuLiStr);
            //Android
            viewpagerTitleStr.add(Constants.AndroidStr);
            //ios
            viewpagerTitleStr.add(Constants.iosStr);
            //休息
            viewpagerTitleStr.add(Constants.restStr);
            //拓展
            viewpagerTitleStr.add(Constants.tuozhanStr);
            //前端
            viewpagerTitleStr.add(Constants.qianduanStr);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer_layout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getSupportFragmentManager());
        if (viewpagerTitleStr != null) {
            for (String title : viewpagerTitleStr) {
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setMode(title);
                adapter.addFragment(homeFragment, title);
            }
        }
        viewPager.setAdapter(adapter);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.BURGER);
                        drawer_layout.closeDrawers();

                        LogUtil.i("LHF", "menuItem.getTitle():" + menuItem.getTitle());
                        switch (menuItem.getTitle().toString()) {
                            case Constants.categorical_data:
                                //分类数据
                                toCategorical();
                                break;
                            case Constants.every_data:
                                //每日数据

                                break;
                            case Constants.random_data:
                                //随机数据
                                toRondom();
                                break;
                            case Constants.about_us:
                                //关于我们
                                new WeakHandler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        readyGo(AboutUs.class);
                                    }
                                }, 300);
                                break;

                            default:
                                break;
                        }


                        return true;
                    }
                });

    }

    //随机数据
    private void toRondom() {
        initTitleStr(Constants.random_data);
    }

    //分类数据
    private void toCategorical() {
        initTitleStr(Constants.categorical_data);
    }

    //viewPage的adapter
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    protected String setTransitionMode() {
        return null;
    }

//    /**
//     * 添加快捷方式
//     *
//     * @param context      context
//     * @param actionIntent 要启动的Intent
//     * @param name         name
//     */
//    public static void addShortcut(Context context, Intent actionIntent, String name,
//                                   boolean allowRepeat, Bitmap iconBitmap) {
//        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
//        // 是否允许重复创建
//        addShortcutIntent.putExtra("duplicate", allowRepeat);
//        // 快捷方式的标题
//        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
//        // 快捷方式的图标
//        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconBitmap);
//        // 快捷方式的动作
//        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
//        context.sendBroadcast(addShortcutIntent);
//    }

    /**
     * 判断是否存在快捷方式
     */
    public boolean hasShortcut(Activity activity, String shortcutName) {
        String url = "";
        boolean result = false;
        try {
            int systemversion = Integer.parseInt(android.os.Build.VERSION.SDK);
/*大于8的时候在com.android.launcher2.settings 里查询（未测试）*/

//        2.2版本以前的URI是：content://com.android.launcher.settings/favorites?notify=true
//    2.2~4.3版本的URI是：content://com.android.launcher2.settings/favorites?notify=true
//    4.4版本以上的目前都是：content://com.android.launcher3.settings/favorites？notify=true
            if (systemversion < 8) {
                url = "content://com.android.launcher.settings/favorites?notify=true";
            } else if (systemversion < 19) {
                url = "content://com.android.launcher2.settings/favorites?notify=true";
            } else {
                url = "content://com.android.launcher3.settings/favorites?notify=true";
//                 content://com.android.launcher3.settings/favorites?notify=true
//            com.android.launcher3.settings
            }
            ContentResolver resolver = activity.getContentResolver();
            Cursor cursor = resolver.query(Uri.parse(url), null, "title=?", new String[]{shortcutName}, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            cursor.close();
//            return true;
//        }
//        return false;
            if (cursor != null && cursor.getCount() > 0) {
                result = true;
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("LHF","Le:"+e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 添加快捷方式
     */
    public void creatShortCut(Activity activity, String shortcutName, int resourceId) {
        Intent intent = new Intent();
        intent.setClass(activity, activity.getClass());
/*以下两句是为了在卸载应用的时候同时删除桌面快捷方式*/
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//不允许重复创建
        shortcutintent.putExtra("duplicate", false);
//需要现实的名称
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
//快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(activity.getApplicationContext(), resourceId);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//点击快捷图片，运行的程序主入口
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//发送广播。OK
        activity.sendBroadcast(shortcutintent);
    }

    /**
     * 删除快捷方式
     */
    public void deleteShortCut(Activity activity, String shortcutName) {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
//快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
//在网上看到到的基本都是一下几句，测试的时候发现并不能删除快捷方式。
//String appClass = activity.getPackageName()+"."+ activity.getLocalClassName();
//ComponentName comp = new ComponentName( activity.getPackageName(), appClass);
//shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
/**改成以下方式能够成功删除，估计是删除和创建需要对应才能找到快捷方式并成功删除**/
        Intent intent = new Intent();
        intent.setClass(activity, activity.getClass());
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        activity.sendBroadcast(shortcut);
    }

//    static String getAuthorityFromPermission(Context context, String permission){
//        if (permission == null) return null;
//        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
//        if (packs != null) {
//            for (PackageInfo pack : packs) {
//                ProviderInfo[] providers = pack.providers;
//                if (providers != null) {
//                    for (ProviderInfo provider : providers) {
//                        if (permission.equals(provider.readPermission)) return provider.authority;
//                        if (permission.equals(provider.writePermission)) return provider.authority;
//                    }
//                }
//            }
//        }
//        return null;
//    }

}
