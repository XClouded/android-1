package com.zlping.demo.common;

import com.zlping.demo.R;

import android.app.Dialog;
import android.content.Context;

public class MyDialog extends Dialog {

    public MyDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.ding_xml);
    }

    public MyDialog(Context context){
        super(context, android.R.style.Theme_Dialog);
    }
}
