package com.lhf.gank.lhfgankclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.lhf.gank.lhfgankclient.R;
import com.lhf.gank.lhfgankclient.app.GankApp;
import com.lhf.gank.lhfgankclient.utils.AppManager;

/**
 * com.lhf.gank.lhfgankclient.activity
 * Created by zeratel3000
 * on 2015 09 15/9/17 09 44
 * description activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = "BaseActivity";
    protected Context mContext;

    boolean isShowTitle = true;// 是否显示标题头 默认为true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (TextUtils.isEmpty(setTransitionMode())){
            //默认右边
            overridePendingTransition(R.anim.right_in,R.anim.right_out);
        } else {

            switch (setTransitionMode()) {
                case "LEFT":
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case "RIGHT":
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case "TOP":
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case "BOTTOM":
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case "SCALE":
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case "FADE":
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
                default:
                    break;
            }
        }
        super.onCreate(savedInstanceState);
        mContext = this;
        TAG = this.getClass().getName();
        AppManager.getAppManager().addActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (TextUtils.isEmpty(setTransitionMode())){
            //默认右边
            overridePendingTransition(R.anim.right_in,R.anim.right_out);
        } else {
            switch (setTransitionMode()) {
                case "LEFT":
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case "RIGHT":
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case "TOP":
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case "BOTTOM":
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case "SCALE":
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case "FADE":
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
                default:
                    break;
            }
        }
        System.gc();
        AppManager.getAppManager().removeActivity(this);
    }


    //获取开启activity的模式是滑动还是别的
    // return "RIGHT";
    protected abstract String setTransitionMode();

    public GankApp getApp() {
        return ((GankApp) getApplication());
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    private static Toast mToast;

    public void showToast(String message) {
        if (mToast != null) {
            mToast.setText(message);
        } else {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void showToast(int messageId) {
        showToast(getString(messageId));
    }

    public void showAlertDialog(String title, String message) {

    }

    public int getScreenWidth() {
        return getApp().screenWidth;
    }

    public int getScreenHeight() {
        return getApp().screenHeigth;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

    }

    @Override
    public void finish() {
        super.finish();
        dismissProgressDialog();
        AppManager.getAppManager().removeActivity(this);

    }

    protected void showProgressDialog() {
    }

    protected void dismissProgressDialog() {
    }

}
