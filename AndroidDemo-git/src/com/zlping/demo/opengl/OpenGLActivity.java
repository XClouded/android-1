package com.zlping.demo.opengl;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlping.demo.R;
import com.zlping.demo.myview.AddViewInLayout;

public class OpenGLActivity extends Activity {
    
    private GLView mGLView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opengl);
        
        mGLView = (GLView)findViewById(R.id.gl_view);
        GLRender render = new GLRender();
        mGLView.setZOrderOnTop(true);
        mGLView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mGLView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        mGLView.setRenderer(render);
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        mGLView.setVisibility(View.GONE);
        
        AddViewInLayout layout = (AddViewInLayout)findViewById(R.id.gl_layout);
        TextView text = new TextView(this);
        text.setText("4444444");
        layout.addView(text, -1, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT), false);
        
        findViewById(R.id.gl_button).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                mGLView.requestRender();
            }
        });
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }
}
