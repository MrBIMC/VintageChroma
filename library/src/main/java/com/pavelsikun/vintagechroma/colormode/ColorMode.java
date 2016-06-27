package com.pavelsikun.vintagechroma.colormode;

import com.pavelsikun.vintagechroma.colormode.mode.ARGB;
import com.pavelsikun.vintagechroma.colormode.mode.AbstractColorMode;
import com.pavelsikun.vintagechroma.colormode.mode.CMYK;
import com.pavelsikun.vintagechroma.colormode.mode.CMYK255;
import com.pavelsikun.vintagechroma.colormode.mode.HSL;
import com.pavelsikun.vintagechroma.colormode.mode.HSV;
import com.pavelsikun.vintagechroma.colormode.mode.RGB;

/**
 * Created by Pavel Sikun on 28.03.16.
 */
public enum ColorMode {
    RGB, HSV, ARGB, CMYK, CMYK255, HSL;

    public AbstractColorMode getColorMode() {
        switch (this) {
            case HSV:
                return new HSV();
            case ARGB:
                return new ARGB();
            case CMYK:
                return new CMYK();
            case CMYK255:
                return new CMYK255();
            case HSL:
                return new HSL();
            case RGB:
            default:
                return new RGB();
        }
    }
}
