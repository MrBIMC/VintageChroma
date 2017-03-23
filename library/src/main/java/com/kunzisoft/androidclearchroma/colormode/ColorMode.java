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
    RGB(0), HSV(1), ARGB(2), CMYK(3), CMYK255(4), HSL(5);

    private int i;

    ColorMode(int id) {
        i = id;
    }

    public int getId() {
        return i;
    }

    public AbstractColorMode getColorMode() {
        switch (this) {
            case RGB:
            default:
                return new RGB();
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
        }
    }

    public static ColorMode getColorModeFromId(int id) {
        switch (id) {
            case(0):
            default:
                return RGB;
            case(1):
                return HSV;
            case(2):
                return ARGB;
            case(3):
                return CMYK;
            case(4):
                return CMYK255;
            case(5):
                return HSL;
        }
    }
}
