package com.kunzisoft.androidclearchroma;

/**
 * Created by Pavel Sikun on 30.03.16.
 */
public enum IndicatorMode {
    DECIMAL(0), HEX(1);

    private int i;

    IndicatorMode(int id) {
        i=id;
    }

    public int getId() {
        return i;
    }

    public static IndicatorMode getIndicatorModeFromId(int id) {
        switch (id) {
            default:
            case 0:
                return DECIMAL;
            case 1:
                return HEX;
        }
    }
}
