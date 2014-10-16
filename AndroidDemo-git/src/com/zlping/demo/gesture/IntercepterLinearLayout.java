package com.zlping.demo.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.LinearLayout;
/**
 * 带拦截的LinearLayout
 * @author 58tc
 *
 */
public class IntercepterLinearLayout extends LinearLayout {
    private long mOnclickTime;//记录点击时间
    private  static final String TAG = IntercepterLinearLayout.class.getSimpleName();
	public IntercepterLinearLayout(Context context) {
        super(context);
    }

	public IntercepterLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Animation animation = this.getAnimation();
		if (animation != null && !animation.hasEnded()) {
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}
	private float y; //记录y轴坐标
	private boolean isMove; //记录手是否移动
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        
        final int action = ev.getAction();
       
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("IntercepterLinearLayout", "MotionEvent.ACTION_DOWN");
                y =ev.getY();
                isMove=false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("IntercepterLinearLayout", "MotionEvent.ACTION_MOVE");
               if(!isMove&&Math.abs(y-ev.getY())>0){
                   isMove=true;
               }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("IntercepterLinearLayout", "MotionEvent.ACTION_UP");
                long sysTime = System.currentTimeMillis();
                if(sysTime-mOnclickTime<700&&!isMove){
                    mOnclickTime=sysTime;
                    return true;
                }
                mOnclickTime=sysTime;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
