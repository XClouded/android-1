
package com.zlping.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlping.demo.myview.DrawLinerLayout;

public class HighLightActivity extends Activity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_light);
        TextView text = (TextView)this.findViewById(R.id.text1);
        String strs = "我是有六个字的测试";
        text.setText(highLightString(strs,"六个"));        
        button = (Button)this.findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                button.setTextColor(getResources().getColor(R.color.tran));
                
            }
        });
        
        DrawLinerLayout yesfoodLayout = (DrawLinerLayout) findViewById(R.id.order_tips_yesfood_content);
        addFoodItem(yesfoodLayout,"");
        View view = getLayoutInflater().inflate(R.layout.app_list_item, yesfoodLayout, false);

        yesfoodLayout.setView(view);
        
        TextView text2 = (TextView)findViewById(R.id.hiht_light_text);
        text2.setText("wqerqwrqwerqerqwrqwerqwerwerwqerqwerwerwqerwqerwerqwqwerwerwqerwqerwq");
    }
    private SpannableStringBuilder highLightString(String strs,String sub){
        SpannableStringBuilder style=new SpannableStringBuilder(strs);
        int start = strs.indexOf(sub);
        if(start>=0){
//            style.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.wb_reg_high_alert)),3,4,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
            style.setSpan(new ForegroundColorSpan(R.color.wb_reg_high_alert),start,start+sub.length(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return style;
    }
    
    private void addFoodItem(ViewGroup parent, String tips) {
            for (int i = 0; i < 4; i++) {
                TextView foodItem = new TextView(this);
                foodItem.setTextSize(11);
//                foodItem.setTextColor(getResources().getColor(R.color.order_tips_item_color));
//                foodItem.setText(tip.getName() + "：" + highLightString(tip.getFunction(), tip.getDishesname()));
                foodItem.setText("23232"+highLightString("我就是这样子的", new String[]{"这样子"}));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//                params.topMargin = 5;
                parent.addView(foodItem, params);
            }
    }

    private SpannableStringBuilder highLightString(String strs, String[] sub) {
        SpannableStringBuilder style = new SpannableStringBuilder(strs);
        if (strs != null && sub != null && sub.length > 0) {
            for (int i = 0; i < sub.length; i++) {
                int start = strs.indexOf(sub[i]);
                if (start >= 0) {
                    style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.wb_reg_high_alert)), start, start + sub[i].length(),
                            Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }

        }
        return style;
    }
}
