
package com.zlping.demo.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.zlping.demo.R;

public class DrawLinerLayout extends LinearLayout {
    private ViewGroup mHeaderView;

    private int mHeaderViewWidth;

    private int mHeaderViewHeight;

    private Scroller mScroller;

    public DrawLinerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public void setView(View view) {
        mHeaderView = (ViewGroup) view;
        View image = view.findViewById(R.id.app_icon);
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DrawLinerLayout", "OnClickListener..........");
            }
        });
        image.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("DrawLinerLayout", "view onTouch********");
                // mScroller.startScroll(0, 0, 0, 20, 2000);
                // invalidate();
                return false;
            }
        });
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // if(mScroller.computeScrollOffset()){
        // Log.i("DrawLinerLayout", "computeScroll......");
        // int y = mScroller.getCurrY();
        // scrollTo(0, y);
        // invalidate();
        // }else{
        // Log.i("DrawLinerLayout", "isFinished......");
        // }
        //
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
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
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // 由于HeaderView并没有添加到ListView的子控件中，所以要draw他
        if (mHeaderView != null) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }
}
