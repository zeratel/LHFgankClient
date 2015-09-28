package com.lhf.gank.lhfgankclient.webView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
    }


    @SuppressLint({ "SetJavaScriptEnabled" })
    protected void initData() {
//		boolean show_yxbcookie = getIntent().getBooleanExtra(
//				StringConstants.SHOW_YXBCOOKIE, true);
        url  = getIntent().getStringExtra(Constants.WEB_URL);

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
