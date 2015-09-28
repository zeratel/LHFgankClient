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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lhf.gank.lhfgankclient.R;
import com.lhf.gank.lhfgankclient.adapter.RecycleAdapter;
import com.lhf.gank.lhfgankclient.utils.Constants;
import com.lhf.gank.lhfgankclient.utils.LogUtil;
import com.lhf.gank.lhfgankclient.utils.NetworkUtil;

public class HomeFragment extends Fragment {

    private String mode = "";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int num = 20;
    private int pages = 1;

    public HomeFragment(String mode) {
        this.mode = mode;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        setupRecyclerView(recyclerView);

        getData("/" + num + "/" + pages);

//        getArguments()
        return view;
    }

    private void getData(String num_pages) {
        switch (mode) {
            case Constants.allStr:
                //all
                getGanHuo(Constants.allURL + num_pages);
                break;
            case Constants.FuLiStr:
                //福利
                getGanHuo(Constants.FuLiURL + num_pages);
                break;
            case Constants.AndroidStr:
                //Android
                getGanHuo(Constants.AndroidURL + num_pages);
                break;
            case Constants.iosStr:
                //ios
                getGanHuo(Constants.iosURL + num_pages);
                break;
            case Constants.restStr:
                //休息
                getGanHuo(Constants.restURL + num_pages);
                break;
            case Constants.tuozhanStr:
                //拓展
                getGanHuo(Constants.tuozhanURL + num_pages);
                break;
            case Constants.qianduanStr:
                //前端
                getGanHuo(Constants.qianduanURL + num_pages);
                break;

            default:
                break;
        }
    }

    // 获取数据
    private void getGanHuo(String url) {

        // /*建立HTTP Get对象*/
        NetworkUtil networkUtil = new NetworkUtil(getActivity());
        networkUtil.getStringForGet(url.trim(), null,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String arg0) {
                        LogUtil.i("LHF", "NetworkUtil.onResponse:" + arg0);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError arg0) {

                        LogUtil.i("LHF", "NetworkUtil.onErrorResponse:" + arg0);
                    }
                });

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
//                getRandomSublist(Cheeses.sCheeseStrings, 30)));

        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置adapter
        recyclerView.setAdapter(new RecycleAdapter());
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        recyclerView.addItemDecoration();
    }

}
