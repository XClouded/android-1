package com.zlping.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zlping.demo.util.HttpClientUtils;

public class HttpClientOrUrlConnectionActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http_client);
		Button button1 = (Button) this.findViewById(R.id.button1);
		button1.setText("httpclient");
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doHttpClient();
			}
		});

		Button button2 = (Button) this.findViewById(R.id.button2);
		button2.setText("urlconnection");
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doUrlConnection();
			}
		});
		httpclient = HttpClientUtils.getDefaultClient();
		enableHttpResponseCache();
	}
	DefaultHttpClient httpclient;
	private void doHttpClient(){
		new Thread(){
			public void run() {
				long beg = System.currentTimeMillis();
		        try {
		            String url = "http://fanqie.test.58v5.cn/customerserver/recommend/recommendoptions";
		            HttpPost httpost = new HttpPost(url);
		            HttpResponse response = httpclient.execute(httpost);
					Log.i("HttpClientActivity","Login form get: " + response.getStatusLine());
					String content = inputStream2String(response.getEntity().getContent());
					Log.i("HttpClientActivity","entity: " + content);

		        } catch (Exception e) {
		            Log.e("HttpClientOrUrlConnectionActivity", "get support template error", e);
		        }
		        Log.i("HttpClientActivity","HttpClient time: " + (System.currentTimeMillis() - beg));
			}
		}.start();

		
	}
	
	private void doUrlConnection(){
		new Thread(){
			public void run() {
				long beg = System.currentTimeMillis();
		        try {
		            String myurl = "http://fanqie.test.58v5.cn/customerserver/recommend/recommendoptions";
		            URL url = new URL(myurl);
		            URLConnection conn = url.openConnection();
		            conn.setReadTimeout(10000 /* milliseconds */);
		            conn.setConnectTimeout(15000 /* milliseconds */);
		            conn.setDoInput(true);
		            conn.connect();
		            
					Log.i("HttpClientActivity","entity: " + inputStream2String(conn.getInputStream()));

		        } catch (Exception e) {
		            Log.e("HttpClientOrUrlConnectionActivity", "get support template error", e);
		        }
		        Log.i("HttpClientActivity","UrlConnection time: " + (System.currentTimeMillis() - beg));
			};
		}.start();
	}
	
	private void enableHttpResponseCache() {
	    try {
	        long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
	        File httpCacheDir = new File(getCacheDir(), "http");
	        Class.forName("android.net.http.HttpResponseCache")
	            .getMethod("install", File.class, long.class)
	            .invoke(null, httpCacheDir, httpCacheSize);
	    } catch (Exception httpResponseCacheNotAvailable) {
	    }
	}
	
	private static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}
	
}
