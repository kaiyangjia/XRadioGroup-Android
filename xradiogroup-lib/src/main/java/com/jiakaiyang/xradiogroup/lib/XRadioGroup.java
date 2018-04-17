package com.jiakaiyang.xradiogroup.lib;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jia on 2017/9/7.
 * <p>
 * the interface onf XRadioGroup
 */

public interface XRadioGroup {
    /**
     * Just like RadioGroup.check(int checkId)
     *
     * @param checkId
     */
    public void check(int checkId);

    /**
     * @see #check(int)
     * @see #clearCheck()
     */
    @IdRes
    public int getCheckedRadioButtonId();


    /**
     * Just like RadioGroup.clearCheck();
     * <p>
     * this method will not change the fixed items' state.
     *
     * @see #check(int)
     * @see #getCheckedRadioButtonId()
     */
    public void clearCheck();

    public void setOnCheckedChangeListener(OnCheckedChangeListener mOnCheckedChangeListener);


    /**
     * you should call this method in your XRadioGroup's onFinishInflate()
     */
    public void onViewFinishInflate();


    /**
     * get current fixed items' id.
     *
     * @return the fixed items' id, if there is no fixed item, this will be null
     */
    public
    @Nullable
    List<Integer> getFixedItemsId();


    /**
     */
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener listener);

    /* the public interface */

    public static interface OnCheckedChangeListener {
        public void onCheckedChanged(XRadioGroup group, @IdRes int checkedId, int childIndex);
    }
}
