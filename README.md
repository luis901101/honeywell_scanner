This is a plugin to scan barcodes by using [Honeywell](https://www.honeywellaidc.com/products/barcode-scanners) PDA Android devices.

## About Honeywell SDK
This plugin uses the native Android SDK available in the [Technical Support Downloads Portal](https://hsmftp.honeywell.com/) for honeywell devices, note that you will need to create an account to download any Honeywell software.
From there you will be able to download the **Honeywell_MobilitySDK_Android_vx.xx.zip** which contains the **Android Data Collection SDK** that is the one used in this plugin.
The **Honeywell_MobilitySDK_Android** is located in `Software -> Software and tools -> Developer Library -> SDKs for Android`
Inside the .zip file you will find the **Android Data Collection SDK** that contains the `DataCollection.aar` library with sample code, pdf and html documentation enough to understand and work with the **Android Data Collection SDK** 

### Mobility SDK version: 1.00.00.0135
### Data collection SDK version: 1.97.00.0026

#### **Note: You do not have to do anything from the description above to be able to use this plugin, it's just a guide for the ones that need to know the source of truth.**


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
Dolphin CT40/CT40XP
Dolphin CT50
Dolphin CT60
EDA50
EDA50K
EDA70
Thor VM1A
VM3A
RT10A
CK65
CN80G
CT60XP
```
If your device doesn't show up in the list above, give it a try anyway...


## Migration instructions
From any version prior to ^4.0.0+14 you must:
- Rename any mention of `ScannerCallBack` to `ScannerCallback` in your code. 
- `setScannerCallBack` function is no longer available in `HoneywellScanner`, if you need to set a scanner callback after scanner constructor you can use the `scannerCallback` set property.
- Change the override of the `onDecode` function to receive a `ScannedData` object instead of a `String`.
- `ScannedData` object contains the scanned code and other info related to the scanned code.

## How to use

# First
Run this command:
```bash
flutter pub add honeywell_scanner
```
This will add `honeywell_scanner` to your pubspec.yaml dependencies like:
```yaml
honeywell_scanner: ^latest_version
```
Then run `flutter pub get` to download the library sources to your pub-cache.

# Second
Copy the **honeywell** folder which is inside the example code sources at:

`.../.pub-cache/hosted/pub.dartlang.org/honeywell_scanner-x.x.x+x/example/android/honeywell`

into your android project module which is going to use this plugin. This step is necessary and crucial because the Honeywell Data Collection Android library is a bundle .aar which has to be referenced as a project library.


# Third
Add this `include ':honeywell'` to your `settings.gradle` in your android project module to allow the plugin to locate the honeywell.aar library.


# Fourth

Add `tools:replace="android:label"` under `application` tag in your **AndroidManifest.xml**, this is required because the **honeywell.aar** library defines an `android:label="@string/app_name"` which conflicts with your project's label resulting in a *Manifest merger failed* error.

If error remains, you should check if `xmlns:tools="http://schemas.android.com/tools"` is missing in the manifest block.


# Fifth
To use the honeywell_scanner plugin just:

0. Instantiate:
```dart
HoneywellScanner honeywellScanner = HoneywellScanner();
```
You can also set the onDecode and onError callbacks in the constructor like:
```dart
HoneywellScanner honeywellScanner = HoneywellScanner(scannerCallback: this);
```
which uses an abstract callback implementation. Or you can use function callbacks like:
```dart
HoneywellScanner honeywellScanner = HoneywellScanner(
    onScannerDecodeCallback: (scannedData) {
      // Do something here
    },
    onScannerErrorCallback: (error) {
      // Do something here
    }
  );
```


1. Check if device is supported. Take into account this plugin supports a list of Honeywell devices but not all, so this function ensures you compatibility.
```dart
isDeviceSupported = await honeywellScanner.isSupported();
```

2. You can set the scanner callback listeners after constructor like:
```dart
honeywellScanner.setScannerCallBack(this);
```
Or
```dart
honeywellScanner.onScannerDecodeCallback = (scannedData) {
// Do something here
};
honeywellScanner.onScannerErrorCallback = (error) {
// Do something here
};
```


3. Override the ScannerCallback methods
```dart
@override
void onDecoded(ScannedData? scannedData) {
  setState(() {
    this.scannedData = scannedData;
  });
}

@override
void onError(Exception error) {
  setState(() {
    errorMessage = error.toString();
  });
}
```

4. Setting properties. By default **honeywell_scanner** sets properties to support all code formats from `CodeFormat` enum, it also sets the trigger control property to `autoControl` and disables browser launching when scanning urls.
However you can set any property you want by using the `honeywellScanner.setProperties(properties)` in case you need some specific behavior from the scanner. 
Properties are represented as a `Map<String, dynamic>`, so for instance if you want the scanner only scans 1D codes and you want the scanned **EAN-13** bar codes to include the last digit **(the check digit)** and want the scanned **Codabar** bar codes to include the **start/stop digits**; then you must set it on properties like:
```dart
List<CodeFormat> codeFormats = CodeFormatUtils.ALL_1D_FORMATS;
Map<String, dynamic> properties = {
  ...CodeFormatUtils.getAsPropertiesComplement(codeFormats), //CodeFormatUtils.getAsPropertiesComplement(...) this function converts a list of CodeFormat enums to its corresponding properties representation.
  'DEC_CODABAR_START_STOP_TRANSMIT': true, //This is the Codabar start/stop digit specific property
  'DEC_EAN13_CHECK_DIGIT_TRANSMIT': true, //This is the EAN13 check digit specific property
};
honeywellScanner.setProperties(properties);
```
## **IMPORTANT:** 
To know and understand the full list of supported properties you may check the Honeywell SDK documentation *(to get SDK documentation read the About Honeywell SDK at the beginning of this README)*.
Anyway you can check a quick documentation in the [doc](https://github.com/luis901101/honeywell_scanner/tree/master/doc) folder on this plugin where you can find the [BarcodeReader.html](https://github.com/luis901101/honeywell_scanner/blob/master/doc/BarcodeReader.html) explaining all about barcode reader including properties and the [BarcodeReaderProperties.java](https://github.com/luis901101/honeywell_scanner/blob/master/doc/BarcodeReaderProperties.java) where you can find all the property values you can set. 

5. Start scanner listener, at this point the app will be listening for any scanned code when you press the physical PDA button or your in-app button to scan codes:
```dart
honeywellScanner.startScanner();
```

6. Stop scanner listener, this will release and close the scanner connection:
```dart
honeywellScanner.stopScanner();
```

7. Check if scanner is already started:
```dart
honeywellScanner.isStarted;
```

8. You can also do a `scanner.pauseScanner()` or `scanner.resumeScanner()` depending on your app life cycle state.

9. Start scanning, this function activates the scanner sensor to scan codes. This is the same as pressing the PDA physical button:
```dart
honeywellScanner.startScanning();
```

10. Stop scanning, it cancels the scanning:
```dart
honeywellScanner.stopScanning();
``` 

### Notes:
> - Both `startScaning()` and `stopScanning()` uses the `softwareTrigger(bool state)` function where `state = true` means scanning and `state = false` menas not scanning
> - It's recommended to check the example code for a better idea of how to work with this plugin.

## Other plugins you may be interested in

- [blue_bird_scanner](https://pub.dev/packages/blue_bird_scanner)
- [zkc_scanner](https://pub.dev/packages/zkc_scanner)
