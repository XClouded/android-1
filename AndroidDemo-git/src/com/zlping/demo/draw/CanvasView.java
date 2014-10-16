
package com.zlping.demo.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.view.View;

public class CanvasView extends View {
    private Paint mPaint;

    private Path mPath;

    public CanvasView(Context context) {
        super(context);
        setFocusable(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(16);
        mPaint.setTextAlign(Paint.Align.RIGHT);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        // canvas.rotate(20f);

        canvas.save();
        canvas.translate(10, 10);
        drawScene(canvas);
        canvas.restore();

        // canvas.save();
        // canvas.translate(100, 10);
        // drawScene(canvas);
        // canvas.restore();

        canvas.save();
        canvas.translate(160, 10);
        canvas.clipRect(10, 10, 90, 90);
        canvas.clipRect(30, 30, 70, 70, Region.Op.DIFFERENCE);
        drawScene(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(10, 160);
        mPath.reset();
        canvas.clipPath(mPath); // makes the clip empty
        mPath.addCircle(50, 50, 50, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        drawScene(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(160, 160);
        canvas.clipRect(0, 0, 60, 60);
        canvas.clipRect(40, 40, 100, 100, Region.Op.UNION);
        drawScene(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(10, 310);
        canvas.clipRect(0, 0, 60, 60);
        canvas.clipRect(40, 40, 100, 100, Region.Op.XOR);
        drawScene(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(160, 310);
        canvas.clipRect(0, 0, 60, 60);
        canvas.clipRect(40, 40, 100, 100, Region.Op.REVERSE_DIFFERENCE);
        drawScene(canvas);
        canvas.restore();
    }

    private void drawScene(Canvas canvas) {
        canvas.clipRect(0, 0, 100, 100);

        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.LTGRAY);
        mPath.reset();
        mPath.moveTo(15, 5);
        mPath.lineTo(80, 10);
        mPath.lineTo(90, 90);
        mPath.lineTo(10, 90);
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(50, 50, 40, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawText("裁剪", 60, 50, mPaint);

    }
}
