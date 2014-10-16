
package com.zlping.demo.myview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.zlping.demo.R;

public class MyEditView extends EditText {
    private Drawable clearButton;

    public MyEditView(Context context) {
        super(context);
    }

    public MyEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        clearButton = getContext().getResources().getDrawable(R.drawable.close);
        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    if (s.toString().length() > 0) {
                        showClearButton(clearButton);
                    } else {
                        showClearButton(null);
                    }
                }
            }
        });
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }



    private boolean mIsClearButton = false;
    private int mMoveCount;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int left = getWidth() - getHeight();
        int top = 0;
        int right = getWidth();
        int bottom = getHeight();
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            float x = event.getX();
            float y = event.getY();
            if (x > left && x < right && y > top && y < bottom) {
                mIsClearButton = true;
            }
            break;
        case MotionEvent.ACTION_MOVE:
            this.mMoveCount++;
            if(this.mMoveCount > 10) {
                mIsClearButton = false;
            }
            break;
        case MotionEvent.ACTION_UP:
            mMoveCount = 0;
            if(mIsClearButton) {
                onClearButtonClick();
                mIsClearButton = false;
            }
            break;
        }
        return super.onTouchEvent(event);
    }

    private void onClearButtonClick() {
        this.setText("");
        showClearButton(null);
    }
    private void showClearButton(Drawable icon) {
        if (icon != null) {
            icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        }
        setCompoundDrawables(null, null, icon, null);
    }
}
