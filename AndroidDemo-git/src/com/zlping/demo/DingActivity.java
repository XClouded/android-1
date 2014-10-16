
package com.zlping.demo;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;

import com.zlping.demo.page.Rotate3dAnimation;

public class DingActivity extends Activity {
    private View view ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ding_xml);
        view = findViewById(R.id.text_content);
        findViewById(R.id.button_send).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.VISIBLE);
                applyRotation(0,-180);
                
            }
        });
        
        findViewById(R.id.view_hidden).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });
        EditText textview = (EditText)findViewById(R.id.text_content);
        textview.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
    
    private void applyRotation(float start, float end) {
        final float centerX = view.getWidth()/2.0f;
        final float centerY = view.getHeight() / 2.0f;
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(900);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new AnimationListener() {
            
            @Override
            public void onAnimationStart(Animation animation) {
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                view.post(new Runnable() {
                    
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                });
                
            }
        });
        view.startAnimation(rotation);
    }

}
