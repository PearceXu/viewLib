package com.xubaipei.viewlib.widget;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by xiuhu on 2016/9/5.
 */
public class ColorfulTextView extends TextView {
    public ColorfulTextView(Context context) {
        super(context);
    }

    public ColorfulTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorfulTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextAndSpan(String text,int color, int start, int end) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan newSpan = new ForegroundColorSpan(color);
        builder.setSpan(newSpan,start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        super.setText(builder);
    }
    /**
     * @function：设置带格式化文本
     * */
    public void setFormatText(Context context,int stringResId,Object... values){
        String text = context.getString(stringResId);
        text = String.format(text,values);
        setText(text);
    }
    /**
     * @function：设置带颜色的文本
     * */
    @SuppressWarnings("all")
    public void setColorString(Context context,int stringResId,int color,String... values){
        String text = context.getString(stringResId);
        Object valuesCopy[] = values;
        text = String.format(text,valuesCopy);
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        for (String value:values) {
            ForegroundColorSpan newSpan = new ForegroundColorSpan(color);
            if (value != null) {
                int start = text.indexOf(value);
                int end = start + (value).length();
                builder.setSpan(newSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else{
                text = text.replace("null","");
                builder = new SpannableStringBuilder(text);
            }
        }
        super.setText(builder);
    }
}