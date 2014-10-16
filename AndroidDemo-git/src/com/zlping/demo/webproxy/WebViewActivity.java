package com.zlping.demo.webproxy;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.zlping.demo.HttpClientActivity;
import com.zlping.demo.R;
import com.zlping.demo.ScrollButtomActivity;

public class WebViewActivity extends Activity {
	private Button callJS;
//	private String url = "http://m.webapp.58.com/favorite";
//	private String url="http://webapp.58.com/bj/sale.shtml";
//	private String url="http://10.59.8.48:8080/examples/index.html";
//	private String url="file:///android_asset/index.html";
//	private String url="http://3g.sina.com.cn/";
	private String url="file:///android_asset/help.html";
//	private String url="https://graph.renren.com/oauth/authorize?client_id=d810806923144be6b67cfa204a2dedca&redirect_uri=http://wap.58.com/wap.html&response_type=token&display=touch";
	private WebView web;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.web_view);
//		Intent it = new Intent();
//		it.setAction("com.fanqie.menu.ui.activitys.LaunchActivity");
//		it.setClassName("com.fanqie.menu", "com.fanqie.menu.ui.activitys.LaunchActivity");
//		it.setComponent(new ComponentName("com.fanqie.menu", "com.fanqie.menu.ui.activitys.LaunchActivity")); 
//		startActivity(it);
//		setCookie(url);
		
        Log.i("AndroidDemoActivity", "resume ProxyHostname="+ProxySettings.getProxyHostname(getApplicationContext()));
        ProxySettings.setProxy(getApplicationContext());
        web = (WebView)this.findViewById(R.id.webView);
//        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//		web.setEnabled(true);
		web.getSettings().setJavaScriptEnabled(true);
//		web.setBackgroundColor(0);
//		setCookie(url);
//		setCookie("http://zlping.demo.com/examples/");
//		setCookie(".demo.com");
//		printCookies(url);
		
		
//		WebView web = (WebView)this.findViewById(R.id.webView);
		
//		web.getSettings().setAppCacheEnabled(false);
////		web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//		web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//		web.setEnabled(true);
////		web.enablePlatformNotifications();
//		web.setWebChromeClient(new WebChromeClient(){
//			
//		});
//		web.addJavascriptInterface(new JavaMethod(), "stub");
//		web.setWebViewClient(new WebViewClient(){
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				Log.i("WebViewActivity", "shouldOverrideUrlLoading="+url);
//				return super.shouldOverrideUrlLoading(view, url);
//			}
//			
//			@Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//			    Log.i("WebViewActivity", "onPageStarted="+url);
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//			public void onLoadResource(WebView view, String url) {
//				Log.i("WebViewActivity", "onLoadResource="+url);
//				super.onLoadResource(view, url);
//			}
//
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				Log.i("WebViewActivity", "onPageFinished="+url);
//				super.onPageFinished(view, url);
//			}
//
//			@Override
//			public void onReceivedError(WebView view, int errorCode,
//					String description, String failingUrl) {
//				Log.i("WebViewActivity", "onReceivedError errorCode="+description+"failingUrl = "+failingUrl);
//				super.onReceivedError(view, errorCode, description, failingUrl);
//			}
//
//			@Override
//			public void onReceivedHttpAuthRequest(WebView view,
//					HttpAuthHandler handler, String host, String realm) {
//				Log.i("WebViewActivity", "onReceivedHttpAuthRequest host="+host+"realm = "+realm);
//				super.onReceivedHttpAuthRequest(view, handler, host, realm);
//			}
//			
//		});
		web.loadUrl(url);
////		web.loadUrl("file:///android_asset/index.html");
//		web.loadUrl("http://qaa.shoujibao.net/error/");
		Button refresh = (Button)this.findViewById(R.id.refresh);
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				web.loadUrl(url);
//				web.loadUrl("http://qaa.shoujibao.net/error/");
			}
		});
		
		Button setProxy = (Button)this.findViewById(R.id.setProxy);
		setProxy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				boolean res = ProxySettings.setProxy(getApplicationContext(), "10.0.0.172", 80);
//				Log.i("WebViewActivity", "oncreate setproxy="+res);
//				Log.i("WebViewActivity", "oncreate ProxyHostname="+ProxySettings.getProxyHostname(getApplicationContext()));
				setCookie("58.com");
				setCookie(".58.com");
			}
		});
		
		Button cancelProxy = (Button)this.findViewById(R.id.cancelProxy);
		cancelProxy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				ProxySettings.resetProxy(getApplicationContext());
//				Log.i("WebViewActivity", "cancel proxy");
//				Log.i("WebViewActivity", "oncreate ProxyHostname="+ProxySettings.getProxyHostname(getApplicationContext()));
				
				clearCookie();
			}
		});
		
		Button pCookies = (Button)this.findViewById(R.id.pCookies);
		pCookies.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				printCookies(url);
			}
		});
//		
		callJS = (Button)this.findViewById(R.id.callJS);
		callJS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				long beg = System.currentTimeMillis();
//				web.loadUrl("javascript:callJSMethod();");
//				long end = System.currentTimeMillis();
//				Log.i("java - js time:", ""+(end - beg));
				Intent it = new Intent();
				it.setClass(getApplicationContext(), WebViewActivity2.class);
				startActivity(it);
			}
		});
		
		Log.i("WebViewActivity", "oncreate ProxyHostname="+ProxySettings.getProxyHostname(getApplicationContext()));
	}
	
	class JavaMethod{
		public void javaMethod(){
			Log.i("JavaMethod", "javaMethod()");
			for(int i=0;i<20;i++){
				String ss = "zzzz";
			}
			
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.i("WebViewActivity", "onResume ProxyHostname="+ProxySettings.getProxyHostname(getApplicationContext()));
	}
	
	@Override
	protected void onStop() {
//		ProxyManager.cancelProxy();
		super.onStop();
	}
	
	private void setCookie(String url){
        CookieSyncManager.createInstance(this);  
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = "58cooper=\"userid=6825167838721&username=zlping123&cooperkey=17b1e4f48bae0cbb85717ae424a2f3bc\"; 58passport=\"1336455178105409512283831&1FC200CCC2CA2C7E903947073BC7E29EC\"; PPU=\"UID=6825167838721&PPK=c6a57cc5&PPT=e4e5376d&SK=2A31A22D970634059C24F22468751021A5EDCB9BD18302731&LT=1336455178127&UN=zlping123&LV=e5d60885\"";
        
        cookieManager.setCookie(url, "58cooper=userid=33333333333&username=zlping4444&cooperkey="+url+"; domain=58.com;");
        cookieManager.setCookie(url, "58passport=1336455178105409512283831&1FC200CCC2CA2C7E903947073BC7E29EC");
        cookieManager.setCookie(url, "PPU=UID=6825167838721&PPK=c6a57cc5&PPT=e4e5376d&SK=2A31A22D970634059C24F22468751021A5EDCB9BD18302731&LT=1336455178127&UN=zlping123&LV=e5d60885");
//        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
	}
	private void clearCookie(){
        CookieSyncManager.createInstance(this);  
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
	}
	private void printCookies(String url){
		CookieSyncManager.getInstance().sync();
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(url);
        Log.i("WebViewActivity", "cookies="+cookie);
	}
	
	@Override
	protected void onDestroy() {
		Log.i("sdsaf", "onDestroy....");
		super.onDestroy();
	}
}
