package com.lhf.gank.lhfgankclient.activity.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.lhf.gank.lhfgankclient.R;
import com.lhf.gank.lhfgankclient.activity.BaseActivity;
import com.lhf.gank.lhfgankclient.utils.Constants;
import com.lhf.gank.lhfgankclient.utils.LogUtil;

/**
 * com.lhf.gank.lhfgankclient.webView
 * Created by zeratel3000
 * on 2015 09 15/9/28 09 34
 * description
 */
public class GenWebView extends BaseActivity {

    private Toolbar toolbar;
    private CircularProgressView progressView;

    @Override
    protected String setTransitionMode() {
        return null;
    }
    private WebView webView;
    public String TAG = "YXBGENWebView";
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        webView = (WebView) findViewById(R.id.wv);
        progressView = (CircularProgressView)findViewById(R.id.progress_view);

        //toolbar的设置
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //按钮的效果
        MaterialMenuDrawable materialMenuDrawable = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
        toolbar.setNavigationIcon(materialMenuDrawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //需要先设置一次。。。
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        //正在加载
        progressView.startAnimation();

    }


    @SuppressLint({ "SetJavaScriptEnabled" })
    protected void initData() {
        url  = getIntent().getStringExtra(Constants.WEB_URL);
        String title = getIntent().getStringExtra(Constants.TITLE);
        if (!TextUtils.isEmpty(title)){
            toolbar.setTitle(title);
        }

        webView.loadUrl(url);
        LogUtil.i("LHF", url);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        // Horizontal水平方向，Vertical竖直方向
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                //控制的动画的
                progressView.setIndeterminate(false);
                LogUtil.i("LHF", "onPageFinished");
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                LogUtil.i("LHF", url);
                return true;
            }
        });

        // 开启localStorage
        webSettings.setDomStorageEnabled(true);

        // 开启数据缓存 DOM Storage
        webSettings.setDatabaseEnabled(true);
        String databasePath = webView.getContext()
                .getDir("databases", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(databasePath);

        // 开启App缓存 AppCache
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir()
                .getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);

    }

    @Override
    protected void onStart() {
        initData();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
