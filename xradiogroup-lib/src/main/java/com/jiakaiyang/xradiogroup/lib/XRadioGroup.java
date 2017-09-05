package com.jiakaiyang.xradiogroup.lib;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jia on 2017/9/5.
 * The XRadioGroup。extends LinearLayout
 */

public class XRadioGroup extends LinearLayout {
    private static final String TAG = "XRadioGroup";

    // current checkedId
    private int mCheckedId = -1;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public XRadioGroup(Context context) {
        super(context);
    }

    public XRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XRadioGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        return XRadioGroup.class.getName();
    }


    /* public methods */

    /**
     * Just like RadioGroup.check(int checkId)
     *
     * @param checkId
     */
    public void check(int checkId) {
        if (checkId != -1 && (checkId == mCheckedId)) {
            return;
        }

        XRadioItem xRadioItem = (XRadioItem) findViewById(checkId);
        if (xRadioItem == null) {
            return;
        }

        if (xRadioItem.isFixed()) {
            // the fixed item just check itself
            xRadioItem.setChecked(true);
            // TODO: 2017/9/5 add listener of the fixed item
            return;
        }

        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }

        if (checkId != -1) {
            setCheckedStateForView(checkId, true);
        }

        setCheckedId(checkId);
    }

    /**
     * @see #check(int)
     * @see #clearCheck()
     */
    @IdRes
    public int getCheckedRadioButtonId() {
        return mCheckedId;
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
        check(-1);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    /**
     * get current fixed items' id.
     *
     * @return the fixed items' id, if there is no fixed item, this will be null
     */
    public
    @Nullable
    List<Integer> getFixedItemsId() {
        List<Integer> fixedItems = null;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (!(child instanceof XRadioItem)) {
                continue;
            }

            XRadioItem xRadioItem = (XRadioItem) child;
            if (xRadioItem.isFixed()) {
                if (fixedItems == null) {
                    fixedItems = new LinkedList<>();
                }

                fixedItems.add(child.getId());
            }
        }

        return fixedItems;
    }


    /* private methods */
    private void setCheckedId(@IdRes int id) {
        mCheckedId = id;
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
        }
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView != null && checkedView instanceof XRadioItem) {
            ((XRadioItem) checkedView).setChecked(checked);
        }
    }


    /* the public interface */

    public static interface OnCheckedChangeListener {
        public void onCheckedChanged(XRadioGroup group, @IdRes int checkedId);
    }

}
