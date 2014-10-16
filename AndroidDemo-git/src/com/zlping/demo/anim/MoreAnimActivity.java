
package com.zlping.demo.anim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.zlping.demo.R;
import com.zlping.demo.page.Rotate3dAnimation;

public class MoreAnimActivity extends Activity {
    TextView animText1;
    TextView animText2;
    Animation anim1;
    Animation anim2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_anim);
        button = (Button)findViewById(R.id.anim_start);
        animText1 = (TextView)findViewById(R.id.text_anim1);
        animText2 = (TextView)findViewById(R.id.text_anim2);
        anim1 = AnimationUtils.loadAnimation(this, R.anim.move_right);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.move_bottom);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animText1.startAnimation(anim1);
                animText2.startAnimation(anim2);
                float centerX = button.getWidth() / 2.0f;
                float centerY = button.getHeight() / 2.0f;
                Rotate3dAnimation rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
                rotation.setDuration(5000);
                rotation.setFillAfter(true);
                rotation.setInterpolator(new DecelerateInterpolator());
                button.startAnimation(rotation);
            }
        });
    }
}
