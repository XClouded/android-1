package com.zlping.demo.draw;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class QuadToView extends View {

	private Paint paint;
	// 贝赛尔曲线成员变量(起始点，控制（操作点），终止点，3点坐标)
	private int startX, startY, controlX, controlY, endX, endY;
	// Path
	private Path path;
	// 为了不影响主画笔，这里绘制贝赛尔曲线单独用一个新画笔
	private Paint paintQ;
	// 随机库（让贝赛尔曲线更明显）
	private Random random;
	private RectF mRectF;
	private RectF leftOval;

	public QuadToView(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		setFocusable(true);
		// -----------以上是SurfaceView游戏框架
		// 贝赛尔曲线相关初始化
		path = new Path();
		paintQ = new Paint();
		paintQ.setAntiAlias(true);
		paintQ.setStyle(Style.STROKE);
		paintQ.setStrokeWidth(5);
		paintQ.setColor(Color.WHITE);
		random = new Random();

		startX = 20;
		startY = 100;
		controlX = 50;
		controlY = 100;
		endX = 600;
		endY = 100;
		mRectF = new RectF();
		leftOval = new RectF();
	}

	/**
	 * 触屏事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		controlX = (int) event.getX();
		controlY = (int) event.getY();
		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLUE);
		// drawQuad(canvas);
		// drawArc(canvas);
		drawCos(canvas);
	}

	private int maxHeight = 150;//波峰最高度
	private int maxLenght = 500;//波长

	// 画余玄图
	private void drawCos(Canvas canvas) {
		path.reset();// 重置path
		startX = 50;
		startY = 400;
		float offsetY = 0;//Y轴偏移量
		int maxRadius = maxHeight/2;//振幅高度
		float curRadius = maxRadius - (float) controlY / getHeight() * maxRadius * 2;//当前振幅高度
		for (int i = -180; i <= 180; i=i+8) {
			Log.i("QuadToView", "i="+i);
			double angle = Math.toRadians(i);
			double cosResult = Math.cos(angle);
			float x = maxLenght/360*(i + 180) + startX;
			float y = (float) (startY - cosResult * curRadius);
			paint.setColor(Color.RED);
			if (i == -180) {
				offsetY = y - startY;
				path.moveTo(x, startY);
			}
			y = y - offsetY;
			path.lineTo(x, y);
		}
		if(curRadius>0){
			canvas.clipRect(0, 0, getWidth(), startY);
			canvas.clipPath(path, Region.Op.DIFFERENCE);
			canvas.drawPaint(paintQ);
		}else{
			canvas.clipRect(0, 0, getWidth(), startY);
			canvas.clipPath(path, Region.Op.XOR);
			canvas.drawPaint(paintQ);
		}
		
//		canvas.drawLine(0, startY, getWidth(), startY, paintQ);
//		canvas.drawPath(path, paintQ);
	}

	// 贝赛尔曲线
	private void drawQuad(Canvas canvas) {
		path.reset();// 重置path
		// 贝赛尔曲线的起始点
		path.moveTo(startX, startY);
		// 设置贝赛尔曲线的操作点以及终止点
		path.quadTo(controlX, (int) (controlY * 1.8), endX, endY);
		// 绘制贝赛尔曲线（Path）
		canvas.drawPath(path, paintQ);
	}

	// 椭圆
	private void drawArc(Canvas canvas) {
		mRectF.left = 200;
		mRectF.top = 100;
		mRectF.right = getWidth() - 200;
		mRectF.bottom = controlY;

		leftOval.left = 0;
		leftOval.right = 200;
		float h = (mRectF.bottom - mRectF.top) / 4;
		leftOval.top = h + mRectF.top;
		leftOval.bottom = h * 3 + mRectF.top;

		paintQ.setColor(Color.WHITE);
		canvas.drawRect(mRectF, paintQ);
		paintQ.setColor(Color.BLACK);
		canvas.drawArc(mRectF, -60, 300, true, paintQ);// 画弧
		canvas.drawOval(leftOval, paintQ);
	}
}
