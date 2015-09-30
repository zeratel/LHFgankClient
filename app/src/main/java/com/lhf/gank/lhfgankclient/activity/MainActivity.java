package com.lhf.gank.lhfgankclient.activity;

import android.graphics.Color;
import android.os.Bundle;
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

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.lhf.gank.lhfgankclient.R;
import com.lhf.gank.lhfgankclient.fragments.HomeFragment;
import com.lhf.gank.lhfgankclient.utils.Constants;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar的设置
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //title的设置
        toolbar.setTitle("");
        toolbar.setTitleTextColor(0xffffffff);

        setSupportActionBar(toolbar);

        //使用NavigationView，使用theme，而不是style和background
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nv_left_drawer = (NavigationView) findViewById(R.id.nv_left_drawer);

        //设置关闭效果包括按钮的动画等
        if (nv_left_drawer != null) {
            setupDrawerContent(nv_left_drawer);
        }

        //viewPage，使用的SmartTabLayout
        initTitleStr();
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


    }

    private void initTitleStr() {

        if (viewpagerTitleStr == null) {

            viewpagerTitleStr = new ArrayList<String>();
            //all
            viewpagerTitleStr.add(Constants.allStr);
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
                adapter.addFragment(new HomeFragment(title), title);
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

//                        LogUtil.i("LHF", "menuItem.getTitle():"+menuItem.getTitle());
                        return true;
                    }
                });

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

}
