/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.zlping.demo.pinnedlist;

import com.zlping.demo.R;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class GroupPinnedListView extends ListView implements OnScrollListener {

    /**
     * 该ListView的Adapter必须实现该接口
     * 
     * @author LiuXiaoyuan@hh.com.cn
     * @date 2011-9-13
     */
    public interface IPinnedListViewAdapter {

        /**
         * 固定标题状态：不可见
         */
        public static final int PINNED_HEADER_GONE = 0;

        /**
         * 固定标题状态：可见
         */
        public static final int PINNED_HEADER_VISIBLE = 1;

        /**
         * 固定标题状态：正在往上推
         */
        public static final int PINNED_HEADER_PUSHED_UP = 2;

        public int getGroupPosition(int firstVisiblePosition);

        public boolean isLastChildren(int position);

        public void configurePinnedView(View header, int groupPosition, int alpha);
    }

    private static final int MAX_ALPHA = 255;

    private IPinnedListViewAdapter mAdapter;

    private View mHeaderView;

    private boolean mHeaderVisible;

    private int mHeaderViewWidth;

    private int mHeaderViewHeight;

    private OnClickListener mPinnedHeaderClickLisenter;

    private int mPreGroupPosition = -1;

    public void setOnPinnedHeaderClickLisenter(OnClickListener listener) {
        mPinnedHeaderClickLisenter = listener;
    }

    public GroupPinnedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
    }

    public void setPinnedHeaderView(View view) {
        mHeaderView = view;
        if (mHeaderView != null) {
            setFadingEdgeLength(0);
        }

        View image = view.findViewById(R.id.app_icon);
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("GroupPinnedListView", "OnClickListener..........");
            }
        });
        image.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("GroupPinnedListView", "view onTouch********");
                // mScroller.startScroll(0, 0, 0, 20, 2000);
                // invalidate();
                return false;
            }
        });
    }

    public void setAdapter(BaseAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (IPinnedListViewAdapter) adapter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }
    }

    private int mOldState = -1;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int firstVisiblePosition = getFirstVisiblePosition();
        int state = getPinnedHeaderState(firstVisiblePosition);
        // 只有在状态改变时才layout，这点相当重要，不然可能导致视图不断的刷新
        if (mHeaderView != null && mAdapter != null && state != mOldState) {
            mOldState = state;
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
        }
        configureHeaderView(firstVisiblePosition);
    }

    public void configureHeaderView(int firstVisiblePosition) {
        if (mHeaderView == null || mAdapter == null) {
            return;
        }
        final int state = getPinnedHeaderState(firstVisiblePosition);
        switch (state) {
            case IPinnedListViewAdapter.PINNED_HEADER_GONE: {
                mHeaderVisible = false;
                break;
            }

            case IPinnedListViewAdapter.PINNED_HEADER_VISIBLE: {
                if (mPreGroupPosition != mAdapter.getGroupPosition(firstVisiblePosition)) {
                    mAdapter.configurePinnedView(mHeaderView, mAdapter.getGroupPosition(firstVisiblePosition), MAX_ALPHA);
                    measureChild(mHeaderView, mHeaderViewWidth, mHeaderViewHeight);
                }
                if (mHeaderView.getTop() != 0) {
                    mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
                }
                mHeaderVisible = true;
                break;
            }

            case IPinnedListViewAdapter.PINNED_HEADER_PUSHED_UP: {
                final View firstView = getChildAt(0);
                if (firstView == null) {
                    break;
                }
                int bottom = firstView.getBottom();
                int headerHeight = mHeaderView.getHeight();
                int y;
                int alpha;
                if (bottom < headerHeight) {
                    y = bottom - headerHeight;
                    alpha = MAX_ALPHA * (headerHeight + y) / headerHeight;
                } else {
                    y = 0;
                    alpha = MAX_ALPHA;
                }
                mAdapter.configurePinnedView(mHeaderView, mAdapter.getGroupPosition(firstVisiblePosition), alpha);
                if (mHeaderView.getTop() != y) {
                    mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight + y);
                }
                mHeaderVisible = true;
                break;
            }

            default:
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mHeaderView != null) {
            mHeaderView.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // 由于HeaderView并没有添加到ExpandableListView的子控件中，所以要draw他
        if (mHeaderVisible) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }

    // private float mDownX;
    //
    // private float mDownY;
    //
    // private static final float FINGER_WIDTH = 20;
    //
    // @Override
    // public boolean onTouchEvent(MotionEvent ev) {
    // if (mHeaderVisible) {
    // switch (ev.getAction()) {
    // case MotionEvent.ACTION_DOWN:
    // mDownX = ev.getX();
    // mDownY = ev.getY();
    // if (mDownX <= mHeaderViewWidth && mDownY <= mHeaderViewHeight) {
    // return true;
    // }
    // break;
    // case MotionEvent.ACTION_UP:
    // float x = ev.getX();
    // float y = ev.getY();
    //
    // float offsetX = Math.abs(x - mDownX);
    // float offsetY = Math.abs(y - mDownY);
    // // 如果在固定标题内点击了，那么触发事件
    // if (x <= mHeaderViewWidth && y <= mHeaderViewHeight && offsetX <=
    // FINGER_WIDTH && offsetY <= FINGER_WIDTH) {
    // if (mPinnedHeaderClickLisenter != null) {
    // mPinnedHeaderClickLisenter.onClick(mHeaderView);
    // }
    // return true;
    // }
    // break;
    // default:
    // break;
    // }
    // }
    // return super.onTouchEvent(ev);
    // }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mAdapter != null) {
            configureHeaderView(firstVisibleItem);
            mPreGroupPosition = mAdapter.getGroupPosition(firstVisibleItem);
        }

    }

    private int getPinnedHeaderState(int firstVisiblePosition) {
        if (mAdapter.isLastChildren(firstVisiblePosition)) {
            return IPinnedListViewAdapter.PINNED_HEADER_PUSHED_UP;
        } else if (firstVisiblePosition == -1) {
            return IPinnedListViewAdapter.PINNED_HEADER_GONE;
        } else {
            return IPinnedListViewAdapter.PINNED_HEADER_VISIBLE;
        }
    }

}
