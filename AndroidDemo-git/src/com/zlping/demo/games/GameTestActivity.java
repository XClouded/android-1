package com.zlping.demo.games;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.zlping.demo.R;

public class GameTestActivity extends Activity {
	private CardRelativeLayout mLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_test);
		findViewById(R.id.button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});
		mLayout = (CardRelativeLayout) findViewById(R.id.card_layout);
		showCard(6);
	}

	private void showCard(int count) {
		mLayout.removeAllViews();
		for (int i = 0; i < count; i++) {
			ImageView image = new ImageView(this);
			image.setImageResource(R.drawable.card_back);
			image.setScaleType(ScaleType.FIT_XY);
			RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(272, 380);
			mLayout.addView(image, param);
			image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mLayout.removeView(v);
					mLayout.refreshLayout();
				}
			});
		}
		mLayout.initShowLayout();
	}

}
