package com.zlping.demo.pinnedlist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

public class TitleListView extends ListView {
	private int mFirstVisibleItem;

	public TitleListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				mFirstVisibleItem = firstVisibleItem;

			}
		});
	}

	public interface ScrollChangedListener {
		public void onScrollChangeds(int l, int t, int oldl, int oldt);
	}
}
