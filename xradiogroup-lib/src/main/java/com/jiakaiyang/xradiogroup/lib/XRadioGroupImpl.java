package com.jiakaiyang.xradiogroup.lib;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jia on 2017/9/7.
 * the impl of XRadioGroup method
 */

public class XRadioGroupImpl implements XRadioGroup {
    // current checkedId
    private int mCheckedId = -1;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    // the real ViewGroup implement of the XRadioGroup
    private ViewGroup mViewGroup;

    public XRadioGroupImpl(ViewGroup mViewGroup) {
        this.mViewGroup = mViewGroup;
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

        XRadioItem xRadioItem = (XRadioItem) mViewGroup.findViewById(checkId);
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

        int childCount = mViewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mViewGroup.getChildAt(i);
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
        View checkedView = mViewGroup.findViewById(viewId);
        if (checkedView != null && checkedView instanceof XRadioItem) {
            ((XRadioItem) checkedView).setChecked(checked);
        }
    }
}
