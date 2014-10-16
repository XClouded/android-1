package com.zlping.demo.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class PaintView extends View {
    private static final int MOVE_LENGHT = 200;
    private static final float ANGLE_MAX = 176.0f;
    private static final int DURATION = 2000;
    private float angleOffset = 0;
    private Scroller mScroller;
    private boolean isFirst = true;
    private static final Interpolator sInterpolator = new Interpolator() {
        public final float getInterpolation(float input) {
            float f = input - 1.0F;
            return 1.0F + f * (f * (f * (f * f)));
        }
    };
    public PaintView(Context context) {
        super(context);
        this.mScroller = new Scroller(getContext(), sInterpolator);
        mScroller.startScroll(0, 0, MOVE_LENGHT, 0, DURATION);
    }
    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setStrokeWidth(10);
        p.setStyle(Paint.Style.STROKE);
        RectF oval1 = new RectF(300, 20, 500, 220);
        float startAngle = -90-angleOffset;
        canvas.drawArc(oval1, startAngle, angleOffset*2, false, p);
    }
    
    @Override
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int currX = this.mScroller.getCurrX();
            int finalX = this.mScroller.getFinalX();
            transformAngle(currX);
            if (currX == finalX) {
                this.mScroller.abortAnimation();
                if(isFirst){
                    mScroller.startScroll(0, 0, MOVE_LENGHT, 0, DURATION/2); 
                }else{
                    mScroller.startScroll(0, 0, MOVE_LENGHT, 0, DURATION); 
                }
                isFirst = !isFirst;
            }
            // 继续绘制，让Animation结束
            postInvalidate();
        }
    }
    
    private void transformAngle(float currx){
        float x = currx;
        if(!isFirst){
            x = MOVE_LENGHT - currx;
        }
        angleOffset = (float) (x/MOVE_LENGHT*ANGLE_MAX);
    }
}
