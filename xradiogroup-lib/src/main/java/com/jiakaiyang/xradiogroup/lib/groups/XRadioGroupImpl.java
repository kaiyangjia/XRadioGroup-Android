package com.jiakaiyang.xradiogroup.lib.groups;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.jiakaiyang.xradiogroup.lib.XRadioGroup;
import com.jiakaiyang.xradiogroup.lib.XRadioItem;
import com.jiakaiyang.xradiogroup.lib.utils.LogUtils;
import com.jiakaiyang.xradiogroup.lib.utils.ViewUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jia on 2017/9/7.
 * the impl of XRadioGroup method
 */

public class XRadioGroupImpl implements XRadioGroup {
    private static final String TAG = "XRadioGroupImpl";

    // current checkedId
    private int mCheckedId = -1;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    private PassThroughHierarchyChangeListener hierarchyChangeListener;
    private ItemCheckChangeListener childCheckChangeListener;

    // the real ViewGroup implement of the XRadioGroup
    private ViewGroup mViewGroup;

    // now this is no need yet
//    private boolean mProtectFromCheckedChange = false;

    public XRadioGroupImpl(ViewGroup mViewGroup) {
        this.mViewGroup = mViewGroup;

        init();
    }

    private void init() {
        hierarchyChangeListener = new PassThroughHierarchyChangeListener();
        childCheckChangeListener = new ItemCheckChangeListener();

        mViewGroup.setOnHierarchyChangeListener(hierarchyChangeListener);
    }

    //
    public void onViewFinishInflate() {
        // checks the appropriate radio button as requested in the XML file
        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, true);
            setCheckedId(mCheckedId, -3);
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

        int index = findChildIndex((View) xRadioItem, mViewGroup);
        setCheckedId(checkId, index);
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
    private void setCheckedId(@IdRes int id, int childIndex) {
        mCheckedId = id;
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId, childIndex);
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
            int id = xRadioItem.getId();
            LogUtils.d(TAG, "ItemCheckChangeListener onCheckedChanged: id: " + id + ", isChecked: " + isChecked);

            // the fixed item will not change other state
            if (xRadioItem.isFixed()) {
                return;
            }

/*            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }*/
            if (isChecked
                    && mCheckedId != id) {
                int count = mViewGroup.getChildCount();

                for (int i = 0; i < count; i++) {
                    View childView = mViewGroup.getChildAt(i);
                    if (!(childView instanceof XRadioItem)) {
                        continue;
                    }
                    XRadioItem child = (XRadioItem) childView;
                    if (child.getId() == id) {
                        continue;
                    }

                    // the fixed item will not change
                    if (child.isFixed()) {
                        continue;
                    }

                    if (child.isChecked()) {
                        child.setChecked(false);
                    }
                }

                int index = findChildIndex((View) xRadioItem, mViewGroup);
                setCheckedId(id, index);
            }
        }
    }

    private int findChildIndex(View child, ViewGroup viewGroup) {
        if (child == null
                || viewGroup == null) {
            return -1;
        }

        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);

            if (child.equals(view)) {
                return i;
            }
        }

        return -2;
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
                XRadioItem xRadioItem = ((XRadioItem) child);
                xRadioItem.setOnCheckedChangeListener(childCheckChangeListener);
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
