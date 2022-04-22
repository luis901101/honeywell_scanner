import 'package:honeywell_scanner/scanned_data.dart';

typedef OnScannerDecodeCallback = Function(ScannedData? scannedData);
typedef OnScannerErrorCallback = Function(Exception error);

/// Callback of the decoding process
abstract class ScannerCallback {
  /// Called when decoder has successfully decoded the code
  ///
  /// @param scannedData Encapsulates the result of decoding a barcode within an image
  void onDecoded(ScannedData? scannedData);

  /// Called when error has occurred
  ///
  /// @param error Exception that has been thrown
  void onError(Exception error);
}
