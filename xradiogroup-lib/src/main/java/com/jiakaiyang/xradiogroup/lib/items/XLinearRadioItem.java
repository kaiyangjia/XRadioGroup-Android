package com.jiakaiyang.xradiogroup.lib.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jiakaiyang.xradiogroup.lib.R;
import com.jiakaiyang.xradiogroup.lib.XRadioItem;
import com.jiakaiyang.xradiogroup.lib.utils.LogUtils;

/**
 * Created by jia on 2017/9/8.
 * <p>
 * The LinearLayout implement of XRadioItem
 */

public class XLinearRadioItem extends LinearLayout implements XRadioItem {
    private static final String TAG = "XLinearRadioItem";

    private static final int[] CHECKED_FIXED_STATE_SET = {
            android.R.attr.state_checked,
            R.attr.state_fixed
    };

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    private static final int[] FIXED_STATE_SET = {
            R.attr.state_fixed
    };

    private XRadioItemImpl xRadioItem;


    public XLinearRadioItem(Context context) {
        this(context, null, 0);
    }

    public XLinearRadioItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XLinearRadioItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public XLinearRadioItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        LogUtils.d(TAG, "init: ");

        xRadioItem = new XRadioItemImpl(this) {
            @Override
            public int getId() {
                return XLinearRadioItem.this.getId();
            }
        };

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.XLinearRadioItem, defStyleAttr, 0);
        xRadioItem.setFixed(a.getBoolean(R.styleable.XLinearRadioItem_fixed, false));
        xRadioItem.setChecked(a.getBoolean(R.styleable.XLinearRadioItem_checked, false));

        a.recycle();
    }


    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }

        // TODO: 2017/9/8 add the fixed state drawable
        return drawableState;
    }

    @Override
    public boolean performClick() {
        final boolean handled = super.performClick();
        return xRadioItem.performClick(handled);
    }

    @Override
    public void setFixed(boolean fixed) {
        xRadioItem.setFixed(fixed);
    }

    @Override
    public boolean isFixed() {
        // this method maybe called before constructor, this mean xRadioItem maybe null.
        if (xRadioItem == null) {
            return false;
        }
        return xRadioItem.isFixed();
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        xRadioItem.setOnCheckedChangeListener(listener);
    }

    @Override
    public void setChecked(boolean checked) {
        xRadioItem.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        // this method maybe called before constructor, this mean xRadioItem maybe null.
        if (xRadioItem == null) {
            return false;
        }
        return xRadioItem.isChecked();
    }

    @Override
    public void toggle() {
        xRadioItem.toggle();
    }
}
