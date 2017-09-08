package com.jiakaiyang.xradiogroup.lib.items;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jiakaiyang.xradiogroup.lib.XRadioItem;

/**
 * Created by jia on 2017/9/8.
 * <p>
 * The LinearLayout implement of XRadioItem
 */

public class XLinearRadioItem extends LinearLayout implements XRadioItem {
    private boolean fixed;
    private boolean checked;

    public XLinearRadioItem(Context context) {
        super(context);
    }

    public XLinearRadioItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XLinearRadioItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public boolean isFixed() {
        return fixed;
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {

    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {

    }
}
