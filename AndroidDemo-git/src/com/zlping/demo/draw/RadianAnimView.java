package com.zlping.demo.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class RadianAnimView extends View {
	private int startX, startY, controlY;
	private Path path;
	private Paint paint;
	private int maxHeight;// 波峰最高度
	private int maxLenght;// 波长
	public RadianAnimView(Context context) {
		super(context);
		init();
	}

	public RadianAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RadianAnimView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setFocusable(true);
		path = new Path();
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		controlY = 0;
	}
	
	public void initRadiusParams(int width,int height){
		maxLenght = width;
		maxHeight = height;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int w = getWidth();
		if(w!=0){
			startX = w/2-maxLenght/2;
			startY = maxHeight;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(maxLenght!=0&&startY!=0){
			drawCos(canvas);
		}
	}

	// 画余玄图
	private void drawCos(Canvas canvas) {
		path.reset();// 重置path
		path.moveTo(0, 0);
		path.lineTo(0, startY);
		float offsetY = 0;// Y轴偏移量
		int maxRadius = maxHeight / 2;// 振幅高度
		float curRadius = maxRadius - (float) controlY / getHeight() * maxRadius * 2;// 当前振幅高度
		for (int i = -180; i <= 180; i = i + 8) {
			double angle = Math.toRadians(i);
			double cosResult = Math.cos(angle);
			float x = (float)maxLenght / 360 * (i + 180) + startX;
			float y = (float) (startY - cosResult * curRadius);
			if (i == -180) {
				offsetY = y - startY;
			}
			y = y - offsetY;
			Log.i("RadianAnimView", "startY="+startY+"|i="+i+"|y="+y+"|curRadius="+curRadius+"|controlY="+controlY);
			path.lineTo(x, y);
		}
		path.lineTo(getWidth(), startY);
		path.lineTo(getWidth(), 0);
		path.close();
		canvas.drawPath(path, paint);
	}
	
	public void setControlY(int y){
		controlY = y;
	}
	
	/**
	 * 触屏事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		controlY = (int) event.getY();
		invalidate();
		return true;
	}
	
}
