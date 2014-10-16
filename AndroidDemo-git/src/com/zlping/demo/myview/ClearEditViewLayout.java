package com.zlping.demo.myview;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.zlping.demo.R;

public class ClearEditViewLayout extends LinearLayout {
    private ImageView mView;
    public ClearEditViewLayout(Context context) {
        super(context);
    }
    public ClearEditViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = new ImageView(context);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = this.getChildAt(0);
        if(view!=null&&view instanceof TextView){
            final TextView text = (TextView)view;
            LayoutParams textparams = (LayoutParams)text.getLayoutParams();
            textparams.weight=1;
            text.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before,int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null) {
                        if (s.toString().length() > 0) {
                            mView.setVisibility(View.VISIBLE);
                        } else {
                            mView.setVisibility(View.INVISIBLE);
                        }
                    }
                }
                
            });

            mView.setImageResource(R.drawable.marker);
            mView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    text.setText("");
                    v.setVisibility(View.GONE);
                }
            });
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.gravity=Gravity.CENTER_VERTICAL;
            addView(mView,params);
        }
    }
}
