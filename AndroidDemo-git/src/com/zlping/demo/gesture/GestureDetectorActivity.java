package com.zlping.demo.gesture;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Scroller;

import com.zlping.demo.R;

public class GestureDetectorActivity extends Activity implements OnGestureListener {
    private static String TAG = "GestureDetectorActivity";
    private GestureDetector detector;
    private ImageView drawPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flying);
//        ListView listview = (ListView)this.findViewById(R.id.listview);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1);
//        for(int i=0;i<100;i++){
//            adapter.add("mylist"+i);
//        }
//        listview.setAdapter(adapter);
        
        detector = new GestureDetector(this, this); 
//        drawPic = (ImageView)findViewById(R.id.draw_pic);
//        drawPic.setOnTouchListener(new OnTouchListener() {
//            
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//                return detector.onTouchEvent(event);
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "point count="+event.getPointerCount());
        return detector.onTouchEvent(event);
    }
    
    
    
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
    	Scroller scroller = new Scroller(this);
        Log.d(TAG, "|onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "|onLongPress");
        
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "|onFling");
        return false;
    }
}
