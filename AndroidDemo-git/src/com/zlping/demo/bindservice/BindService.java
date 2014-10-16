package com.zlping.demo.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BindService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		Log.i("BindService", "onBind");
		return null;
	}

	@Override
	public void onCreate() {
		Log.i("BindService", "onCreate");
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i("BindService", "onStart");
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("BindService", "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.i("BindService", "onDestroy");
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i("BindService", "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		Log.i("BindService", "onRebind");
		super.onRebind(intent);
	}
}
