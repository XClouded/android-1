package com.zlping.demo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MyImageView extends ImageView{

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("MyImageView", "onTouchEvent...");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        Log.i("MyImageView", "performClick.............=");
        return super.performClick();
    }
}
