package com.zlping.demo;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.util.Log;
import android.webkit.WebView;

public class AplicationDemo extends Application {
	public static String CURAPN;
	public static String HOST;
	public static int PORT;
	public static int FRIST = 0;
	public static String sDataDir;
	@Override
	public void onCreate() {
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo info = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//		CURAPN = info.getExtraInfo().toLowerCase();
//		HOST = Proxy.getDefaultHost();
//		PORT = Proxy.getDefaultPort();
		sDataDir = getApplicationInfo().dataDir;
		super.onCreate();
	}
	
}
