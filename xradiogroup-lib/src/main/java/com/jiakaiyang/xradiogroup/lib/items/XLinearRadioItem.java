package com.jiakaiyang.xradiogroup.lib.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.widget.LinearLayout;

import com.jiakaiyang.xradiogroup.lib.R;
import com.jiakaiyang.xradiogroup.lib.XRadioItem;

/**
 * Created by jia on 2017/9/8.
 * <p>
 * The LinearLayout implement of XRadioItem
 */

public class XLinearRadioItem extends LinearLayout implements XRadioItem {
    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };


    private boolean fixed;
    private boolean checked;


    private XRadioItem.OnCheckedChangeListener onCheckedChangeListener;

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

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.XLinearRadioItem, defStyleAttr, 0);
        fixed = a.getBoolean(R.styleable.XLinearRadioItem_fixed, false);
        checked = a.getBoolean(R.styleable.XLinearRadioItem_checked, false);
        a.recycle();
    }


    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public boolean performClick() {
        toggle();

        final boolean handled = super.performClick();
        if (!handled) {
            // View only makes a sound effect if the onClickListener was
            // called, so we'll need to make one here instead.
            playSoundEffect(SoundEffectConstants.CLICK);
        }

        return handled;
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
        this.onCheckedChangeListener = listener;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;

        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}
