package com.zlping.demo.bindservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zlping.demo.R;

public class BindServiceActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_service);
		Button bind = (Button)this.findViewById(R.id.bind);
		bind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(BindServiceActivity.this, BindService.class);
				bindService(it, conn, Context.BIND_AUTO_CREATE);
				
			}
		});
		Button unbind = (Button)this.findViewById(R.id.unbind);
		unbind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				unbindService(conn);
				
			}
		});
		
		Button close = (Button)this.findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		Button start = (Button)this.findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent();
//				it.setClass(getApplicationContext(), BindService.class);
//				it.setClass(BindServiceActivity.this, MyIntentService.class);
				it.setAction("com.zlping.demo.bindservice.TestIntentService");
				startService(it);
				
			}
		});
	}
	
	 private ServiceConnection conn = new ServiceConnection() {
	        
	        @Override
	        public void onServiceDisconnected(ComponentName name) {
	            
	        }
	        
	        @Override
	        public void onServiceConnected(ComponentName name, IBinder service) {

	        }
	    };

	@Override
	protected void onStart() {
		Log.i("BindServiceActivity", "onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.i("BindServiceActivity", "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i("BindServiceActivity", "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i("BindServiceActivity", "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i("BindServiceActivity", "onDestroy");
		super.onDestroy();
	}
	    
	    
}
