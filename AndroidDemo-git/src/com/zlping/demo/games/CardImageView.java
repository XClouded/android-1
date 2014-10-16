package com.zlping.demo.games;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class CardImageView extends ImageView {
	private Scroller mScroller;
	private RelativeLayout.LayoutParams lp;
	public CardImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public CardImageView(Context context) {
		super(context);
	}
	@Override
	public void computeScroll() {
		if(mScroller!=null&&lp!=null&&mScroller.computeScrollOffset()){
			int x = mScroller.getCurrX();
			int finalX = mScroller.getFinalX();
			lp.leftMargin = x;
			if(x == finalX){
				mScroller.abortAnimation();
			}
			postInvalidate();
			requestLayout();
		}
	}
	
	public void startMarginAnim(int end){
		mScroller = new Scroller(getContext());
		lp = (RelativeLayout.LayoutParams)getLayoutParams();
		mScroller.startScroll(lp.leftMargin, 0, end-lp.leftMargin, 0, 200);
		postInvalidate();
	}

}
