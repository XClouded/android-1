
package com.zlping.demo;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class DeleteAnimSet extends AnimationSet {

    public DeleteAnimSet(boolean shareInterpolator, int toX, int toY) {
        super(shareInterpolator);
        Animation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, toX, Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, toY);
        Animation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        translateAnimation.setDuration(3000);
        addAnimation(translateAnimation);
//        addAnimation(scaleAnimation);
    }

}
