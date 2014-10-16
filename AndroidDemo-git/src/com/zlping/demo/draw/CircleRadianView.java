package com.zlping.demo.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 设大圆半径R，小圆半径r,两小圆心距2w，波峰高h 方程式 (R-(h-r))*(R-(h-r))+w*w=R*R 可计算出R
 * 
 * @author lenovo
 * 
 */
public class CircleRadianView extends View {
	private int startX, startY, controlY;
	private int r = 100;
	private Path path;
	private Paint paint;
	private int maxHeight;// 波峰最高度
	private int maxLenght;// 波长

	public CircleRadianView(Context context) {
		super(context);
		init();
	}

	public CircleRadianView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CircleRadianView(Context context, AttributeSet attrs, int defStyle) {
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

	public void initRadiusParams(int width, int height) {
		maxLenght = width;
		maxHeight = height;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int w = getWidth();
		if (w != 0) {
			startX = w / 2 - maxLenght / 2;
			startY = maxHeight;

		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (maxLenght != 0 && startY != 0) {
			drawCos(canvas);
		}
	}

	/**
	 * 设大圆半径为R，小圆半径r,两小圆心距2w，波峰高h 方程式 (R-(h-r))*(R-(h-r))+w*w=(R+r)*(R+r) 可计算出R
	 * 
	 * @param canvas
	 */
	private void drawCos(Canvas canvas) {
		path.reset();// 重置path
		path.moveTo(0, 0);
		path.lineTo(0, startY);
		int w = maxLenght/2;
		float hh = maxHeight - ((float) controlY / getHeight()) * maxHeight * 2;
		float h = Math.abs(hh);
		float t =h-r;
		float R = Math.abs((t*t+w*w-r*r)/((r+t)*2));//大圆半径
		
		int smallRadianLen = (int)(r*w/(R+r));//小圆上的弧度X轴长
		int bigRadianLen = maxLenght-smallRadianLen*2;//大圆上的弧度X轴长
		
		path.lineTo(startX, startY);
		for(int i = 0;i<=smallRadianLen;i++){
			double y = r-Math.sqrt(r*r-i*i);
			y=y*(hh>0?1:-1);
			path.lineTo(startX+i, (int)(startY-y));
		}
		float offsetY = R - h;
		for(int i=0;i<bigRadianLen;i++){
			double y = Math.sqrt(R*R - (bigRadianLen/2-i)*(bigRadianLen/2-i))-offsetY;
			y=y*(hh>0?1:-1);
			path.lineTo(startX+smallRadianLen+i, (int)(startY-y));
		}
		
		for(int i = 0;i<=smallRadianLen;i++){
			double y = r-Math.sqrt(r*r-(smallRadianLen-i)*(smallRadianLen-i));
			y=y*(hh>0?1:-1);
			path.lineTo(startX+smallRadianLen+bigRadianLen+i, (int)(startY-y));
		}

		path.lineTo(getWidth(), startY);
		path.lineTo(getWidth(), 0);
		path.close();
		canvas.drawPath(path, paint);
	}

	public void setControlY(int y) {
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
