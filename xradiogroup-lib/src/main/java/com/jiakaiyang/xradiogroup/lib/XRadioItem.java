package com.jiakaiyang.xradiogroup.lib;

import android.widget.Checkable;

/**
 * Created by jia on 2017/9/5.
 * the base interface for the item in XRadioGroup
 */

public interface XRadioItem extends Checkable {

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
}
