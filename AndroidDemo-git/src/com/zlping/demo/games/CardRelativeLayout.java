package com.zlping.demo.games;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class CardRelativeLayout extends RelativeLayout {
	private int mWith;
	private int mItemWidth;
	public CardRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public CardRelativeLayout(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWith = getMeasuredWidth();
	}
	
	public void initShowLayout(){
		post(new Runnable() {
			@Override
			public void run() {
				int childCount = getChildCount();
				if(childCount>0){
					mItemWidth = getChildAt(0).getLayoutParams().width;
				}
				int itemMargin = getItemMarginLeft(childCount);
				for (int i = 0; i < childCount; i++) {
					View card = getChildAt(i);
					RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)card.getLayoutParams();
					int marginLift = getMarginLeftOfView(childCount,i,itemMargin);
					param.leftMargin = marginLift;
				}
				Animation anim = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f);
				anim.setDuration(200);
				anim.setInterpolator(new OvershootInterpolator());
				startAnimation(anim);
			}
		});
	}
	
	public void refreshLayout(){
		int childCount = getChildCount();
		int childItemMargin = getItemMarginLeft(childCount);
		for(int i=0;i<childCount;i++){
			int childMarginLeft = getMarginLeftOfView(childCount,i,childItemMargin);
			View childView = getChildAt(i);
			Scroller scroller = new Scroller(getContext());
			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)childView.getLayoutParams();
			scroller.startScroll(lp.leftMargin, 0, childMarginLeft-lp.leftMargin, 0, 200);
			childView.setTag(scroller);
		}
		isRefreshAnim = true;
		postInvalidate();
	}
	
	private boolean isRefreshAnim = false;
	private int computeCount = 0 ;
	@Override
	public void computeScroll() {
		if(isRefreshAnim){
			int childCount = getChildCount();
			for(int i=0;i<childCount;i++){
				View childView = getChildAt(i);
				Object obj = childView.getTag();
				if(obj instanceof Scroller){
					Scroller mScroller = (Scroller)obj;
					if(mScroller.computeScrollOffset()){
						int x = mScroller.getCurrX();
						int finalX = mScroller.getFinalX();
						RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)childView.getLayoutParams();
						lp.leftMargin = x;
						if(x == finalX){
							mScroller.abortAnimation();
							computeCount++;
							if(computeCount==childCount){
								Log.i("CardRelativeLayout", "computeScroll true");
								isRefreshAnim = false;
								computeCount = 0;
							}
						}
						Log.i("CardRelativeLayout", "computeScroll");
						postInvalidate();
						requestLayout();
					}
				}
			}
		}
	}
	
	private int getItemMarginLeft(int count){
		int itemMargin = 0;
		if (mItemWidth * count > mWith && count > 1) {
			itemMargin = (mItemWidth * count - mWith) / (count - 1);
		}else{
			itemMargin = (mWith-mItemWidth * count)/(count+1);
		}
		return itemMargin;
	}
	
	private int getMarginLeftOfView(int count,int index,int itemMargin){
		int marginLift = 0;
		if (mItemWidth * count > mWith){
			marginLift = (mItemWidth - itemMargin) * index;
		}else{
			marginLift = itemMargin*(index+1)+mItemWidth*index;
		}
		return marginLift;
	}

}
