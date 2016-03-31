package com.pavelsikun.vintagechroma.colormode;

import com.pavelsikun.vintagechroma.colormode.mode.ARGB;
import com.pavelsikun.vintagechroma.colormode.mode.AbstractColorMode;
import com.pavelsikun.vintagechroma.colormode.mode.HSV;
import com.pavelsikun.vintagechroma.colormode.mode.RGB;

/**
 * Created by Pavel Sikun on 28.03.16.
 */
public enum ColorMode {
    RGB, HSV, ARGB;

    public AbstractColorMode getColorMode() {
        switch (this) {
            case RGB:
                return new RGB();
            case HSV:
                return new HSV();
            case ARGB:
                return new ARGB();
            default:
                return new RGB();
        }
    }
}
