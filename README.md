# VintageChroma
A Beautiful Material color picker view for Android.

Identical remake of [Chroma by Priyesh Patel](https://github.com/ItsPriyesh/chroma) in Java instead of Kotlin, 
so it is much more lightweight for using in kotlin-less apps :D

If your app is written in kotlin, I recommend you using original [lib](https://github.com/ItsPriyesh/chroma) instad.

Screenshots
--

<img src="https://raw.githubusercontent.com/MrBIMC/VintageChroma/master/art/screen1.png" width="250">
<img src="https://raw.githubusercontent.com/MrBIMC/VintageChroma/master/art/screen2.png" width="250">
<img src="https://raw.githubusercontent.com/MrBIMC/VintageChroma/master/art/screen3.png" width="500">

Download
--------
```
compile 'com.pavelsikun:vintage-chroma:1.2'
```

Usage as stand-alone dialog && listener:
-----
To display an color picker `DialogFragment`:

``` java
new ChromaDialog.Builder()
    .initialColor(Color.GREEN)
    .colorMode(ColorMode.ARGB) // RGB, ARGB, HVS
    .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that ColorMode.HSV && IndicatorMode.HEX is a bad idea
    .onColorSelected(color -> /* do your stuff */)
    .create()
    .show(getFragmentManager(), "ChromaDialog");
```

Usage as ColorPickerPreference:
-----
You have 2 choices:

1. add Preference to your *.xml preference layout:

``` xml
    <com.pavelsikun.vintagechroma.ChromaPreference
        android:key="hsv" // any key you want
        android:title="HSV sample" // summary will be automatically fetched from the current color
        app:chromaColorMode="HSV" // RGB, ARGB, HSV
        app:chromaIndicatorMode="HEX" // HEX or DECIMAL
        app:chromaInitialColor="@color/colorAccent"/> // default color
```

2. or you can add preferences dynamically from the code:

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

Bonus feature:

- method for formatted output of a given color:
```java
    ChromaUtil.getFormattedColorString(int color, boolean showAlpha);
```


Check out the [sample project](sample) for more details.

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
