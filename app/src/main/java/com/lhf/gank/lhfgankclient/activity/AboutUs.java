package com.lhf.gank.lhfgankclient.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.lhf.gank.lhfgankclient.R;
import com.lhf.gank.lhfgankclient.activity.webview.GenWebView;
import com.lhf.gank.lhfgankclient.utils.Constants;

/**
 * com.lhf.gank.lhfgankclient.activity
 * Created by zeratel3000
 * on 2015 10 15/10/9 10 24
 * description
 */
public class AboutUs extends BaseActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private TextView about_us_title_tv;
    private TextView about_us_mailbox;
    private TextView about_us_tools;
    private TextView about_us_code;

    @Override
    protected String setTransitionMode() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        about_us_title_tv = (TextView)findViewById(R.id.about_us_title_tv);
        about_us_mailbox = (TextView)findViewById(R.id.about_us_mailbox);
        about_us_tools = (TextView)findViewById(R.id.about_us_tools);
        about_us_code = (TextView)findViewById(R.id.about_us_code);

        about_us_title_tv.setOnClickListener(this);
        about_us_mailbox.setOnClickListener(this);
        about_us_tools.setOnClickListener(this);
        about_us_code.setOnClickListener(this);

        //toolbar的设置
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //按钮的效果
        MaterialMenuDrawable materialMenuDrawable = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);

        //需要先设置一次。。。
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        //注意顺序！！！
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(materialMenuDrawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_us_title_tv:
                goToWeb(Constants.about_us_title_url,getResources().getString(R.string.about_us_title));
                break;
            case R.id.about_us_mailbox:
                goToWeb(Constants.about_us_mailbox_url,getResources().getString(R.string.about_us_mailbox));
                break;
            case R.id.about_us_tools:
                goToWeb(Constants.about_us_tools_url,getResources().getString(R.string.about_us_tools));
                break;
            case R.id.about_us_code:
                goToWeb(Constants.about_us_code_url,getResources().getString(R.string.about_us_code));
                break;

            default:
                break;
        }
    }

    private void goToWeb(String url,String title) {
        Intent intent = new Intent(mContext, GenWebView.class);
        intent.putExtra(Constants.WEB_URL, url);
        intent.putExtra(Constants.TITLE, title);
        mContext.startActivity(intent);
    }
}
