package com.zlping.demo.pinnedlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.zlping.demo.R;

public class PinnedListActivity extends Activity implements OnItemClickListener{
    private GroupPinnedListView mExpandableListView;
    private ListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinned_list);
        mExpandableListView = (GroupPinnedListView)findViewById(R.id.expand_list);
//        mExpandableListView.setDividerHeight(0);
//        mExpandableListView.setDivider(null);
//        mExpandableListView.setOnChildClickListener(this);
//        mExpandableListView.setOnGroupClickListener(this);
        List<GroupBean> group  = new ArrayList<GroupBean>();
        List<List<String>> mAllList = new ArrayList<List<String>>();
        for(int i=0;i<5;i++){
            GroupBean bean = new GroupBean();
            group.add(bean);
            List<String> str = new ArrayList<String>();
            for(int j=0;j<5;j++){
                str.add("ssssss"+j);
            }
            mAllList.add(str);
        }
        mAdapter= new ListAdapter(mAllList,group);
        mExpandableListView.setAdapter(mAdapter);
        View view = findViewById(R.id.textview);
        View headerView = getLayoutInflater().inflate(R.layout.app_list_item, mExpandableListView, false);
        mExpandableListView.setPinnedHeaderView(headerView);
        mExpandableListView.setOnItemClickListener(this);
    }
    
    class ListAdapter extends BasePinnedListAdapter<String, GroupBean>{
        public ListAdapter(List<List<String>> list,List<GroupBean> group){
            super(list,group);
        }
        @Override
        public void configurePinnedView(View header, int groupPosition, int alpha) {
            ViewHolder holder = (ViewHolder)header.getTag();
            if(holder==null){
                holder = new ViewHolder();
                holder.textView = (TextView)header.findViewById(R.id.app_name);
                header.setBackgroundColor(getResources().getColor(R.color.white));
            }
            holder.textView.setText("title"+groupPosition);
            
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            ViewHolder holder = null;
            if(convertView==null){
                holder = new ViewHolder();
                if(type==TYPE_TITLE){
                    convertView = getLayoutInflater().inflate(R.layout.app_list_item, parent, false);
                    holder.textView = (TextView)convertView.findViewById(R.id.app_name);
                    convertView.setBackgroundColor(getResources().getColor(R.color.white));
                }else if(type==TYPE_ITEM){
                    convertView = getLayoutInflater().inflate(R.layout.app_list_item, parent, false);
                    holder.textView = (TextView)convertView.findViewById(R.id.app_name);
                }
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            if(type==TYPE_TITLE){
                holder.textView.setText("title"+getGroupPosition(position));
            }else if(type==TYPE_ITEM){
                holder.textView.setText(getItem(position).toString());
            }
            
            return convertView;
        }
        
        public class ViewHolder {
            public TextView textView;
            
            public View view;
        }

    }
    
    class GroupBean{
        private int chlidcount;

        public int getChlidcount() {
            return chlidcount;
        }

        public void setChlidcount(int chlidcount) {
            this.chlidcount = chlidcount;
        }
        
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = mAdapter.getItem(position);
        System.out.print(obj);
    }
}
