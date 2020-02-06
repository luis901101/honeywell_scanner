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
# add this line to your flutter project dependencies
honeywell_scanner: ^1.0.0+1
```

#### Second
The android project which is going to use this plugin library should define **flatDir** in its gradle allProjects closure, exactly as below:


```android
  flatDir {
      dirs 'libs'
  }
```

#### Third

The honeywell.aar file which is inside the android/libs/ of this plugin should be copied to the android/app/libs/ folder inside your android project sources. This will allow the plugin to locate the honeywell.aar library.
