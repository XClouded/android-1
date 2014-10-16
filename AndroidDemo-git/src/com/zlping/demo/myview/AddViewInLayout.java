package com.zlping.demo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class AddViewInLayout extends ViewGroup {

    public AddViewInLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void addView(View child, int index, LayoutParams params,
            boolean preventRequestLayout){
        addViewInLayout(child, index, params, preventRequestLayout);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        
        
    }

}
