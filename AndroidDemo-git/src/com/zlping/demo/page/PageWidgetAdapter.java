package com.zlping.demo.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlping.demo.R;

public class PageWidgetAdapter extends BaseAdapter {

	private Context mContext;
	private int count;
	private LayoutInflater inflater;
	private Integer[] imgs = { R.drawable.meinu, R.drawable.meinu, R.drawable.meinu,
	        R.drawable.meinu, R.drawable.meinu, R.drawable.meinu};
	
	public PageWidgetAdapter(Context context) {
		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		count = (int) Math.ceil(imgs.length);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imgs[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewGroup layout;
		if(convertView == null) {
			layout = (ViewGroup) inflater.inflate(R.layout.item_layout, null);
		} else {
			layout = (ViewGroup) convertView;
		}
		setViewContent(layout, position);
		
		return layout;
	}
	
	private void setViewContent(ViewGroup group, int position) {
		TextView text = (TextView) group.findViewById(R.id.item_layout_leftText);
		text.setText(String.valueOf(position));
		ImageView image = (ImageView) group.findViewById(R.id.item_layout_leftImage);
		image.setImageResource(imgs[position]);

	}

}
