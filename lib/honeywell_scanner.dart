import 'dart:async';
import 'dart:io';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:honeywell_scanner/scanned_data.dart';
import 'package:honeywell_scanner/scanner_callback.dart';

export 'package:honeywell_scanner/scanner_callback.dart';
export 'package:honeywell_scanner/code_format.dart';
export 'package:honeywell_scanner/scanned_data.dart';

class HoneywellScanner {
  static const _METHOD_CHANNEL = "honeywellscanner";
  static const _IS_SUPPORTED = "isSupported";
  static const _IS_STARTED = "isStarted";
  static const _SET_PROPERTIES = "setProperties";
  static const _START_SCANNER = "startScanner";
  static const _RESUME_SCANNER = "resumeScanner";
  static const _PAUSE_SCANNER = "pauseScanner";
  static const _STOP_SCANNER = "stopScanner";
  static const _SOFTWARE_TRIGGER = "softwareTrigger";
  static const _START_SCANNING = "startScanning";
  static const _STOP_SCANNING = "stopScanning";
  static const _ON_DECODED = "onDecoded";
  static const _ON_ERROR = "onError";

  static const MethodChannel _channel = MethodChannel(_METHOD_CHANNEL);
  ScannerCallback? _scannerCallback;
  OnScannerDecodeCallback? _onScannerDecodeCallback;
  OnScannerErrorCallback? _onScannerErrorCallback;

  HoneywellScanner(
      {ScannerCallback? scannerCallback,
      OnScannerDecodeCallback? onScannerDecodeCallback,
      OnScannerErrorCallback? onScannerErrorCallback}) {
    _channel.setMethodCallHandler(_onMethodCall);
    _scannerCallback = scannerCallback;
    _onScannerDecodeCallback = onScannerDecodeCallback;
    _onScannerErrorCallback = onScannerErrorCallback;
  }

  set scannerCallback(ScannerCallback scannerCallback) =>
      _scannerCallback = scannerCallback;

  set onScannerDecodeCallback(OnScannerDecodeCallback value) =>
      _onScannerDecodeCallback = value;

  set onScannerErrorCallback(OnScannerErrorCallback value) =>
      _onScannerErrorCallback = value;

  Future<void> _onMethodCall(MethodCall call) async {
    try {
      switch (call.method) {
        case _ON_DECODED:
          onDecoded(call.arguments);
          break;
        case _ON_ERROR:
          onError(Exception(call.arguments));
          break;
        default:
          print(call.arguments);
      }
    } catch (e) {
      print(e);
    }
  }

  ///Called when decoder has successfully decoded the code
  ///<br>
  ///Note that this method always called on a worker thread
  ///
  ///@param scannedData Encapsulates the result of decoding a barcode within an image
  void onDecoded(Map<dynamic, dynamic>? scannedDataMap) {
    if (scannedDataMap != null) {
      final scannedData = ScannedData.fromMap(scannedDataMap);
      _scannerCallback?.onDecoded(scannedData);
      _onScannerDecodeCallback?.call(scannedData);
    }
  }

  ///Called when error has occurred
  ///<br>
  ///Note that this method always called on a worker thread
  ///
  ///@param error Exception that has been thrown
  void onError(Exception error) {
    _scannerCallback?.onError(error);
    _onScannerErrorCallback?.call(error);
  }

  Future<bool> isSupported() async {
    if (kIsWeb || !Platform.isAndroid) return false;
    return await _channel.invokeMethod<bool>(_IS_SUPPORTED) ?? false;
  }

  Future<bool> isStarted() async {
    if (kIsWeb || !Platform.isAndroid) return false;
    return await _channel.invokeMethod<bool>(_IS_STARTED) ?? false;
  }

  Future<void> setProperties(Map<String, dynamic> mapProperties) {
    return _channel.invokeMethod(_SET_PROPERTIES, mapProperties);
  }

  Future<bool> startScanner() async {
    return await _channel.invokeMethod<bool>(_START_SCANNER) ?? false;
  }

  Future<bool> resumeScanner() async {
    return await _channel.invokeMethod(_RESUME_SCANNER) ?? false;
  }

  Future<bool> pauseScanner() async {
    return await _channel.invokeMethod(_PAUSE_SCANNER) ?? false;
  }

  Future<bool> stopScanner() async {
    return await _channel.invokeMethod(_STOP_SCANNER) ?? false;
  }

  Future<bool> softwareTrigger(bool state) async {
    return await _channel.invokeMethod(_SOFTWARE_TRIGGER, state) ?? false;
  }

  Future<bool> startScanning() async {
    return await _channel.invokeMethod(_START_SCANNING) ?? false;
  }

  Future<bool> stopScanning() async {
    return await _channel.invokeMethod(_STOP_SCANNING) ?? false;
  }
}
