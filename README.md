# VintageChroma
===
[![Download](https://api.bintray.com/packages/mrbimc/maven/VintageChroma/images/download.svg)](https://bintray.com/mrbimc/maven/VintageChroma/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-VintageChroma-green.svg?style=true)](https://android-arsenal.com/details/1/3352)

A Beautiful Material color picker view for Android.

This project started off as an identical remake of [Chroma by Priyesh Patel](https://github.com/ItsPriyesh/chroma)
but written in Java instead of Kotlin, so it is much more lightweight for using in kotlin-less apps.

Since then this "fork" became a lot more developed(even more, it became the most powerful colorpicker for android!).
It has next distinctive features:

    - works on api-7 and up
    - supports RGB, ARGB, HSV, HSL, CMYK, CMYK255 color modes
    - can indicate current color in either DECIMAL or HEXADECIMAL mode
    - Can be used in standalone Dialog+Callback mode or as custom preference.

Screenshots
--

<img src="https://raw.githubusercontent.com/MrBIMC/VintageChroma/master/art/screen4.png" width="250">
<img src="https://raw.githubusercontent.com/MrBIMC/VintageChroma/master/art/screen5.png" width="250">
<img src="https://raw.githubusercontent.com/MrBIMC/VintageChroma/master/art/screen3.png" width="505">

Download
--------
```
compile 'com.pavelsikun:vintage-chroma:1.4'
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

A. Add Preference to your *.xml preference layout:
``` xml
    <com.pavelsikun.vintagechroma.ChromaPreference
        android:key="hsv" // any key you want
        android:title="HSV sample" // summary will be automatically fetched from the current color
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

A1. Use ChromaPreferenceCompat instead of ChromaPreference
``` xml
    <com.pavelsikun.vintagechroma.ChromaPreferenceCompat
        android:key="hsv" // any key you want
        android:title="HSV sample" // summary will be automatically fetched from the current color
        app:chromaColorMode="HSV" // RGB, ARGB, HSV, HSL, CMYK, CMYK255
        app:chromaIndicatorMode="HEX" // HEX or DECIMAL
        app:chromaInitialColor="@color/colorAccent"/> // default color
```
A2. And then, in your PreferenceFragmentCompat you have to inject SupportFragmentManager manually:
```java
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for(int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            Preference p = getPreferenceScreen().getPreference(i);
            if(p instanceof ChromaPreferenceCompat) {
                ((ChromaPreferenceCompat) p).setSupportFragmentManager(getFragmentManager());
            }
        }

        //... your stuff
    }

```

B. Or you can add stuff dynamically through java:
```java
    ChromaPreferenceCompat pref = new ChromaPreferenceCompat(getActivity());
    pref.setSupportFragmentManager(getSupportFragmentManager); // !! important !!
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

--

Check out the [sample project](sample) for more details.

Check out the [sample-v7 project](sample-api-v7) for more details on how to use it with older androids.

License
-------
    Copyright 2016 Pavel Sikun.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
