package com.jiakaiyang.xradiogroup.lib.groups;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jiakaiyang.xradiogroup.lib.XRadioGroup;
import com.jiakaiyang.xradiogroup.lib.XRadioItem;

import java.util.List;

/**
 * Created by jia on 2017/9/26.
 * The XRadioGroup extends FrameLayout
 */

public class XFrameRadioGroup extends FrameLayout implements XRadioGroup {
    private static final String TAG = "XFrameRadioGroup";

    private XRadioGroupImpl xRadioGroup;


    public XFrameRadioGroup(@NonNull Context context) {
        this(context, null, 0);
    }

    public XFrameRadioGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XFrameRadioGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof XRadioItem) {
            // TODO: 2017/9/5 add child to the XRadioGroup
        }
        super.addView(child, index, params);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return XLinearRadioGroup.class.getName();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onViewFinishInflate();
    }

    /* public methods */

    /**
     * Just like RadioGroup.check(int checkId)
     *
     * @param checkId
     */
    public void check(int checkId) {
        xRadioGroup.check(checkId);
    }

    /**
     * @see #check(int)
     * @see #clearCheck()
     */
    @IdRes
    public int getCheckedRadioButtonId() {
        return xRadioGroup.getCheckedRadioButtonId();
    }


    /**
     * Just like RadioGroup.clearCheck();
     * <p>
     * this method will not change the fixed items' state.
     *
     * @see #check(int)
     * @see #getCheckedRadioButtonId()
     */
    public void clearCheck() {
        xRadioGroup.clearCheck();
    }

    public void setOnCheckedChangeListener(XRadioGroup.OnCheckedChangeListener mOnCheckedChangeListener) {
        xRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    public void onViewFinishInflate() {
        xRadioGroup.onViewFinishInflate();
    }

    /**
     * get current fixed items' id.
     *
     * @return the fixed items' id, if there is no fixed item, this will be null
     */
    public
    @Nullable
    List<Integer> getFixedItemsId() {
        return xRadioGroup.getFixedItemsId();
    }
}
