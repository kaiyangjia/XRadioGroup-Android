package com.jiakaiyang.xradiogroup.lib.items;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.jiakaiyang.xradiogroup.lib.XRadioItem;
import com.jiakaiyang.xradiogroup.lib.utils.LogUtils;

/**
 * Created by jia on 2017/9/8.
 * <p>
 * The LinearLayout implement of XRadioItem
 */

public class XLinearRadioItem extends LinearLayout implements XRadioItem {
    private static final String TAG = "XLinearRadioItem";

    private XRadioItemImpl xRadioItem;

    public XLinearRadioItem(Context context) {
        this(context, null, 0);
        LogUtils.d(TAG, toString() + ":, constructor1 call");
    }

    public XLinearRadioItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        LogUtils.d(TAG, toString() + ":, constructor2 call");
    }

    public XLinearRadioItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogUtils.d(TAG, toString() + ":, constructor3 call");
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public XLinearRadioItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LogUtils.d(TAG, toString() + ":, constructor4 call");
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

    public boolean isSyncChildrenCheckState() {
        return xRadioItem.isSyncChildrenCheckState();
    }

    public void setSyncChildrenCheckState(boolean syncChildrenCheckState) {
        xRadioItem.setSyncChildrenCheckState(syncChildrenCheckState);
    }

    @Override
    public void syncChildrenCheckState() {
        xRadioItem.syncChildrenCheckState();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Log.i(TAG, "drawableStateChanged: called");
        if (isSyncChildrenCheckState()) {
            syncChildrenCheckState();
        }
    }

    @Override
    public void toggle() {
        xRadioItem.toggle();
    }
}
