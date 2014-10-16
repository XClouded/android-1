package com.zlping.demo.games;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;

public class MyViewGroupLayout extends ViewGroup {

	public MyViewGroupLayout(Context context) {
		super(context);
	}

	public MyViewGroupLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		View child = getChildAt(0);
		ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
		final int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                lp.leftMargin + lp.rightMargin,
                lp.width);
        final int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                lp.topMargin + lp.bottomMargin,
                lp.height);
        Log.i("MyViewGroupLayout", "onMeasure widthSize="+widthSize+"|heightSize="+heightSize);
        Log.i("MyViewGroupLayout", "onMeasure lp.width="+lp.width+"|lp.height="+lp.height);
        Log.i("MyViewGroupLayout", "onMeasure drawerWidthSpec="+drawerWidthSpec+"|drawerHeightSpec="+drawerHeightSpec);
		child.measure(drawerWidthSpec,drawerHeightSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		View view = getChildAt(0);
		Log.i("MyViewGroupLayout", "onLayout getMeasuredWidth="+view.getMeasuredWidth()+"|getMeasuredHeight="+view.getMeasuredHeight());
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
	}
	
	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new ViewGroup.MarginLayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}
	
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new ViewGroup.MarginLayoutParams(getContext(), attrs);
	}
	
	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return new ViewGroup.MarginLayoutParams(p);
	}

}
