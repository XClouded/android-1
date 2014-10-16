
package com.zlping.demo.pinnedlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class ListTopTitleView extends RelativeLayout implements OnListViewTouchEventListener {
    private View mTitleView;

    private MarginLayoutParams mLayoutParams;

    private Scroller mScroller;

    private int mHeight;

    public ListTopTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);

    }

    public void setTitleView(View view) {
        mTitleView = view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = mTitleView.getHeight();
        mLayoutParams = (MarginLayoutParams) mTitleView.getLayoutParams();
        // mHeader.setHeight(mHeight);
    }

    // @Override
    // public void onScrollStateChanged(AbsListView view, int scrollState) {
    // if(scrollState==SCROLL_STATE_IDLE){
    // mIsScroll = false;
    // }else{
    // mIsScroll = true;
    // }
    // }
    //
    // private int mPreTop = 0;
    //
    // @Override
    // public void onScroll(AbsListView view, int firstVisibleItem, int
    // visibleItemCount, int totalItemCount) {
    // if (mIsScroll) {
    // setVisibility(View.VISIBLE);
    //
    // View firstView = getChildAt(0);
    // if(firstView==null){
    // return;
    // }
    // Log.i("TitleListView",
    // "firstVisibleItem="+firstVisibleItem+"|getScaleY1="+mHeader.getTop());
    //
    // int curTop = firstView.getTop();
    // int setOff = curTop - mPreTop;
    // if(setOff<0){
    //
    // }
    //
    // int bottom = firstView.getBottom();
    // int headerHeight = mHeader.getHeight();
    // int y=0;
    // if (bottom < headerHeight) {
    // y = bottom - headerHeight;
    // }
    //
    //
    //
    // if (mLayoutParams != null && mLayoutParams.topMargin != y) {
    // mLayoutParams.topMargin = y;
    // requestLayout();
    // // Log.i("TitleListView",
    // "firstVisibleItem="+firstVisibleItem+"|y="+mLayoutParams.topMargin);
    // }
    // }
    // }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int y = mScroller.getCurrY();
            mLayoutParams.topMargin = y;
            requestLayout();
        } else {
            Log.i("TitleListView", "computeScroll mScroller.getCurrY() ="+mScroller.getCurrY());
        }
    }

    public void open() {
        setVisibility(View.VISIBLE);
        mLayoutParams.topMargin = mLayoutParams.topMargin;
        mScroller.startScroll(0, mLayoutParams.topMargin, 0, -mLayoutParams.topMargin, 300);
        requestLayout();
    }

    public void close() {
        setVisibility(View.VISIBLE);
        mScroller.startScroll(0, mLayoutParams.topMargin, 0, -mHeight - mLayoutParams.topMargin, 300);
        postInvalidate();
    }

    public void toggle() {
        if (isShow() == Type.SHOW) {
            close();
        } else if (isShow() == Type.HIDDEN) {
            open();
        }
    }

    public void adjust() {
        if (mLayoutParams.topMargin >= 0 || mLayoutParams.topMargin <= -mHeight) {
            return;
        }
        if (mLayoutParams.topMargin > -mHeight / 2) {
            open();
        } else {
            close();
        }
    }

    private Type isShow() {
        if (mLayoutParams.topMargin <= -mHeight) {
            return Type.HIDDEN;
        } else if (mLayoutParams.topMargin >= 0) {
            return Type.SHOW;
        } else {
            return Type.ANIM;
        }
    }

    public enum Type {
        SHOW, HIDDEN, ANIM
    }

    private float mOldY;

	@Override
	public ScrollType onListViewTouchEvent(MotionEvent ev, boolean firstVisibleItem) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setDownTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
	}



}
