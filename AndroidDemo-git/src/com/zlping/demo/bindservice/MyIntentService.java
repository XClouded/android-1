package com.zlping.demo.bindservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }
    public MyIntentService(String name) {
        super(name);
    }
    
    @Override
    public void onCreate() {
        Log.i("Myservice", "onCreate");
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int i = intent.getIntExtra("value", 0);
        Log.i("Myservice", "onStartCommand value="+i+"|startId="+startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int i = intent.getIntExtra("value", 0);
        Log.i("Myservice", "onHandleIntent value = "+i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        new MyThread().start();
    }
    
    @Override
    public void onDestroy() {
        Log.i("Myservice", "onDestroy");
        super.onDestroy();
    }
    
    class MyThread extends Thread{
        @Override
        public void run() {
            int i = 0 ;
            while(i<20){
                Log.i("DialogActivity", "loop i ="+i);
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        }
    }

}
