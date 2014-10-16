package com.zlping.demo.page;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.zlping.demo.R;

public class FlipPageActivity  extends Activity{

    private ViewGroup mLeftView;
    private ViewGroup mRightView;
    private ImageView mLeftImage;
    private ImageView mRightImage;
    
    private List<View> mLeftViews = new ArrayList<View>();
    private List<View> mRightViews= new ArrayList<View>();
    private boolean isPage = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flip_page);
        mLeftView = (ViewGroup)findViewById(R.id.quick_menu_left);
        mRightView = (ViewGroup)findViewById(R.id.quick_menu_right);
        mLeftImage = (ImageView)findViewById(R.id.quick_flip_page_left); 
        mRightImage = (ImageView)findViewById(R.id.quick_flip_page_right);
        new QuickMenuTask().execute();
        mRightView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isPage = true;
                new QuickMenuTask().execute();
            }
        });
    }
    
    public class QuickMenuTask extends AsyncTask<Integer, String, Void>{

        @Override
        protected Void doInBackground(Integer... params) {
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {
            ImageView image = new ImageView(FlipPageActivity.this);
            image.setImageResource(R.drawable.meinu);
            mLeftViews.add(image);
            ImageView image1 = new ImageView(FlipPageActivity.this);
            image1.setImageResource(R.drawable.meinu);
            mRightViews.add(image1);
            if(isPage){
                startFlipPage();
            }else{
                updateRightViews();
                updateLeftViews();
            }
        }

    }
    
    private void updateRightViews(){
        mRightView.removeAllViews();
        if(mRightViews!=null&&mRightViews.size()>0){
            for(View view:mRightViews){
                mRightView.addView(view);
            }
        }
        mRightView.postInvalidate();
    }
    
    private void updateLeftViews(){
        mLeftView.removeAllViews();
        if(mLeftViews!=null&&mLeftViews.size()>0){
            for(View view:mLeftViews){
                mLeftView.addView(view);
            }
        }
        mLeftView.postInvalidate();
    }
    
    private void startFlipPage(){
        mRightView.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(mRightView.getDrawingCache());
        mRightView.setDrawingCacheEnabled(false);
        updateRightViews();
        mRightImage.setImageBitmap(b);
        mRightImage.setVisibility(View.VISIBLE);
        applyRotation(0,-90);
    }
    
    
    private void applyRotation(float start, float end) {
        final float centerX = 0;
        final float centerY = mRightImage.getHeight() / 2.0f;
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(900);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new AnimationListener() {
            
            @Override
            public void onAnimationStart(Animation animation) {
//                int rh = mRightView.getHeight();
//                if(mRightImage.getLayoutParams().height<rh){
//                    mRightImage.getLayoutParams().height = rh;
////                    LayoutParams p=mRightImage.getLayoutParams();
////                    p.height = rh;
////                    mRightImage.setLayoutParams(p);
//                }
                
//                Log.i("quickmenuactivity", "rh="+rh+"|height="+mRightImage.getLayoutParams().height+"|getheight="+mRightImage.getHeight());
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                
                mRightImage.setVisibility(View.GONE);
                mLeftView.setDrawingCacheEnabled(true);
                Bitmap b = Bitmap.createBitmap(mLeftView.getDrawingCache());
                mLeftView.setDrawingCacheEnabled(false);
                mLeftImage.setImageBitmap(b);
                mLeftImage.setVisibility(View.VISIBLE);
                
                mRightImage.post(new SwapViews());
            }
        });
        mRightImage.startAnimation(rotation);
    }

    private final class SwapViews implements Runnable {
        public void run() {
            final float centerX = mLeftView.getWidth();
            final float centerY = mLeftView.getHeight() / 2.0f;
            Rotate3dAnimation rotation;
            rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            rotation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    updateLeftViews();
                }
                
                @Override
                public void onAnimationRepeat(Animation animation) {
                    
                }
                
                @Override
                public void onAnimationEnd(Animation animation) {
                    mLeftImage.setVisibility(View.GONE);
                };
            });
            mLeftView.startAnimation(rotation);
        }
    }

}
