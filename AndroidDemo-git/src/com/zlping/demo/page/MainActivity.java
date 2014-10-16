package com.zlping.demo.page;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zlping.demo.R;

public class MainActivity extends Activity {
    private PageWidget mPageWidget;
    private View pageLayout;
    private TextView pageNum;
    private FlipPageAnimation mFlipPageAnimation;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_xml);
        pageLayout = findViewById(R.id.page_layout);
        pageNum = (TextView)findViewById(R.id.page_num);
        mPageWidget = (PageWidget)findViewById(R.id.page_widget);
        CopyOfQuickMenuListAdapter adapter = new CopyOfQuickMenuListAdapter(pageNum);
        mFlipPageAnimation = new FlipPageAnimation(this,mPageWidget,pageLayout,adapter);
        
        Button btn = (Button)findViewById(R.id.page_btn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlipPageAnimation.startFlipPage();
                Log.i("MainActivity", "page_btn nextPage ");
            }
        });
    }
    
}