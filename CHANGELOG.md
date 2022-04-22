The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

Types of changes
- `Added` for new features.
- `Changed` for changes in existing functionality.
- `Deprecated` for soon-to-be removed features.
- `Removed` for now removed features.
- `Fixed` for any bug fixes.
- `Security` in case of vulnerabilities.

## 4.0.0+14 (2022-04-21)
### Added
- New function `OnScannerDecodeCallback` available for specific `onDecoded` event callbacks
- New function `OnScannerErrorCallback` available for specific `onError` event callbacks

### Changed
- Breaking change on `ScannerCallBack` class name which was renamed to `ScannerCallback`
- Breaking change in `onDecode` function which now receives a `ScannedData` object with the scanned code itself and other info like the code type, code symbol, etc.

### Removed
- `setScannerCallBack` function removed from `HoneywellScanner`, if you need to set a scanner callback after scanner instantiation you can use the `scannerCallback` set property.

## 3.2.0+13 (2022-03-07)  
### Added
- Scan trigger. Now scanning can be triggered by a button in your app.

### Changed
- Example updated to show how to use scan trigger.
- Readme updated to show how to use scan trigger.

## 3.1.4+12 (2021-10-26)  
### Changed  
- Data collection SDK updated to v1.97.00.0026. *If you were using a previous version of this plugin then you should check the **How to use** from **Readme** and do the **Second** step again to ensure using the new **Honeywell DataCollection.aar** library*

## 3.1.3+11 (2021-10-05)
### Changed
- README.md updated

## 3.1.3+10 (2021-10-05)
### Added
- doc folder added with specific BarCode properties documentation.

### Changed
- Minor changes to example code
- README.md updated

## 3.1.2+9 (2021-09-13)
### Changed
- Android plugin API updated to support v2 Embedding while remaining compatible with apps that don’t use the v2 Android embedding.

## 3.1.1+8 (2021-07-28)
### Fixed
- `Future<bool> isSupported()` validation added to avoid platform plugin exceptions on any other platform different than Android.

## 3.1.0+7 (2021-07-28)
### Added
- `Future<bool> isSupported()` function added to know if running device is supported by honeywell scanner.

## 3.0.1+6 (2021-04-15)
### Changed
- Android gradle plugin updated
- Android compileSdkVersion updated

## 3.0.0+5 (2021-04-15)
### Added
- Support for null-safety

## 2.0.0+4 (2020-10-12)
### Changed
- Honeywell sdk lib AAR updated to v1.00.00.0102
- Example project updated to show a more detailed how to use.
- CodeFormatUtils is now an extension of CodeFormat **(breaking change)**
- Changed the way of importing the honeywell.aar library **(breaking change)**

### Fixed
- Bug on releasing the scanner claim when **stopScanner** was called

### Removed
- Unsupported CodeFormats ITF and UPC_EAN_EXTENSION.

## 1.0.1+3 (2020-10-10)
### Fixed
- Support for latest flutter frameworks

## 1.0.0+2
### Fixed
- Some bugs fixed