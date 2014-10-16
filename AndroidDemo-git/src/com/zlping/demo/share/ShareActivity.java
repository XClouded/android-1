package com.zlping.demo.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zlping.demo.HttpClientActivity;
import com.zlping.demo.R;

public class ShareActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_info);
        Button sina = (Button)this.findViewById(R.id.sinaBTN);
        sina.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("zlpingdemo", Context.MODE_PRIVATE);
                String sinaToken = settings.getString("sina_access_token", "");
                if(TextUtils.isEmpty(sinaToken)){
                    Intent it = new Intent();
                    it.setClass(getApplicationContext(), OAuthWebViewActivity.class);
                    it.putExtra("type", 1);
                    startActivity(it);
                }else{
                    Intent it = new Intent();
                    it.setClass(getApplicationContext(), ShareInfoActivity.class);
                    it.putExtra("mAccessToken", sinaToken);
                    startActivity(it);
                }
            }
        }); 
        Button qq = (Button)findViewById(R.id.qqBTN);
        qq.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(getBaseContext(), HttpClientActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(it);
//                Intent it = new Intent();
//                it.setClass(getApplicationContext(), OAuthWebViewActivity.class);
//                it.putExtra("type", 2);
//                startActivity(it);
            }
        });
        
        Button renren = (Button)findViewById(R.id.renrenBTN);
        renren.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(getApplicationContext(), OAuthWebViewActivity.class);
                it.putExtra("type", 3);
                startActivity(it);
            }
        });
    }
    
    @Override
    protected void onDestroy() {
    	Log.i("ShareActivity", "onDestroy .... ");
    	super.onDestroy();
    }
}
