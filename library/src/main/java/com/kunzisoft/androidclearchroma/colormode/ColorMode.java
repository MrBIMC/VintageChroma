package com.kunzisoft.androidclearchroma.colormode;

import com.kunzisoft.androidclearchroma.colormode.mode.ARGB;
import com.kunzisoft.androidclearchroma.colormode.mode.AbstractColorMode;
import com.kunzisoft.androidclearchroma.colormode.mode.CMYK;
import com.kunzisoft.androidclearchroma.colormode.mode.CMYK255;
import com.kunzisoft.androidclearchroma.colormode.mode.HSL;
import com.kunzisoft.androidclearchroma.colormode.mode.HSV;
import com.kunzisoft.androidclearchroma.colormode.mode.RGB;

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
