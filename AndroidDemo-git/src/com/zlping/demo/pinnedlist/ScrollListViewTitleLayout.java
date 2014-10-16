package com.zlping.demo.pinnedlist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ListView;
import android.widget.Scroller;

public class ScrollListViewTitleLayout extends ViewGroup implements OnListViewTouchEventListener {
	private static final String TAG = "ScrollVerticalLayout";

	private int mMoveHeightMax;// 能滑动的最大高度

	private Scroller mScroller;

	private View mScrollVeiw;

	private boolean mIsScroll = true;// 是否可以滚动

	private Type mCurStatus = Type.SHOW;

	private OnChangeStatusLinstener mOnChangeStatusLinstener;

	// 滑动范围, 最小值和最大值
	private final int[] mLimits = { Integer.MIN_VALUE, Integer.MAX_VALUE };

	private static final Interpolator sInterpolator = new Interpolator() {
		public final float getInterpolation(float input) {
			float f = input - 1.0F;
			return 1.0F + f * (f * (f * (f * f)));
		}
	};

	public ScrollListViewTitleLayout(Context context) {
		super(context);
	}

	public ScrollListViewTitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(getContext(), sInterpolator);
	}

	public ScrollListViewTitleLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void computeScroll() {
		if (this.mScroller.computeScrollOffset()) {
			int currY = this.mScroller.getCurrY();
			int finalY = this.mScroller.getFinalY();
			scrollTo(clampToScrollLimits(currY));
			if (currY == finalY) {
				this.mScroller.abortAnimation();
			}
			// 继续绘制，让Animation结束
			postInvalidate();
		}
	}

	public void setScrollLimits(int minLimit, int maxLimit) {
		this.mLimits[0] = minLimit;
		this.mLimits[1] = maxLimit;
	}

	public void initScrollVeiw(OnTouchInterceptListener listener, View scrollView, boolean isClose) {
		mScrollVeiw = scrollView;
		listener.addOnListViewTouchEventListener(this);
		if (isClose) {
			close();
		}
		
	}

	public void scrollTo(int y) {
		scrollTo(0, clampToScrollLimits(y));
		changeStatus();
	}

	public final void smoothScrollTo(int y) {
		int deltaY = clampToScrollLimits(y) - getScrollY();
		this.mScroller.startScroll(0, getScrollY(), 0, deltaY, 500);
		invalidate();
	}

	public void changeStatus() {
		int scrollY = getScrollY();
		if (scrollY <= -mMoveHeightMax) {
			if (mCurStatus != Type.SHOW && mOnChangeStatusLinstener != null) {
				mOnChangeStatusLinstener.onChangeStatus(true);
				requestLayout();
			}
			mCurStatus = Type.SHOW;

		} else if (scrollY >= 0) {
			if (mCurStatus != Type.HIDDEN && mOnChangeStatusLinstener != null) {
				mOnChangeStatusLinstener.onChangeStatus(false);
				requestLayout();
			}
			mCurStatus = Type.HIDDEN;
		} else {
			mCurStatus = Type.ANIM;
		}
	}

	public enum Type {
		SHOW, HIDDEN, ANIM
	}

	public int getScrollDistance() {
		return getScrollY() + mMoveHeightMax;
	}

	public final void open() {
		if (mIsScroll) {
			smoothScrollTo(-this.mMoveHeightMax);
		}
	}

	public final void close() {
		if (mIsScroll) {
			smoothScrollTo(0);
		}
	}

	public void toggle() {
		if (mCurStatus == Type.SHOW) {
			close();
		} else if (mCurStatus == Type.HIDDEN) {
			open();
		}
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

	protected void onScrollFinished(int direction) {
		if (Math.abs(getScrollY()) > mMoveHeightMax / 2) { // 表示Open
			smoothScrollTo(-this.mMoveHeightMax);
		} else {
			smoothScrollTo(0);
		}
	}

	public void setScrollEanable(boolean flag) {
		if (flag) {
			mCurStatus = Type.SHOW;
			if (mOnChangeStatusLinstener != null) {
				mOnChangeStatusLinstener.onChangeStatus(true);
			}
		} else {
			mCurStatus = Type.HIDDEN;
			if (mOnChangeStatusLinstener != null) {
				mOnChangeStatusLinstener.onChangeStatus(false);
			}
		}
		mIsScroll = flag;
	}

	private float mOldY;
	private ScrollType mPreType;
	@Override
	public ScrollType onListViewTouchEvent(MotionEvent ev, boolean firstVisibleItem) {
		ScrollType type = ScrollType.NONE;
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mOldY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			mScroller.abortAnimation();
			if (mOldY == 0) {
				type = ScrollType.DOWN;
				mOldY = ev.getRawY();
			}
			float deltaY = mOldY - ev.getRawY();
			// if(Math.abs(deltaY)<2.0f){
			// break;
			// }
			mOldY = ev.getRawY();
			if (mIsScroll && mMoveHeightMax > 0) {
				if (deltaY > 0 && mCurStatus != Type.HIDDEN) {
					scrollTo(getScrollY() + (int) deltaY);
					type = ScrollType.UP;
				} else if (deltaY < 0 && firstVisibleItem && mCurStatus != Type.SHOW) {
					scrollTo(getScrollY() + (int) deltaY);
					type = ScrollType.DOWN;
				} else if (deltaY > 0 && mCurStatus == Type.HIDDEN) {
					if(mPreType==ScrollType.UP){
						type = ScrollType.UP_END;
					}else{
						type = ScrollType.UP_CANNOT;
					}
				} else if (deltaY < 0 && firstVisibleItem && mCurStatus == Type.SHOW) {
					if(mPreType==ScrollType.DOWN){
						type = ScrollType.DOWN_END;
					}else{
						type = ScrollType.DOWN_CANNOT;
					}
				}
			}else{
				if (deltaY > 0) {
					type = ScrollType.UP_CANNOT;
				} else if (deltaY < 0 && firstVisibleItem) {
					type = ScrollType.DOWN_CANNOT;
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			if (mPreType == ScrollType.DOWN) { // 表示Open
				smoothScrollTo(-this.mMoveHeightMax);
				type = ScrollType.ANIM;
			} else if(mPreType == ScrollType.UP){
				smoothScrollTo(0);
				type = ScrollType.ANIM;
			}
			mOldY = 0;
			break;
		}
		if (type == ScrollType.NONE && mCurStatus == Type.ANIM && action != MotionEvent.ACTION_UP
				&& action != MotionEvent.ACTION_CANCEL) {
			type = ScrollType.ANIM;
		}
		mPreType = type;
		return type;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = View.MeasureSpec.getSize(widthMeasureSpec);
		int height = View.MeasureSpec.getSize(heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		getChildAt(0).measure(View.MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
				View.MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
		if (mScrollVeiw != null) {
			mMoveHeightMax = mScrollVeiw.getHeight();
			setScrollLimits(-this.mMoveHeightMax, 0);
			height = height + mMoveHeightMax;
			getChildAt(0).measure(View.MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
					View.MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		getChildAt(0).layout(0, -mMoveHeightMax, r - l, b - t);
		if (mCurStatus == Type.SHOW) {
			scrollTo(0, -mMoveHeightMax);
		} else if (mCurStatus == Type.HIDDEN) {
			scrollTo(0, 0);
		}
	}

	public void setOnChangeStatusLinstener(OnChangeStatusLinstener onChangeStatusLinstener) {
		mOnChangeStatusLinstener = onChangeStatusLinstener;
		if (mCurStatus == Type.SHOW) {
			mOnChangeStatusLinstener.onChangeStatus(true);
		} else if (mCurStatus == Type.HIDDEN) {
			mOnChangeStatusLinstener.onChangeStatus(false);
		}
	}

	public interface OnChangeStatusLinstener {
		public void onChangeStatus(boolean isShow);
	}

	@Override
	public void setDownTouchEvent(MotionEvent ev) {
		mOldY = ev.getRawY();
	}
	
	public static class OnTouchInterceptListener implements OnTouchListener {
		private List<OnListViewTouchEventListener> mOnListViewTouchEvent = new ArrayList<OnListViewTouchEventListener>();

		public void addOnListViewTouchEventListener(OnListViewTouchEventListener mOnListViewTouchEvent) {
			this.mOnListViewTouchEvent.add(mOnListViewTouchEvent);
		}

		private int index = 0;

		private boolean isFirstDown = true;

		private ListView mListView;
		public OnTouchInterceptListener(ListView view) {
			mListView = view;
			mListView.setOnTouchListener(this);
		}

		@Override
		public boolean onTouch(View v, MotionEvent ev) {
			if (mOnListViewTouchEvent.size() > 0) {
				int firstitem = mListView.getFirstVisiblePosition();
				boolean isFirst = false;
				if (firstitem <= 0) {
					View view = mListView.getChildAt(0);
					if (view.getTop() == 0) {
						isFirst = true;
					}
				}

				OnListViewTouchEventListener linstener = mOnListViewTouchEvent.get(index);
				ScrollType type = linstener.onListViewTouchEvent(ev, isFirst);
				Log.i(TAG, "type = " + type + "|index=" + index + "|action=" + ev.getAction());
				if ((type == ScrollType.UP_CANNOT||type == ScrollType.UP_END) && index < mOnListViewTouchEvent.size() - 1) {
					index++;
					mOnListViewTouchEvent.get(index).setDownTouchEvent(ev);
				} else if (type == ScrollType.DOWN_CANNOT || type == ScrollType.DOWN_END) {
					if (isFirstDown) {
						index = mOnListViewTouchEvent.size() - 1;
						isFirstDown = false;
					} else if (index > 0) {
						index--;
						mOnListViewTouchEvent.get(index).setDownTouchEvent(ev);
					}
				}

				if ((type == ScrollType.UP || type == ScrollType.UP_END ||type == ScrollType.ANIM || type == ScrollType.DOWN || type == ScrollType.DOWN_END)
						&& ev.getAction() != MotionEvent.ACTION_UP) {
					return true;
				}

			}
			return false;
		}
		
	}

}
