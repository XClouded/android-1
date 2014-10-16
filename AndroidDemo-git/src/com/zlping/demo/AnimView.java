package com.zlping.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class AnimView extends View {
	
	public AnimView(Context context) {
		super(context);
	}
	
	public AnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AnimView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void moveView(){
		layout(getLeft()+10, getTop(), getRight()+10, getBottom());
	}

}
