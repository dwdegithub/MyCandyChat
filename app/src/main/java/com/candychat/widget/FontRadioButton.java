package com.candychat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.candychat.utils.FontsUtils;


/**
 * Created by dw on 2016/9/2.
 */
public class FontRadioButton extends RadioButton {
    public FontRadioButton(Context context) {
        this(context, null);
    }

    public FontRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode()){
            return;
        }
        setTypeface(FontsUtils.getsInstance().getTypeface());
    }
}
