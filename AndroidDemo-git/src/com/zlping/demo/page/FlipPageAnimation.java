package com.zlping.demo.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;


public class FlipPageAnimation {
    public PageWidget mPageWidget;
    private View mContainer;// 用来实现翻页效果的容器
    private CopyOfQuickMenuListAdapter mQuickAdapter;
    private Context mContext;
    public FlipPageAnimation(Context context, PageWidget pageWidget, View container, CopyOfQuickMenuListAdapter quickAdapter){
        mContext = context;
        mPageWidget = pageWidget;
        mContainer = container;
        mQuickAdapter = quickAdapter;
    }
    
    public void startFlipPage(){
        mContainer.setDrawingCacheEnabled(true);
        Bitmap curPage = Bitmap.createBitmap(mContainer.getDrawingCache());
        mContainer.setDrawingCacheEnabled(false);
        
        mQuickAdapter.notifyChangeRightView();
        mQuickAdapter.notifyChangeLeftView();
        
        mContainer.setDrawingCacheEnabled(true);
        Bitmap nextPage = Bitmap.createBitmap(mContainer.getDrawingCache());
        mContainer.setDrawingCacheEnabled(false);
        
        mPageWidget.setBitmaps(curPage,nextPage);
        
        mPageWidget.doTouchEvent(MotionEvent.obtain(0, 0, 0, 423, 133, 0));
        mPageWidget.doTouchEvent(MotionEvent.obtain(0, 0, 1, 423, 133, 0));
    }
}
