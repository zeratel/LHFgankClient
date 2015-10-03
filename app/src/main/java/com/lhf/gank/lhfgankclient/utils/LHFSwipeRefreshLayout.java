package com.lhf.gank.lhfgankclient.utils;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * com.lhf.gank.lhfgankclient.utils
 * Created by zeratel3000
 * on 2015 10 15/10/2 22 53
 * description 参考Simple大神的代码
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能.
 *
 * @author mrsimple
 */
public class LHFSwipeRefreshLayout extends SwipeRefreshLayout {

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;
    /**
     * recyclerView实例
     */
    private RecyclerView recyclerView;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

//    /**
//     * recyclerView的加载中footer
//     */
//    private View mrecyclerViewFooter;

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    /**
     * @param context
     */
    public LHFSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public LHFSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

//        mrecyclerViewFooter = LayoutInflater.from(context).inflate(R.layout.recyclerView_footer, null,
//                false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 初始化recyclerView对象
        if (recyclerView == null) {
            getrecyclerView();
        }
    }

    /**
     * 获取recyclerView对象
     */
    private void getrecyclerView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof RecyclerView) {
                recyclerView = (RecyclerView) childView;
                // 设置滚动监听器给recyclerView, 使得滚动的情况下也可以自动加载

//                mrecyclerView.setOnScrollListener(this);

                //添加监听
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        // 滚动时到了最底部也可以加载更多
                        if (canLoad()) {
                            loadData();
                        }

                        super.onScrolled(recyclerView, dx, dy);
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                });

                Log.d(VIEW_LOG_TAG, "### 找到recyclerView");
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, recyclerView不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {

        if (recyclerView != null) {
            boolean flag = false;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                flag = linearLayoutManager.findLastVisibleItemPosition() + 1 == linearLayoutManager.getItemCount();

            } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {

                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                //两列的传入null貌似他会自己创建个给我们
                int[] temp = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[2]);
                boolean flag1 = temp[0]+1 == staggeredGridLayoutManager.getItemCount();
                boolean flag2 = temp[1]+1 == staggeredGridLayoutManager.getItemCount();
                flag = flag1 || flag2;

            }

            return flag;

        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
//            recyclerView.addFooterView(mrecyclerViewFooter);
        } else {
//            recyclerView.removeFooterView(mrecyclerViewFooter);
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

//    @Override
//    public void onScrollStateChanged(AbsrecyclerView view, int scrollState) {
//
//    }
//
//    @Override
//    public void onScroll(AbsrecyclerView view, int firstVisibleItem, int visibleItemCount,
//                         int totalItemCount) {
//        // 滚动时到了最底部也可以加载更多
//        if (canLoad()) {
//            loadData();
//        }
//    }

    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    public static interface OnLoadListener {
        public void onLoad();
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

}