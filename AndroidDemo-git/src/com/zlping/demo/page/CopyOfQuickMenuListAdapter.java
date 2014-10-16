
package com.zlping.demo.page;

import android.widget.TextView;

public class CopyOfQuickMenuListAdapter {
    private static final String TAG = "QuickMenuListAdapter";
    private TextView mView;
    private int num=0;
    public CopyOfQuickMenuListAdapter(TextView view) {
        mView=view;
    }


    /**
     * 更新右边View
     */
    public void notifyChangeRightView() {
        num++;
        mView.setText(String.valueOf(num));

    }

    /**
     * 更新左边View
     */
    public void notifyChangeLeftView() {
        
    }

   
}
