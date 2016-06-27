package com.pavelsikun.vintagechroma;

/**
 * Created by Pavel Sikun on 01.04.16.
 */
public class ChromaUtil {

    private ChromaUtil() {
    }

    public static String getFormattedColorString(int color, boolean showAlpha) {
        if(showAlpha) {
            return String.format("#%08X", color);
        }
        else {
            return String.format("#%06X", 0xFFFFFF & color);
        }
    }
}
