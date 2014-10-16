
package com.zlping.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * @Title FreeFallActivity.java
 * @Package com.mobcubes.hai.animation
 * @Description 自由落体运动动画 1. 初始位置界面左上角0，0坐标 2. 动向下运动，加速 3. 到边界弹回，到达高度一半的时候在下落 4.
 *              依次类推
 * @Author haiwang
 * @Date 2011-8-18下午01:39:29
 * @Version 1.0
 */
public class FreeFallActivity extends Activity {

    private final String Tag = "FreeFallActivity";

    private ImageView globe;

    private TranslateAnimation tAnimation;// 位置变化

    private RotateAnimation rAnimation;

    private float fromX = 0;

    private float fromY = 0;

    private float toX = 0;

    private float toY = 0;

    private int width = 0;// 分辨率

    private int height = 0;

    private int count = 6;// 运动周期数

    private int n = 2;// 与count和width得到当前的x坐标

    private int upcount = 1;// 向上的次数

    private boolean down = true;// 是否是下落

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freefall);
        globe = (ImageView) this.findViewById(R.id.free_fall_imageview);
        globe.setBackgroundResource(R.drawable.meinu);
        getWindowWidthAndHeight();
        toX = width / count;
        height = 410;
        toY = height;
        globe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                down = true;
                freefall();
            }
        });
        
    }

    private void freefall() {
        if (down) {// 向下
            Log.i(Tag, "down 从(" + fromX + " , " + fromY + ")到(" + toX + " , " + toY + ")");
            tAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
            tAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            tAnimation.setDuration(1000);
            tAnimation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    // Log.i(Tag, "down-start");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.i(Tag, "down-repeat");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.i(Tag, "down-end");
                    if (n > 8) {
                        Log.i(Tag, "over");
                        return;
                    } else {
                        freefall();
                    }
                }
            });
            rAnimation = new RotateAnimation(0, +360);
            rAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            rAnimation.setDuration(1000);
            globe.startAnimation(rAnimation);
            globe.startAnimation(tAnimation);
            // 设置下次向上的坐标
            down = false;
            fromX = toX;
            fromY = toY;
            // 计算这次向上应达到额坐标
            double a = Math.pow(0.5, upcount);
            toY = toY * (float) (1.0 - a);
            upcount++;
        } else {// 向上
            Log.i(Tag, "up 从(" + fromX + " , " + fromY + ")到(" + toX + " , " + toY + ")");
            tAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
            tAnimation.setInterpolator(new DecelerateInterpolator());
            tAnimation.setDuration(1000);
            tAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.i(Tag, "up-repeat");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.i(Tag, "up-end");
                    if (n > 8) {
                        Log.i(Tag, "over");
                        return;
                    } else {
                        freefall();
                    }
                }
            });
            rAnimation = new RotateAnimation(0, +360);
            rAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            rAnimation.setDuration(1000);
            globe.startAnimation(rAnimation);
            globe.startAnimation(tAnimation);
            down = true;
            // 设置下次向下的坐标
            fromX = toX;
            fromY = toY;
            toY = height;
        }
        toX = width / count * n;
        n++;
    }

    // 得到屏幕的分辨率
    private void getWindowWidthAndHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        Log.i(Tag, "分辨率为" + width + "X" + height);
    }
}
