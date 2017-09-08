package com.jiakaiyang.xradiogroup.lib.items;

import android.view.SoundEffectConstants;
import android.view.View;

import com.jiakaiyang.xradiogroup.lib.XRadioItem;

/**
 * Created by jia on 2017/9/8.
 * the implement of XRadioItem
 */

public abstract class XRadioItemImpl implements XRadioItem {
    // the real View implement of the XRadioItem
    private View mView;

    private boolean fixed;
    private boolean checked;

    private XRadioItem.OnCheckedChangeListener onCheckedChangeListener;


    public XRadioItemImpl(View mView) {
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
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}
