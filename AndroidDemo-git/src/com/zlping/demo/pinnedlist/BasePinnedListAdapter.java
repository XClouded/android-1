
package com.zlping.demo.pinnedlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.BaseAdapter;

public abstract class BasePinnedListAdapter<T,G> extends BaseAdapter implements GroupPinnedListView.IPinnedListViewAdapter {
    protected static final int TYPE_TITLE = 0;

    protected static final int TYPE_ITEM = 1;

    private List<Object> mAllList = new ArrayList<Object>();

    private List<List<T>> mListGroup;

    private Map<Integer, PositionInfo> mPositionInfos = new HashMap<Integer, PositionInfo>();

    public BasePinnedListAdapter(List<List<T>> list, List<G> group) {
        mListGroup = list;
        if (list != null&&group!=null&&list.size()==group.size()) {
            for (int i = 0; i < list.size(); i++) {
                mAllList.add(group.get(i));
                mAllList.addAll(list.get(i));
            }
        }
    }

    @Override
    public int getCount() {
        return mAllList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAllList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        PositionInfo p = mPositionInfos.get(position);
        if (p == null||position==getCount()) {
            p = new PositionInfo();
            for (int i = 0; i < mListGroup.size(); i++) {
                if (position == 0) {
                    p.type = TYPE_TITLE;
                    p.groupIndex = 0;
                    p.isLastChildren = false;
                    break;
                } else {
                    List<T> childs = mListGroup.get(i);
                    count = count + childs.size() + 1;
                    if (count > position) {// 判断position是否属于这一组
                        p.groupIndex = i;
                        if (position == count - 1) {// 判断position是否本组中的最后一个
                            p.isLastChildren = true;
                        }
                        if (position == count-childs.size()-1) {
                            p.type = TYPE_TITLE;
                        } else {
                            p.type = TYPE_ITEM;
                        }
                        break;
                    }
                }
            }
            mPositionInfos.put(position, p);
        }

        return p.type;
    }

    @Override
    public int getGroupPosition(int position) {
        return mPositionInfos.get(position).groupIndex;
    }

    @Override
    public boolean isLastChildren(int position) {
        return mPositionInfos.get(position).isLastChildren;
    }

    class PositionInfo {
        public int groupIndex = -1;

        public boolean isLastChildren = false;

        public int type = -1;
    }

}
