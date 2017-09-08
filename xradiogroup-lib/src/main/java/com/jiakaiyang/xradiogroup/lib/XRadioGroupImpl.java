package com.jiakaiyang.xradiogroup.lib;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.jiakaiyang.xradiogroup.lib.utils.ViewUtils;

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

    private PassThroughHierarchyChangeListener hierarchyChangeListener;
    private ItemCheckChangeListener childCheckChangeListener;

    // the real ViewGroup implement of the XRadioGroup
    private ViewGroup mViewGroup;

    private boolean mProtectFromCheckedChange = false;

    public XRadioGroupImpl(ViewGroup mViewGroup) {
        this.mViewGroup = mViewGroup;

        init();
    }

    private void init() {
        hierarchyChangeListener = new PassThroughHierarchyChangeListener();
        childCheckChangeListener = new ItemCheckChangeListener();
    }

    //
    public void onViewFinishInflate() {
        // checks the appropriate radio button as requested in the XML file
        if (mCheckedId != -1) {
            mProtectFromCheckedChange = true;
            setCheckedStateForView(mCheckedId, true);
            mProtectFromCheckedChange = false;
            setCheckedId(mCheckedId);
        }
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

    @Override
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener listener) {
        this.hierarchyChangeListener.mOnHierarchyChangeListener = listener;
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


    private class ItemCheckChangeListener implements XRadioItem.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(XRadioItem xRadioItem, boolean isChecked) {
            // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return;
            }

            mProtectFromCheckedChange = true;
            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }
            mProtectFromCheckedChange = false;

            int id = xRadioItem.getId();
            setCheckedId(id);
        }
    }

    /**
     * brow form Android SDK
     */
    private class PassThroughHierarchyChangeListener implements
            ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        /**
         * {@inheritDoc}
         */
        public void onChildViewAdded(View parent, View child) {
            if (parent == mViewGroup && child instanceof XRadioItem) {
                int id = child.getId();
                // generates an id if it's missing
                if (id == View.NO_ID) {
                    id = ViewUtils.generateViewId();
                    child.setId(id);
                }
                ((XRadioItem) child).setOnCheckedChangeListener(childCheckChangeListener);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        /**
         * {@inheritDoc}
         */
        public void onChildViewRemoved(View parent, View child) {
            if (parent == mViewGroup && child instanceof XRadioItem) {
                ((XRadioItem) child).setOnCheckedChangeListener(null);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }
}
