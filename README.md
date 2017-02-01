AndroidClearChroma
===
A customisable material color picker view for Android.

This project is a fork of [VintageChroma by Pavel Sikun](https://github.com/MrBIMC/VintageChroma) no longer maintained.

It has next distinctive features:

    - works on api-7 and up
    - supports RGB, ARGB, HSV, HSL, CMYK, CMYK255 color modes (with alpha preview)
    - can indicate current color in either DECIMAL or HEXADECIMAL mode
    - can be used in standalone Dialog+Callback mode or as custom preference.
    - can select shape for preview color in custom preference
    - add color as part of summary string

Screenshots
--

<img src="https://raw.githubusercontent.com/Kunzisoft/AndroidClearChroma/master/art/screen4.png" width="250">
<img src="https://raw.githubusercontent.com/Kunzisoft/AndroidClearChroma/master/art/screen_add_1.png" width="505">
<img src="https://raw.githubusercontent.com/Kunzisoft/AndroidClearChroma/master/art/screen3.png" width="505">

Download
--------
```
TODO Jitpack in few days
```

Usage as stand-alone dialog && listener(universal guide for all android versions):
-----

To display an color picker `DialogFragment`:
``` java
new ChromaDialog.Builder()
    .initialColor(Color.GREEN)
    .colorMode(ColorMode.ARGB) // RGB, ARGB, HVS, CMYK, CMYK255, HSL
    .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that (HSV || HSL || CMYK) && IndicatorMode.HEX is a bad idea
    .onColorSelected(color -> /* do your stuff */)
    .create()
    .show(getSupportFragmentManager(), "ChromaDialog");
```

Usage as ColorPickerPreference(API-v11+ guide):
-----

<img src="https://raw.githubusercontent.com/J-Jamet/VintageChroma/master/art/screen_add_2.png" width="505">

A. Add Preference to your *.xml preference layout:
``` xml
    <com.pavelsikun.vintagechroma.ChromaPreference
        android:key="hsv" // any key you want
        android:title="HSV sample" // summary will be automatically fetched from the current color
        android:summary="text and [color] string" // add [color] for show current color as string in summary
        app:chromaColorMode="HSV" // RGB, ARGB, HSV, HSL, CMYK, CMYK255
        app:chromaIndicatorMode="HEX" // HEX or DECIMAL
        app:chromaInitialColor="@color/colorAccent"/> // default color
```

B. Or you can add preferences dynamically from the code:
```java
    ChromaPreference pref = new ChromaPreference(getActivity());
    getPreferenceScreen().addPreference(pref);

    //supported additional methods:
    public void setColor(@ColorInt int color);
    public int getColor();

    public void setOnColorSelectedListener(OnColorSelectedListener listener)

    public ColorMode getColorMode()
    public void setColorMode(ColorMode colorMode)

    public IndicatorMode getIndicatorMode()
    public void setIndicatorMode(IndicatorMode indicatorMode)
```

Usage as ColorPickerPreference(API-v7+ guide (use only if you need to support pre-v11 androids)):
-----
Same two choices, though implementation is a bit different since it's built on top of preference-v7:

A. Use ChromaPreferenceCompat instead of ChromaPreference
``` xml
    <com.pavelsikun.vintagechroma.ChromaPreferenceCompat
        android:key="hsv" // any key you want
        android:title="HSV sample" // summary will be automatically fetched from the current color
        android:summary="text and [color] string" // add [color] for show current color as string in summary
        app:chromaColorMode="HSV" // RGB, ARGB, HSV, HSL, CMYK, CMYK255
        app:chromaIndicatorMode="HEX" // HEX or DECIMAL
        app:chromaInitialColor="@color/colorAccent"/> // default color
```

B. Or you can add stuff dynamically through java:
```java
    ChromaPreferenceCompat pref = new ChromaPreferenceCompat(getActivity());
    getPreferenceScreen().addPreference(pref);

    //supported additional methods:
    public void setColor(@ColorInt int color);
    public int getColor();

    public void setOnColorSelectedListener(OnColorSelectedListener listener)

    public ColorMode getColorMode()
    public void setColorMode(ColorMode colorMode)

    public IndicatorMode getIndicatorMode()
    public void setIndicatorMode(IndicatorMode indicatorMode)
```

Bonus feature(universal):
---
method for formatted output of a given color:
```java
ChromaUtil.getFormattedColorString(int color, boolean showAlpha);
```


Check out the [sample project](sample) for more details.

License
-------
    Copyright 2017 JAMET Jeremy.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
