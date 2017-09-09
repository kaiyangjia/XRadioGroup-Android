package com.jiakaiyang.xradiogroup.lib.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.view.View;

import com.jiakaiyang.xradiogroup.lib.R;
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
                attrs, R.styleable.XLinearRadioItem, defStyleAttr, 0);
        setFixed(a.getBoolean(R.styleable.XLinearRadioItem_fixed, false));
        setChecked(a.getBoolean(R.styleable.XLinearRadioItem_checked, false));

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
    }
}
