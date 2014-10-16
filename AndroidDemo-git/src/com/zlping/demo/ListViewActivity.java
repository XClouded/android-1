
package com.zlping.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zlping.demo.myview.NavigateBGScrollView;
import com.zlping.demo.myview.NavigateListBGScrollView;
import com.zlping.demo.pinnedlist.ScrollListViewTitleLayout;
import com.zlping.demo.pinnedlist.ScrollListViewTitleLayout.OnTouchInterceptListener;
import com.zlping.demo.pinnedlist.TitleListView;

public class ListViewActivity extends Activity implements OnClickListener {
    private Animation mAinim;

    private ScrollListViewTitleLayout mTitleLayout;
    
    private ScrollListViewTitleLayout mBtnLayout;

    private TitleListView listView;
    
    private NavigateBGScrollView mScrollView;
    
    private NavigateListBGScrollView mScrollListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAinim = AnimationUtils.loadAnimation(this, R.anim.scale_small);
        setContentView(R.layout.listview_title);
        mTitleLayout = (ScrollListViewTitleLayout) findViewById(R.id.pinned_title_layout);
        mBtnLayout = (ScrollListViewTitleLayout) findViewById(R.id.pinned_btn_layout);
        listView = (TitleListView) this.findViewById(R.id.pinned_listview);
        
        // mTitle.setListView(listView);
        String[] array = new String[] {
                "WebView 测试", "Google Map", "httpclient", "滚动", "bindservice", "TextWatcher", "高亮文字", "图片缩放", "ding", "手势事件", "插件", "程序列表",
                "dialog测试", "分享给第三方", "自由落体", "翻页效果", "仿真翻书", "多个动画", "水波动画", "画图动画"
        };
        ListAdapter adapter = new ListAdapter(this,array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mScrollListView.onListItemClick(view, position, listView,mTitleLayout.getScrollDistance());
            }
        });
        
        OnTouchInterceptListener listener = new OnTouchInterceptListener(listView);
        
        mBtnLayout.initScrollVeiw(listener, findViewById(R.id.list_btn), false);
        mTitleLayout.initScrollVeiw(listener, findViewById(R.id.pinned_title_view), false);
        
        findViewById(R.id.list_btn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mTitleLayout.toggle();
            }
        });

        mTitleLayout.setScrollEanable(true);

        findViewById(R.id.text_btn_1).setOnClickListener(this);
        findViewById(R.id.text_btn_2).setOnClickListener(this);
        findViewById(R.id.text_btn_3).setOnClickListener(this);
        findViewById(R.id.text_btn_4).setOnClickListener(this);
        mScrollListView = (NavigateListBGScrollView)findViewById(R.id.scroll_listview);
        mScrollView = (NavigateBGScrollView)findViewById(R.id.scroll_view);
//        findViewById(R.id.text_btn_4).post(new Runnable() {
//            @Override
//            public void run() {
//                mScrollView.initToView(findViewById(R.id.text_btn_1));
//                mScrollListView.initToView(listView.getChildAt(0), 0, listView);
//            }
//        });
    }
    
    class ListAdapter extends BaseAdapter{
        private String[] mArray;
        private Context mContext;
        public ListAdapter (Context context,String[] array){
            mArray = array;
            mContext = context;
        }
        
        @Override
        public int getCount() {
            return mArray.length;
        }

        @Override
        public Object getItem(int position) {
            return mArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sample_item, null);
            TextView text = (TextView)convertView.findViewById(R.id.list_item_text);
            if(position==2){
                text.setText("123123123213123123123122132312312312312312312312312312312312");
            }else{
                text.setText(mArray[position]);
            }
            
            return convertView;
        }
        
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.text_btn_1:
                mScrollView.onClickView(v);
                break;
            case R.id.text_btn_2:
                mScrollView.onClickView(v);
                break;
            case R.id.text_btn_3:
                mScrollView.onClickView(v);
                break;
            case R.id.text_btn_4:
                mScrollView.onClickView(v);
                break;
        }

    }
}
