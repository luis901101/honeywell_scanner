# honeywell_scanner 2.0.0+4

This is a plugin to scan barcodes by using [Honeywell](https://www.honeywellaidc.com/products/barcode-scanners) PDA Android devices.

**Native library version is 1.00.00.0102.**

## Description

Supported devices

```
CN51
CK75
CN75
CN75e
CN80
CN85 
Dolphin 75e 
Dolphin CT40 
Dolphin CT50 
Dolphin CT60 
EDA50 
EDA51
EDA50K 
EDA70
Thor VM1A
```
If your device doesn't show up in the list above, give it a try anyway...



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

into your android project module which is going to use this plugin. This step is necessary and crucial because the Honeywell Android library is a bundle .aar which has to be referenced as a project library.


#### Third
Add this `include ':honeywell'` to your `settings.gradle` in your android project module to allow the plugin to locate the honeywell.aar library.


#### Fourth

Add `tools:replace="android:label"` to your **AndroidManifest.xml**, this is required because the **honeywell.aar** library defines an `android:label="@string/app_name"` which conflicts with your project's label resulting in a *Manifest merger failed* error


#### Fifth
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