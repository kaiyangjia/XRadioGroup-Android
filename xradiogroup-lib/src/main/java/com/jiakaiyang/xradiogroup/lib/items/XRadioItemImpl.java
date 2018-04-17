package com.jiakaiyang.xradiogroup.lib.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

import com.jiakaiyang.xradiogroup.lib.R;
import com.jiakaiyang.xradiogroup.lib.XRadioItem;

/**
 * Created by jia on 2017/9/8.
 * the implement of XRadioItem
 */

public abstract class XRadioItemImpl implements XRadioItem {
    private static final String TAG = "XRadioItemImpl";

    // the real View implement of the XRadioItem
    private View mView;

    private boolean fixed;
    private boolean checked;
    private boolean syncChildrenCheckState;

    private XRadioItem.OnCheckedChangeListener onCheckedChangeListener;


    public static XRadioItemImpl newInstance(final View view) {
        return new XRadioItemImpl(view) {
            @Override
            public int getId() {
                return view.getId();
            }
        };
    }

    private XRadioItemImpl(View mView) {
        this.mView = mView;
    }


    public boolean performClick(boolean superHandled) {
        toggle();

        if (!superHandled) {
            // View only makes a sound effect if the onClickListener was
            // called, so we'll need to make one here instead.
            mView.playSoundEffect(SoundEffectConstants.CLICK);
        }

        return superHandled;
    }


    /**
     * gat onCreateDrawableState method extraSpace Incremental
     *
     * @return
     */
    public int calExtraSpaceIncremental() {
        if (fixed && checked) {
            return 2;
        }

        if (fixed || checked) {
            return 1;
        }

        return 0;
    }


    public void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.XRadioItem, defStyleAttr, 0);
        setFixed(a.getBoolean(R.styleable.XRadioItem_fixed, false));
        setChecked(a.getBoolean(R.styleable.XRadioItem_checked, false));
        setSyncChildrenCheckState(a.getBoolean(R.styleable.XRadioItem_sync_children_check_state, true));

        a.recycle();
    }

    @Override
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public boolean isFixed() {
        return fixed;
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;

        mView.refreshDrawableState();

        onSetChildrenState();

        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, checked);
        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
/*
        if (isFixed()) {
            // the fixed item will always can change itself state when toggle
            setChecked(!isChecked());
        } else {

            // the not fixed item, when it is checked, the toggle method will not
            // change its state
            if (!isChecked()) {
                setChecked(!isChecked());
            }
        }
*/

        // TODO: 2018/4/17 add fixed item handle
        setChecked(!isChecked());
    }

    @Override
    public boolean isSyncChildrenCheckState() {
        return syncChildrenCheckState;
    }

    @Override
    public void setSyncChildrenCheckState(boolean syncChildrenCheckState) {
        this.syncChildrenCheckState = syncChildrenCheckState;
    }

    @Override
    public void syncChildrenCheckState() {
        if (!(mView instanceof ViewGroup)) {
            // if this item is just A View, return
            return;
        }

        ViewGroup viewGroup = (ViewGroup) mView;

        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = viewGroup.getChildAt(i);
            applyCheckStateForView(child);
        }
    }


    /**
     * set child view state
     */
    protected void onSetChildrenState() {
        if (!(mView instanceof ViewGroup)) {
            return;
        }

        ViewGroup mViewGroup = (ViewGroup) mView;

        int childCount = mViewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mViewGroup.getChildAt(i);

            if (child instanceof Checkable) {
                Checkable radioItem = (Checkable) child;
                radioItem.setChecked(checked);
            }
        }
    }


    private void applyCheckStateForView(View view) {
        Drawable backgroundDrawable = view.getBackground();
        if (backgroundDrawable == null) {
            return;
        }

        if (!(backgroundDrawable instanceof StateListDrawable)) {
            Log.d(TAG, "applyCheckStateForView: backgroundDrawable is not instance of StateListDrawable");
            return;
        }

        StateListDrawable stateListDrawable = (StateListDrawable) backgroundDrawable;
    }
}
