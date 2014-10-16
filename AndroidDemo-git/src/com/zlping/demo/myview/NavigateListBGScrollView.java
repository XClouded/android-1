
package com.zlping.demo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

public class NavigateListBGScrollView extends View implements AnimationListener{
    private LayoutParams mParams;
    private int mPreLeft;
    private int mPreTop;
    private int mTrueLeft;
    private int mTrueTop;
    private View mOnclickView;
    public NavigateListBGScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mParams = getLayoutParams();
    }
    
    public void initToView(View v,int position,ListView listView){
        int[] location = new int[2];
        getLocationOnScreen(location);
        mTrueLeft = location[0];
        mTrueTop = location[1];
        
        mParams.width = v.getWidth();
        mParams.height = v.getHeight();
        requestLayout();
        mOnclickView = v;
        onListItemClick(v,position,listView,0);
    }
    
    private int mPosition;
    public void onListItemClick(View v,int position,ListView listView,int offset) {
        int childIndex = 0;
        if(mPosition<listView.getFirstVisiblePosition()){
            childIndex = 0;
        }else if(mPosition>listView.getLastVisiblePosition()){
            childIndex = listView.getLastVisiblePosition();
        }else{
            childIndex = mPosition - listView.getFirstVisiblePosition();
        }
        
        View preView = listView.getChildAt(childIndex);
        int[] location = new int[2];
        preView.getLocationOnScreen(location);
        mPreLeft = location[0];
        mPreTop = location[1];
        
        
        v.getLocationOnScreen(location);
        int clickLeft = location[0];
        int clickTop = location[1];

        if(mPreTop<clickTop){
            mPreTop = mPreTop+offset;
        }else{
            clickTop = clickTop+offset;
        }

        Animation anim = new TranslateAnimation(mPreLeft - mTrueLeft, clickLeft-mTrueLeft, mPreTop - mTrueTop, clickTop - mTrueTop);
        anim.setDuration(300);
        anim.setAnimationListener(this);
        startAnimation(anim);
        
        mOnclickView = v;
        mPosition = position;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        setVisibility(View.INVISIBLE);
        mOnclickView.setSelected(true);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        
    }

}
