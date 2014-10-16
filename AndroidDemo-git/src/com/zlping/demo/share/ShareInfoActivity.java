package com.zlping.demo.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.zlping.demo.R;

public class ShareInfoActivity extends Activity {
    private static final String TAG = "ShareInfoActivity";
    public static final String SINA_URL="https://api.weibo.com/2/";
    private EditText content;
    private String mAccessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_submit);
        Intent it  = getIntent();
        mAccessToken = it.getStringExtra("mAccessToken");
        content = (EditText)findViewById(R.id.share_info);
        Button button = (Button)findViewById(R.id.submit_btn);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = SINA_URL + "statuses/upload.json";
                WeiboParameters params = new WeiboParameters();
                params.add("source", "360420510");
                params.add("status", content.getText().toString());
                params.add("access_token", mAccessToken);
//                String res = Utility.openUrl(ShareInfoActivity.this,url,"POST",params,null,mAccessToken);
                String res = WeiboUtils.sendWeibo(ShareInfoActivity.this,url,"POST",params,null,mAccessToken);
//                String res = WeiboUtils.testRequest();
                Log.i(TAG, "RES="+res);
            }
        });
            
    }
}
