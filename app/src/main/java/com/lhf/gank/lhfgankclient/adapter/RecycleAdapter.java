package com.lhf.gank.lhfgankclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lhf.gank.lhfgankclient.R;
import com.lhf.gank.lhfgankclient.activity.webview.GenWebView;
import com.lhf.gank.lhfgankclient.beans.NormalData;
import com.lhf.gank.lhfgankclient.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * com.lhf.gank.lhfgankclient.adapter
 * Created by zeratel3000
 * on 2015 09 15/9/28 09 26
 * description Recycle的Adapter
 */
public class RecycleAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<NormalData.ResultsEntity> resultsEntitys = new ArrayList<NormalData.ResultsEntity>();
    private String mode;

    public RecycleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.normal_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (resultsEntitys != null) {



            if (!TextUtils.isEmpty(mode) && mode.equals(Constants.FuLiStr)) {
                ((MyViewHolder) holder).iv.setVisibility(View.VISIBLE);
                //以前我写的
//                if (!TextUtils.isEmpty(tempWallPics.get(i).getUrl())) {
//                    Picasso.with(mContext)
//                            .load(tempWallPics.get(i).getUrl())
//                            .placeholder(R.drawable.yxblogo_gray)
//                            .error(R.drawable.yxblogo_gray).fit()
//                            .centerCrop().into(imageView);
//                } else {
//                    Picasso.with(mContext).load(R.drawable.white_plank)
//                            .error(R.drawable.white_plank).fit()
//                            .centerCrop().into(imageView);
//                }
                Picasso.with(context).load(Uri.parse(resultsEntitys.get(position)
                        .getUrl())).into(((MyViewHolder) holder).iv);

                ((MyViewHolder) holder).title.setVisibility(View.GONE);
                ((MyViewHolder) holder).provider.setVisibility(View.GONE);
                ((MyViewHolder) holder).time.setVisibility(View.GONE);

            } else {
                ((MyViewHolder) holder).iv.setVisibility(View.GONE);
                ((MyViewHolder) holder).title.setText(resultsEntitys.get(position).getDesc());
                ((MyViewHolder) holder).provider.setText(resultsEntitys.get(position).getWho());
                ((MyViewHolder) holder).time.setText(resultsEntitys.get(position).getCreatedAt().split("T")[0]);
                ((MyViewHolder) holder).rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GenWebView.class);
                        intent.putExtra(Constants.WEB_URL, resultsEntitys.get(position).getUrl());
                        intent.putExtra(Constants.TITLE, resultsEntitys.get(position).getDesc());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (resultsEntitys != null) {
            return resultsEntitys.size();
        } else {
            return 0;
        }
    }

    //继续添加新的
    public void addNormalData(NormalData normalData, String mode) {
        resultsEntitys.addAll(normalData.getResults());
        this.mode = mode;
    }

    //清除resultsEntitys
    public void clearNormalData() {
        resultsEntitys.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView provider;
        TextView time;
        ImageView iv;
        RelativeLayout rl;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            provider = (TextView) view.findViewById(R.id.provider);
            time = (TextView) view.findViewById(R.id.time);
            rl = (RelativeLayout) view.findViewById(R.id.rl);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }
}
