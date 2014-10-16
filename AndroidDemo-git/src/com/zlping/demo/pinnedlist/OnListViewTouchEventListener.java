package com.zlping.demo.pinnedlist;

import android.view.MotionEvent;

public interface OnListViewTouchEventListener {
    /**
     * 
     * @param ev
     * @param firstVisibleItem 是否第一行
     * @return TITLE 的滚动状态 NONE 不滚动 UP 向上滚动 DOWN 下向滚动 UP_END向上滚动到头
     */
    public ScrollType onListViewTouchEvent(MotionEvent ev, boolean firstVisibleItem);
    
    public void setDownTouchEvent(MotionEvent ev);
    public enum ScrollType{
    	NONE, //默认没有反应
    	UP, //向上滑动
    	DOWN, //向下滑动
    	UP_CANNOT, 
    	DOWN_CANNOT, 
    	ANIM, //正在自已做动画
    	UP_END,//向上滑动结束
    	DOWN_END;//向下滑动结束
    }
}
