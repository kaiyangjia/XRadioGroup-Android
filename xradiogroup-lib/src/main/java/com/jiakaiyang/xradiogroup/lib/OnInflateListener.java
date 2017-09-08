package com.jiakaiyang.xradiogroup.lib;

import android.view.View;

/**
 * Created by jia on 2017/9/8.
 * A Inflate listener for a View
 */

public interface OnInflateListener {

    /**
     * when the view inflate finish, just like View.onFinishInflate()
     *
     * @param view
     */
    public void onFinishInflate(View view);
}
