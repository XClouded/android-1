
package com.zlping.demo.pinnedlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public abstract class ScrollVerticalViewGroup extends ViewGroup {

    private static final Interpolator sInterpolator = new Interpolator() {
        public final float getInterpolation(float input) {
            float f = input - 1.0F;
            return 1.0F + f * (f * (f * (f * f)));
        }
    };

    /**
     * 平滑的时间
     */
    private static final int SMOOTH_DURATION_MILLIS = 500;

    // 方向
    public static final int SCROLL_DIRECTION_RIGHT = 1;
    public static final int SCROLL_DIRECTION_NO = 0;
    public static final int SCROLL_DIRECTION_LEFT = -1;

    private float mFlingVelocity = 0.0f;
    private boolean mFlingable = false;
    protected boolean mIsBeingDragged = false;
    protected float[] mLastPosition = {
            0.0f, 0.0f
    };// 动画执行之前的位置
    // 滑动范围, 最小值和最大值
    private final int[] mLimits = {
            Integer.MIN_VALUE, Integer.MAX_VALUE
    };
    private int mMaximumVelocity;
    private int mMinimumVelocity;

    private int mScrollDirection = SCROLL_DIRECTION_NO;
    private boolean mScrollEnabled = true;
    protected Scroller mScroller;
    protected int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public ScrollVerticalViewGroup(Context context) {
        super(context);
        init();
    }

    public ScrollVerticalViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollVerticalViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setFocusable(false);
        ViewConfiguration viewConfig = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfig.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfig.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfig.getScaledMaximumFlingVelocity();
        this.mScroller = new Scroller(getContext(), sInterpolator);
    }

    @Override
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int currY = this.mScroller.getCurrY();
            int finalY = this.mScroller.getFinalY();

            scrollTo(clampToScrollLimits(currY));

            if (currY == finalY) {
                this.mScroller.abortAnimation();
                // 计算滚动的方向
                if (mFlingable) {
                    if (mFlingVelocity < 0.0f) {
                        onScrollFinished(1);
                    } else if (mFlingVelocity > 0.0f) {
                        onScrollFinished(-1);
                    }
                    this.mFlingVelocity = 0.0f;
                }
            }

            // 继续绘制，让Animation结束
            postInvalidate();
        }
    }

    /**
     * direction 如果>0,表示滑向右边 direction 如果<0，表示滑向左边
     * 
     * @param direction
     */
    protected void onScrollFinished(int direction) {

    }

    /**
     * 用于计算有没有滚动出界
     * 
     * @param currX
     * @return
     */
    private int clampToScrollLimits(int currY) {
        if (currY < this.mLimits[0]) {
            currY = this.mLimits[0];
        } else if (currY > this.mLimits[1]) {
            currY = this.mLimits[1];
        }
        return currY;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return shouldStartDrag(ev);
    }

    private boolean shouldStartDrag(MotionEvent ev) {
        if (!this.mScrollEnabled) {
            return false;
        }

        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
            return true;
        }
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: // 这个down事件，只有会在open之后，才会被调用，所以mReceivedDown只会在open之后，才会被设置为true
                updatePosition(ev);
                if (!mIsBeingDragged) {
                    startDrag();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = ev.getY() - this.mLastPosition[0];
                if (Math.abs(deltaY) > this.mTouchSlop) {
                    updatePosition(ev);
                    startDrag();
                }
                break;
        }

        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        // 没有进行初始化
        if (!this.mIsBeingDragged && shouldStartDrag(event)) {
            return true;
        }

        // 速度探查器
        if (this.mFlingable) {
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(event);
        }
        
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (mIsBeingDragged) {
                    float lastY = this.mLastPosition[0];
                    float lastX = this.mLastPosition[1];
                    updatePosition(event);
                    float deltaY = lastY - this.mLastPosition[0];
                    float deltaX = lastX - this.mLastPosition[1];
                    if(Math.abs(deltaX)/Math.abs(deltaY)>1){//如果是大于45度测不移动
                        return false;
                    }
                    if (deltaY >= 1.0f) { // 值为反向
                        mScrollDirection = SCROLL_DIRECTION_LEFT;
                    }
                    else if (deltaY <= -1.0f) {
                        mScrollDirection = SCROLL_DIRECTION_RIGHT;
                    }
                    // 这样相当于添加了阻力
                    if (Math.abs(deltaY) > 1.0f) {
//                        if(getScrollY()==0){
//                            return false;
//                        }
                        scrollTo(getScrollY() + (int) deltaY);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged) {
                    mIsBeingDragged = false;
                    if (mFlingable) {
                        mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                        int initialVelocity = (int) mVelocityTracker.getYVelocity();

                        if (Math.abs(initialVelocity) > mMinimumVelocity) {
                            this.mScroller.fling(getScrollY(), getScrollY(), -initialVelocity, 0,
                                    this.mLimits[0], this.mLimits[1], 0, 0);
                            this.mFlingVelocity = -initialVelocity;
                            invalidate();
                            break;
                        }

                        if (mVelocityTracker != null) {
                            mVelocityTracker.recycle();
                            mVelocityTracker = null;
                        }
                    }

                    onScrollFinished(this.mScrollDirection);
                }
                break;
        }
        return false;
    }

    public void scrollTo(int y) {
        scrollTo(0, clampToScrollLimits(y));
    }

   protected void updatePosition(MotionEvent ev) {
        this.mLastPosition[0] = ev.getY();
        this.mLastPosition[1] = ev.getX();
    }

    protected void startDrag() {
        this.mIsBeingDragged = true;
        this.mFlingVelocity = 0.0F;
        this.mScrollDirection = 0;
        this.mScroller.abortAnimation();
    }

    public final void smoothScrollTo(int y) {
        int deltaY = clampToScrollLimits(y) - getScrollY();
        this.mScroller.startScroll(getScrollX(), getScrollY(), 0, deltaY, SMOOTH_DURATION_MILLIS);
        invalidate();
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        this.mScrollEnabled = scrollEnabled;
    }

    public void setScrollLimits(int minLimit, int maxLimit) {
        this.mLimits[0] = minLimit;
        this.mLimits[1] = maxLimit;
    }

    // public void setFlingable(boolean flingable) {
    // this.mFlingable = flingable;
    // }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        requestDisallowInterceptTouchEvent(true);
        return super.showContextMenuForChild(originalView);
    }

}
