package com.jiakaiyang.xradiogroup.lib;

import android.widget.Checkable;

/**
 * Created by jia on 2017/9/5.
 * the base interface for the item in XRadioGroup
 * <p>
 * Your implement of this interface should be the child Class of View
 */

public interface XRadioItem extends Checkable {

    public static final int[] CHECKED_FIXED_STATE_SET = {
            android.R.attr.state_checked,
            R.attr.state_fixed
    };

    public static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public static final int[] FIXED_STATE_SET = {
            R.attr.state_fixed
    };

    /**
     * Set the item's state(Fixed or not)
     * <p>
     * The item fixed will stay the current state till it's not fixed,
     * when other item state changed, the fixed items will not change.
     *
     * @param fixed
     */
    public void setFixed(boolean fixed);

    /**
     * Is fixed
     *
     * @return
     */
    public boolean isFixed();


    /**
     * just like View.getId().
     *
     * @return
     */
    public int getId();


    public void setOnCheckedChangeListener(XRadioItem.OnCheckedChangeListener listener);


    public static interface OnCheckedChangeListener {
        /**
         */
        void onCheckedChanged(XRadioItem xRadioItem, boolean isChecked);
    }
}
