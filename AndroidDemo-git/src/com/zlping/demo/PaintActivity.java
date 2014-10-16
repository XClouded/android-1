package com.zlping.demo;

import android.app.Activity;
import android.os.Bundle;

import com.zlping.demo.common.PaintView;

public class PaintActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaintView view = new PaintView(this);
        
        view.setBackgroundResource(R.color.white);
        setContentView(view);
    }
    
    
}
