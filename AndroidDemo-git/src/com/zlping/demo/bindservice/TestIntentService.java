package com.zlping.demo.bindservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestIntentService extends IntentService {
    public TestIntentService(){
        super("TestIntentService");
    }

    public TestIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("TestIntentService", "onHandleIntent");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TestIntentService", "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i("TestIntentService", "onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TestIntentService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TestIntentService", "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("TestIntentService", "onBind");
        return super.onBind(intent);
    }
    
    

}
