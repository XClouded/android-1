package com.zlping.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class AnimLinearLayout extends LinearLayout {
	public AnimLinearLayout(Context context) {
		super(context);
	}
	public AnimLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public AnimLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void moveLayout(){
		this.layout(getLeft()+10, getTop(), getRight()+10, getBottom());
//		this.setLeft(getLeft()+10);
//		this.setX(getLeft()+10);
//		this.setRight(getRight()+10);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
