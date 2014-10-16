package com.zlping.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

public class TextWatcherActivity extends Activity {
	private ListView listEmail;
	private String[] str = new String[] { "163.com", "123.com", "gmail.com",
			"name.com" };
	private List<String> strList;
	private ArrayAdapter<String> adapter;
	private EditText edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_watcher);
		edit = (EditText) this.findViewById(R.id.editTextWatcher);
		listEmail = (ListView) this.findViewById(R.id.listEmail);
		listEmail.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = strList.get(position);
				edit.setText(text);
				edit.setSelection(text.length());
				adapter.clear();
				adapter.notifyDataSetChanged();
			}
		});
		adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1);
		listEmail.setAdapter(adapter);
		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.i("TextWatcherActivity", "onTextChanged S=" + s + "|start="
						+ start + "|before=" + before + "|count=" + count);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				Log.i("TextWatcherActivity", "beforeTextChanged S=" + s
						+ "|start=" + start + "|after=" + after + "|count="
						+ count);

			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.i("TextWatcherActivity", "afterTextChanged S=" + s);
				String text = s.toString();
				showEmailHint(text);
			}

			private void showEmailHint(String text) {
				adapter.clear();
				if (text.contains("@")) {
					int start = text.indexOf("@") + 1;
					String sub = text.substring(start, text.length());
					strList = new ArrayList<String>();
					for (int i = 0; i < str.length; i++) {
						if (str[i].indexOf(sub) == 0) {
							String item = text.substring(0, start) + str[i];
							strList.add(item);
							adapter.add(item);
						}
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
		
		NumberPicker mNumberPicker = (NumberPicker) findViewById(R.id.show_num_picker);  
//        mNumberPicker.setFormatter(this);  
//        mNumberPicker.setOnValueChangedListener(this);  
//        mNumberPicker.setOnScrollListener(this);  
        mNumberPicker.setMaxValue(23);  
        mNumberPicker.setMinValue(0);  
        mNumberPicker.setValue(10); 
	}
}
