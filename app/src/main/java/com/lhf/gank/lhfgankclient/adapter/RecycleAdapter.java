package com.lhf.gank.lhfgankclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lhf.gank.lhfgankclient.R;
import com.lhf.gank.lhfgankclient.beans.NormalData;
import com.lhf.gank.lhfgankclient.utils.Constants;
import com.lhf.gank.lhfgankclient.webView.GenWebView;

/**
 * com.lhf.gank.lhfgankclient.adapter
 * Created by zeratel3000
 * on 2015 09 15/9/28 09 26
 * description Recycle的Adapter
 */
public class RecycleAdapter extends RecyclerView.Adapter {

    private NormalData normalData;
    private Context context;

    public RecycleAdapter(NormalData normalData, Context context) {
        this.normalData = normalData;
        this.context = context;

    }

    public RecycleAdapter(Context context) {
        this.context = context;
    }

    public void setNormalData(NormalData normalData) {
        this.normalData = normalData;
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
        if (normalData != null && !normalData.getError()){

            ((MyViewHolder)holder).title.setText(normalData.getResults().get(position).getDesc());
            ((MyViewHolder)holder).provider.setText(normalData.getResults().get(position).getWho());
            ((MyViewHolder)holder).time.setText(normalData.getResults().get(position).getCreatedAt().split("T")[0]);
            ((MyViewHolder)holder).rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GenWebView.class);
                    intent.putExtra(Constants.WEB_URL,normalData.getResults().get(position).getUrl());
                    intent.putExtra(Constants.TITLE,normalData.getResults().get(position).getDesc());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (normalData != null && !normalData.getError()) {
            return normalData.getResults().size();
        } else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView provider;
        TextView time;
        RelativeLayout rl;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            provider = (TextView) view.findViewById(R.id.provider);
            time = (TextView) view.findViewById(R.id.time);
            rl = (RelativeLayout) view.findViewById(R.id.rl);
        }
    }
}
