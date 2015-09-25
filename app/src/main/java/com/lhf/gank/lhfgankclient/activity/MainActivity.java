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
import com.lhf.gank.lhfgankclient.fragments.CheeseListFragment;
import com.lhf.gank.lhfgankclient.utils.LogUtil;
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

    private Toolbar toolbar;
    private MaterialMenuDrawable materialMenuDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        nv_left_drawer = (NavigationView)findViewById(R.id.nv_left_drawer);

        if (nv_left_drawer != null) {
            setupDrawerContent(nv_left_drawer);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.tabs);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        viewPagerTab.setViewPager(viewPager);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("haha1");
        toolbar.setTitleTextColor(0xffffffff);


        drawer_layout.closeDrawer(nv_left_drawer);

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
//        Adapter adapter = new Adapter(getSupportFragmentManager());
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(), "Category 1");
        adapter.addFragment(new CheeseListFragment(), "Category 2");
        adapter.addFragment(new CheeseListFragment(), "Category 3");
        adapter.addFragment(new CheeseListFragment(), "Category 1");
        adapter.addFragment(new CheeseListFragment(), "Category 2");
        adapter.addFragment(new CheeseListFragment(), "Category 3");
        adapter.addFragment(new CheeseListFragment(), "Category 1");
        adapter.addFragment(new CheeseListFragment(), "Category 2");
        adapter.addFragment(new CheeseListFragment(), "Category 3");
        adapter.addFragment(new CheeseListFragment(), "Category 1");
        adapter.addFragment(new CheeseListFragment(), "Category 2");
        adapter.addFragment(new CheeseListFragment(), "Category 3");
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


                        LogUtil.i("LHF", "menuItem.getTitle():"+menuItem.getTitle());
                        return true;
                    }
                });

    }

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

//    // 获取数据
//    private void getData() {
//
//        String url = Constants.allURL+"/1/1";
//
//        // "http://192.168.31.219:8080/ronyun/index.jsp?type=getToke&id=";
//        // /*建立HTTP Get对象*/
//        NetworkUtil networkUtil = new NetworkUtil(this);
//        networkUtil.getStringForGet(url.trim(), null,
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String arg0) {
//                        LogUtil.i("LHF", "NetworkUtil.onResponse:" + arg0);
//
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError arg0) {
//
//                        LogUtil.i("LHF", "NetworkUtil.onErrorResponse:" + arg0);
//                    }
//                });
//
//    }

}
