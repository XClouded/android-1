package com.zlping.demo.draw;

import android.app.Activity;
import android.os.Bundle;

public class CanvasActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		// setContentView(new CanvasView(this));

//		 setContentView(new QuadToView(this));
//		 setContentView(new PathView(this));
//		 RadianAnimView anim = new RadianAnimView(this);
//		 setContentView(anim);
//		 anim.initRadiusParams(300, 100);

		CircleRadianView anim = new CircleRadianView(this);
		setContentView(anim);
		anim.initRadiusParams(400, 90);

	}

}
