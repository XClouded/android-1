package com.zlping.demo;

import android.app.Activity;
import android.app.Dialog;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zlping.demo.bindservice.MyIntentService;
import com.zlping.demo.common.MyDialog;

public class DialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view);
        Button dialogBtn = (Button)findViewById(R.id.dialog);
        dialogBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
//                showDialog(100);
//                new MyThread().start();
                for(int i=1;i<20;i++){
                    Intent it = new Intent();
                    it.setClass(getApplicationContext(), MyIntentService.class);
                    it.putExtra("value", i);
                    startService(it);
                }
            }
        });
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==100){
            return new MyDialog(this);
        }
        return super.onCreateDialog(id);
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
    
    class Myservice extends IntentService{
        public Myservice(String name) {
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
            new MyThread().start();
        }
        
        @Override
        public void onDestroy() {
            Log.i("Myservice", "onCreate");
            super.onDestroy();
        }
        
    }
}
