package com.zlping.demo;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ScrollButtomActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scroll_view);
		Gallery gallery = (Gallery) this.findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private Integer[] mImageIds = { R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher };

		public ImageAdapter(Context context) {
			mContext = context;
		}

		@Override
		public int getCount() {
			return mImageIds.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);
			i.setImageResource(mImageIds[position]);
//			i.setScaleType(ImageView.ScaleType.FIT_XY);
//			i.setLayoutParams(new Gallery.LayoutParams(300, 400));
			return i;
		}
	}

}
