
package com.zlping.demo.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MyImageView extends ImageView {
    private static final String TAG = "MyImageView";

    private GestureDetector detector;

    private int screenWidth;

    private int screenHeight;

    private int mLeft, mTop, mBottom, mRight;
    private AnimationSet animSet;
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels-50;
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.d(TAG, "|onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d(TAG, "|onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "|onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                int dx = (int) (e2.getX() - e1.getX());
                int dy = (int) (e2.getY() - e1.getY());
                int left = (int) (getLeft() + dx);
                int top = (int) (getTop() + dy);
                int right = (int) (getRight() + dx);
                int bottom = (int) (getBottom() + dy);
                Log.i(TAG, "onScroll dx=" + dx + "|dy=" + dy + "|right=" + right + "|bottom=" + bottom + "|distanceX=" + distanceX + "|distanceY="
                        + distanceY);
                // 设置不能出界
                if (left < 0) {
                    left = 0;
                    right = left + getWidth();
                }

                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - getWidth();
                }

                if (top < 0) {
                    top = 0;
                    bottom = top + getHeight();
                }

                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - getHeight();
                }

                layout(left, top, right, bottom);

                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "|onLongPress");
                RotateAnimation anim = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setDuration(3000);
                startAnimation(anim);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG, "|onFling velocityX="+velocityX+"|velocityY="+velocityY);
                if(velocityX>400){
                    velocityX = velocityX/5;
                }
                if(velocityY>100){
                    velocityY = velocityY/10;
                }
                Animation tX = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                        Animation.ABSOLUTE, getLeft()+velocityX, Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0);
                tX.setInterpolator(new DecelerateInterpolator());
                tX.setDuration(2000);
                Animation ty = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                        Animation.ABSOLUTE, getTop()+velocityY);
                ty.setInterpolator(new DecelerateInterpolator());
                ty.setDuration(2000);
                animSet.addAnimation(tX);
                animSet.addAnimation(ty);
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                animSet = new AnimationSet(false);
                mLeft = getLeft();
                mTop = getTop();
                mRight = getRight();
                mBottom = getBottom();
                Log.i(TAG, "ACTION_DOWN mLeft="+mLeft+"|mTop="+mTop);
                break;
            case MotionEvent.ACTION_UP:
                int cleft = getLeft();
                int ctop = getTop();
                Log.i(TAG, "ACTION_UP cleft="+cleft+"|ctop="+ctop);
                if(Math.abs(cleft-mLeft)<50&&Math.abs(ctop-mTop)<50){
                    layout(mLeft, mTop, mRight, mBottom);
                }else{
                    Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                            Animation.ABSOLUTE, ctop+screenHeight);
                    translateAnimation.setInterpolator(new AccelerateInterpolator(2));
                    translateAnimation.setDuration(700);
                    animSet.addAnimation(translateAnimation);
                    startAnimation(animSet);
                }
                break;
        }
        detector.onTouchEvent(event);
        return true;
    }

}
