# honeywell_scanner 1.0.0+2

This is a plugin to scan barcodes by using [Honeywell](https://www.honeywellaidc.com/products/barcode-scanners) PDA Android devices.

**Native library version is 1.00.00.0085.**

## Description

Honeywell Mobility SDK for Android provides scanning and printing Java libraries for native Android application development. This revision replaces the previous revision 1.00.00.0034.
Target platform:  Android.
Programming language: Java
License: Free

Supported computers

      Dolphin CT50 Android 4.4 and Android 6.0
      Dolphin CT40 Android 7.1.1
      Dolphin CT60 Android 7.1.1
      Dolphin CN80 Android 7.1.1
      Dolphin 75e Android 4.4 and Android 6.0
      CN51 Android 6.0
      CK75, CN75 and CN75e Android 6.0
      EDA50, EDA50K, EDA70 Android 7.1.1
      EDA51 Android 8.1.0

 Supported printers

      Receipt printers – PR2, PR3, PB21, PB31, PB42, PB51, and 6824
      Label printers - PB22, PB32 and PB50
Supported external scanners

      Ring scanner for Dolphin 75e

## How to use

#### First
```yaml
# Add this line to your flutter project dependencies
honeywell_scanner: ^2.0.0+4
```
and run `flutter pub get` to download the library sources to your pub-cache.

#### Second
Copy the **honeywell** folder which is inside the example code sources at:

`.../your-flutter-sdk/.pub-cache/hosted/pub.dartlang.org/honeywell_scanner-2.0.0+4/example/android/honeywell`

into your android project module which is going to use this plugin. This step is necessary and crucial because the Honeywell Android library is a bundle .aar which has to be referenced as a prject library to allow the plugin to locate the honeywell.aar.


#### Third

Add `tools:replace="android:label"` to your **AndroidManifest.xml**, this is required because the **honeywell.aar** library defines an `android:label="@string/app_name"` which conflicts with your project's label resulting in a *Manifest merger failed* error


#### Four
To use the honeywell_scanner plugin just:

1. Instantiate:
```
HoneywellScanner honeywellScanner = HoneywellScanner();
```

2. Set the ScannerCallBack listener:
```
honeywellScanner.setScannerCallBack(this);
```

3. Override the ScannerCallback methods

```
@override
  void onDecoded(String result) {
    setState(() {
      scannedCode = result;
    });
  }

  @override
  void onError(Exception error) {
    setState(() {
      scannedCode = error.toString();
    });
  }
```

3. Set some properties *(optional)* for instance if you want the scanner only scans 2D codes:
```
List<CodeFormat> codeFormats = codeFormats.addAll(CodeFormatUtils.ALL_2D_FORMATS);
honeywellScanner.setProperties( CodeFormatUtils.getAsPropertiesComplement(codeFormats));
```

4. Start scanner listener, at this point the app will be listening for any scanned code when you press the physical PDA button to scan something:
```
honeywellScanner.startScanner();
```

5. Stop scanner listener, this will release and close the scanner connection:
```
honeywellScanner.stopScanner();
```

6. You can also do a `scanner.pauseScanner()` or `scanner.resumeScanner()` depending on your app life cycle state.

### Note:
it's recommended to check the example code for a better idea of how to work with this plugin.