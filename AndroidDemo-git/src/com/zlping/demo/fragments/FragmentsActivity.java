package com.zlping.demo.fragments;

import com.zlping.demo.R;
import com.zlping.demo.R.layout;
import com.zlping.demo.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class FragmentsActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragments_activity);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.fragments_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
