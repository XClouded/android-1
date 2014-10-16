
package com.zlping.demo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class NavigateBGScrollView extends View{
    private LayoutParams mParams;
    private int mPreLeft;
    private int mPreTop;
    private int mTrueLeft;
    private int mTrueTop;
    public NavigateBGScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mParams = getLayoutParams();
        Log.i("NavigateBGScrollView", "onMeasure");
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("NavigateBGScrollView", "onLayout");
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i("NavigateBGScrollView", "onFinishInflate");
    }
    
    public void initToView(View v){
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        mPreLeft = location[0];
        mPreTop = location[1];
        
        getLocationOnScreen(location);
        mTrueLeft = location[0];
        mTrueTop = location[1];
        
        mParams.width = v.getWidth();
        mParams.height = v.getHeight();
        requestLayout();
        
        onClickView(v);
    }
    public void onClickView(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int clickLeft = location[0];
        int clickTop = location[1];

        Animation anim = new TranslateAnimation(mPreLeft - mTrueLeft, clickLeft-mTrueLeft, mPreTop - mTrueTop, clickTop - mTrueTop);
        anim.setDuration(300);
        anim.setFillAfter(true);
        startAnimation(anim);
        
        mPreLeft = clickLeft;
        mPreTop = clickTop;
    }
    
    
   

}
