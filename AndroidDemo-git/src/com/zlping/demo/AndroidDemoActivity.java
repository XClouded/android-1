package com.zlping.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zlping.demo.anim.MoreAnimActivity;
import com.zlping.demo.anim.WaterAnimActivity;
import com.zlping.demo.backapp.BackUpAppActivity;
import com.zlping.demo.bindservice.BindServiceActivity;
import com.zlping.demo.draw.CanvasActivity;
import com.zlping.demo.fragments.FragmentsActivity;
import com.zlping.demo.games.GameTestActivity;
import com.zlping.demo.gesture.GestureDetectorActivity;
import com.zlping.demo.opengl.OpenGLActivity;
import com.zlping.demo.page.FlipPageActivity;
import com.zlping.demo.page.MainActivity;
import com.zlping.demo.pinnedlist.PinnedListActivity;
import com.zlping.demo.plugin.MyPluginActivity;
import com.zlping.demo.share.ShareActivity;
import com.zlping.demo.webproxy.WebViewActivity;

public class AndroidDemoActivity extends Activity {
	   /** Called when the activity is first created. */
	WebView web;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i("AndroidDemoActivity", "onCreate .... ");
        setContentView(R.layout.main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        WebView web = (WebView)this.findViewById(R.id.webView);
//        WebView web = new WebView(this);
//        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//		web.setEnabled(true);
//		web.loadUrl("http://qaa.shoujibao.net/error/");
//		ProxySettings.setProxy(getApplicationContext());
//		Log.i("AndroidDemoActivity", "resume ProxyHostname="+ProxySettings.getProxyHostname(getApplicationContext()));
		
        ListView listView = (ListView)this.findViewById(R.id.listView);
        String[] array = new String[]{
        		"WebView 测试","Google Map","httpclient","滚动","bindservice","TextWatcher","高亮文字","图片缩放","ding","手势事件","插件","程序列表"
        		,"dialog测试","分享给第三方","自由落体","翻页效果","仿真翻书","多个动画","水波动画","画圆动画","强制顶端悬浮","opengl","listview顶端悬浮","对比urlconnection"
        		,"fragments","cardGame","EditText换行线","canvas画图曲线","Notification","GridView"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sample_item, array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent();
				if(position==0){
					it.setClass(AndroidDemoActivity.this, WebViewActivity.class);
				}else if(position==1){
					it.setClass(AndroidDemoActivity.this, HttpClientActivity.class);
				}else if(position == 2){
					it.setClass(AndroidDemoActivity.this, HttpClientActivity.class);
				}else if(position == 3){
					it.setClass(AndroidDemoActivity.this, ScrollButtomActivity.class);
				}else if(position == 4){
					it.setClass(AndroidDemoActivity.this, BindServiceActivity.class);
				}else if(position == 5){
					it.setClass(AndroidDemoActivity.this, TextWatcherActivity.class);
				}else if(position == 6){
				    it.setClass(AndroidDemoActivity.this, HighLightActivity.class);
				}else if(position == 7){
				    it.setClass(AndroidDemoActivity.this, ImageMatrixActivity.class);
				}else if(position == 8){
                    it.setClass(AndroidDemoActivity.this, DingActivity.class);
                }else if(position == 9){
                    it.setClass(AndroidDemoActivity.this, GestureDetectorActivity.class);
                }else if(position == 10){
                    it.setClass(AndroidDemoActivity.this, MyPluginActivity.class);
                }else if(position == 11){
                    it.setClass(AndroidDemoActivity.this, BackUpAppActivity.class);
                }else if(position == 12){  
                    it.setClass(AndroidDemoActivity.this, DialogActivity.class);
                }else if(position == 13){  
                    it.setClass(AndroidDemoActivity.this, ShareActivity.class);
                }else if(position == 14){  
                    it.setClass(AndroidDemoActivity.this, FreeFallActivity.class);
                }else if(position == 15){  
                    it.setClass(AndroidDemoActivity.this, FlipPageActivity.class);
                }else if(position == 16){  
                    it.setClass(AndroidDemoActivity.this, MainActivity.class);
                }else if(position == 17){
                    it.setClass(AndroidDemoActivity.this, MoreAnimActivity.class);
                }else if(position == 18){
                    it.setClass(getBaseContext(), WaterAnimActivity.class);
                }else if(position==19){
                    it.setClass(getBaseContext(), PaintActivity.class);
                }else if(position==20){
                    it.setClass(getBaseContext(), PinnedListActivity.class);
                }else if(position==21){
                    it.setClass(getBaseContext(), OpenGLActivity.class);
                }else if(position==22){
                    it.setClass(getBaseContext(), ListViewActivity.class);
                }else if(position==23){
                    it.setClass(getBaseContext(), HttpClientOrUrlConnectionActivity.class);
                }else if(position==24){
                    it.setClass(getBaseContext(), FragmentsActivity.class);
                }else if(position==25){
                    it.setClass(getBaseContext(), GameTestActivity.class);
                }else if(position == 26){
                	it.setClass(getBaseContext(), DividerEditTextActivity.class);
                }else if(position == 27){
                	it.setClass(getBaseContext(), CanvasActivity.class);
                }else if(position == 28){
                	it.setClass(getBaseContext(), NotificationActivity.class);
                }else if(position == 29){
                	it.setClass(getBaseContext(), GridViewActivity.class);
                }
                
				startActivityForResult(it,0);
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.i("AndroidDemoActivity", "onActivityResult");
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    @Override
    protected void onDestroy() {
    	Log.i("AndroidDemoActivity", "onDestroy ... ");
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());// kill process
    }
    
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.i("AndroidDemoActivity", "onUserLeaveHint.....");
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("AndroidDemoActivity", "onSaveInstanceState.....");
    }
}