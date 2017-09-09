package com.jiakaiyang.xradiogroup.lib.items;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jiakaiyang.xradiogroup.lib.XRadioItem;
import com.jiakaiyang.xradiogroup.lib.utils.LogUtils;

/**
 * Created by jia on 2017/9/9.
 * <p>
 * The FrameLayout implement of XRadioItem
 */

public class XFrameRadioItem extends FrameLayout implements XRadioItem {
    private static final String TAG = "XFrameRadioItem";

    private XRadioItemImpl xRadioItem;

    public XFrameRadioItem(@NonNull Context context) {
        this(context, null, 0);
    }

    public XFrameRadioItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XFrameRadioItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        xRadioItem = XRadioItemImpl.newInstance(this);
        xRadioItem.init(context, attrs, defStyleAttr);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        LogUtils.d(TAG, toString() + ":, onCreateDrawableState call");
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
            LogUtils.w(TAG, "isFixed: xRadioItem is null");
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
            LogUtils.w(TAG, "isChecked: xRadioItem is null");
            return false;
        }
        return xRadioItem.isChecked();
    }

    @Override
    public void toggle() {
        xRadioItem.toggle();
    }

}
