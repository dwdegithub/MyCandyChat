package com.candychat.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.candychat.utils.FontsUtils;


/**
 * Created by dw on 2016/9/2.
 */
public class FontTextView extends TextView {

    public FontTextView(Context context) {
        this(context, null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode()){
            return;
        }
        Typeface typeface = FontsUtils.getsInstance().getTypeface();
        if (typeface != null) {
            setTypeface(typeface);
        }
    }
}
