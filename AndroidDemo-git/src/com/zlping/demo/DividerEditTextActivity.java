package com.zlping.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class DividerEditTextActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.divider_edittext);
		EditText text = (EditText)findViewById(R.id.divider_edit);
		text.setText("lksaflkasjflaskjf;askdfj;aslkjdf;aslkjdf;aslkjdf");
	}
}
