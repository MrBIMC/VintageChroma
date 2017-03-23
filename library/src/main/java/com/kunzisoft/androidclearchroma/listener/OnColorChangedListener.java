package com.kunzisoft.androidclearchroma.listener;

import android.support.annotation.ColorInt;

/**
 * Callback listener for color changed
 */
public interface OnColorChangedListener {

    /**
     * Called when color has been changed
     * @param color int: The color that was selected in view
     */
    void onColorChanged(@ColorInt int color);
}
