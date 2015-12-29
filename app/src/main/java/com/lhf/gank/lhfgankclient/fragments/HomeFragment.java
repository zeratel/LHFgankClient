/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lhf.gank.lhfgankclient.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badoo.mobile.util.WeakHandler;
import com.lhf.gank.lhfgankclient.R;
import com.lhf.gank.lhfgankclient.adapter.RecycleAdapter;
import com.lhf.gank.lhfgankclient.beans.NormalData;
import com.lhf.gank.lhfgankclient.utils.Constants;
import com.lhf.gank.lhfgankclient.utils.LHFSwipeRefreshLayout;
import com.lhf.gank.lhfgankclient.utils.LogUtil;
import com.lhf.gank.lhfgankclient.utils.NetworkClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class HomeFragment extends Fragment {

    private String mode = "";
    private LHFSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int pages = 1;
    private RecycleAdapter recycleAdapter;
    private View view;
    private Context context;
    private int lines = 2;

    public HomeFragment(String mode) {
        this.mode = mode;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();

        view = inflater.inflate(R.layout.home_fragment, container, false);
        swipeRefreshLayout = (LHFSwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recycleAdapter = new RecycleAdapter(context);

        setupRecyclerView(recyclerView);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pages = 1;
                recycleAdapter.clearNormalData();
                //手动下拉刷新
                getData("" + pages);

                //5秒后取消刷新的标记
                new WeakHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }

        });

        //上拉加载
        swipeRefreshLayout.setOnLoadListener(new LHFSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {

                //手动下拉刷新
                getData("" + pages);

                //设置状态
                swipeRefreshLayout.setLoading(false);

                swipeRefreshLayout.setRefreshing(true);
                LogUtil.i("LHF", "swipeRefreshLayout.setOnLoadListener");
            }
        });

        return view;
    }

    @Override
    public void onStart() {

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                //貌似这玩意没有主动刷新的方法，这个只是显示个图。。。
                swipeRefreshLayout.setRefreshing(true);

                //dimss刷新的标记
                new WeakHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);

                new WeakHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        getData("" + pages);
                    }
                });


            }
        });

        super.onStart();
    }

    private void getData(String num_pages) {
        switch (mode) {
            case Constants.allStr:
                //all
                getGanHuo(Constants.allStr, num_pages);
                break;
            case Constants.FuLiStr:
                //福利
                getGanHuo(Constants.FuLiStr, num_pages);
                //福利的特殊处理
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(lines, StaggeredGridLayoutManager.VERTICAL));
                break;
            case Constants.AndroidStr:
                //Android
                getGanHuo(Constants.AndroidStr, num_pages);
                break;
            case Constants.iosStr:
                //ios
                getGanHuo(Constants.iosStr, num_pages);
                break;
            case Constants.restStr:
                //休息
                getGanHuo(Constants.restStr, num_pages);
                break;
            case Constants.tuozhanStr:
                //拓展
                getGanHuo(Constants.tuozhanStr, num_pages);
                break;
            case Constants.qianduanStr:
                //前端
                getGanHuo(Constants.qianduanStr, num_pages);
                break;

            default:
                break;
        }
    }

    // 获取数据
    private void getGanHuo(String pages_mode, String num_pages) {

        NetworkClient.getIntance().getFuLiURL(pages_mode, num_pages)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NormalData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("LHF", "NetworkUtil.onErrorResponse:" + e.getStackTrace());
//                        Snackbar.make(view, Constants.NET_ERROR_RESPONSE, Snackbar.LENGTH_LONG).show();
//                        停止刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(NormalData normalData) {
                        if (!normalData.getError() && normalData.getResults().size() != 0) {

                            recycleAdapter.addNormalData(normalData, mode);
                            recycleAdapter.notifyDataSetChanged();

                            //停止刷新
                            swipeRefreshLayout.setRefreshing(false);

                            //自动加一
                            pages += 1;
                        } else if (normalData.getResults().size() == 0) {
                            Snackbar.make(view, Constants.IS_ALL_LOAD, Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(view, Constants.API_ERROR_RESPONSE, Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

        //对比
        // /*建立HTTP Get对象*/
//        NetworkUtil networkUtil = NetworkUtil.getInstance(context);
//        networkUtil.setRoot(view);
//        networkUtil.getStringForGet(url, null,
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String arg0) {
//                        LogUtil.i("LHF", "NetworkUtil.onResponse:" + arg0);
//
//                        Gson gson = new Gson();
//                        NormalData normalData = gson.fromJson(arg0, NormalData.class);
//                        if (!normalData.getError() && normalData.getResults().size() != 0){
//
//                            recycleAdapter.addNormalData(normalData,mode);
//                            recycleAdapter.notifyDataSetChanged();
//
//                            //停止刷新
//                            swipeRefreshLayout.setRefreshing(false);
//
//                            //自动加一
//                            pages += 1;
//                        }else if (normalData.getResults().size() == 0){
//                            Snackbar.make(view, Constants.IS_ALL_LOAD, Snackbar.LENGTH_LONG).show();
//                        }else{
//                            Snackbar.make(view, Constants.API_ERROR_RESPONSE, Snackbar.LENGTH_LONG).show();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError arg0) {
//
//                        LogUtil.i("LHF", "NetworkUtil.onErrorResponse:" + arg0);
////                        Snackbar.make(view, Constants.NET_ERROR_RESPONSE, Snackbar.LENGTH_LONG).show();
////                        停止刷新
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                });

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
//                getRandomSublist(Cheeses.sCheeseStrings, 30)));

        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //设置adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        recyclerView.addItemDecoration();

    }

}
