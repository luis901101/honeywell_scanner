package com.plugin.flutter.honeywell_scanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import java.util.Map;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * HoneywellScannerPlugin
 */
public class HoneywellScannerPlugin implements FlutterPlugin, MethodCallHandler, ScannerCallBack
{
    private static final String _METHOD_CHANNEL = "honeywellscanner";
    private static final String _IS_SUPPORTED = "isSupported";
    private static final String _IS_STARTED = "isStarted";
    private static final String _SET_PROPERTIES = "setProperties";
    private static final String _START_SCANNER = "startScanner";
    private static final String _RESUME_SCANNER = "resumeScanner";
    private static final String _PAUSE_SCANNER = "pauseScanner";
    private static final String _STOP_SCANNER = "stopScanner";
    private static final String _SOFTWARE_TRIGGER = "softwareTrigger";
    private static final String _START_SCANNING = "startScanning";
    private static final String _STOP_SCANNING = "stopScanning";
    private static final String _ON_DECODED = "onDecoded";
    private static final String _ON_ERROR = "onError";

    private final Handler handler;
    private MethodChannel channel;
    private HoneywellScanner scanner;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        init(
            flutterPluginBinding.getApplicationContext(),
            flutterPluginBinding.getBinaryMessenger()
        );
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        if(channel != null) channel.setMethodCallHandler(null);
    }

    // This static method is only to remain compatible with apps that don’t use the v2 Android embedding.
    @Deprecated()
    @SuppressLint("Registrar")
    public static void registerWith(Registrar registrar)
    {
        new HoneywellScannerPlugin().init(
            registrar.context(),
            registrar.messenger()
        );
    }

    public HoneywellScannerPlugin() {
        handler = new Handler();
    }

    private void init(Context context, BinaryMessenger messenger) {
        channel = new MethodChannel(messenger, _METHOD_CHANNEL);
        channel.setMethodCallHandler(this);
        (scanner = new HoneywellScannerNative(context)).setScannerCallBack(this);
    }

    private void scannerNotInitialized(Result result){
        result.error("Scanner has not been initialized.", null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result)
    {
        try
        {
            switch(call.method)
            {
                case _IS_SUPPORTED:
                    if(scanner != null)
                        result.success(scanner.isSupported());
                    else scannerNotInitialized(result);
                    break;
                case _IS_STARTED:
                    if(scanner != null)
                        result.success(scanner.isStarted());
                    else scannerNotInitialized(result);
                    break;
                case _SET_PROPERTIES:
                    if(scanner != null){
                        scanner.setProperties((Map<String, Object>) call.arguments);
                        result.success(true);
                    } else scannerNotInitialized(result);
                    break;

                case _START_SCANNER:
                    if(scanner != null)
                        result.success(scanner.startScanner());
                    else scannerNotInitialized(result);
                    break;
                case _RESUME_SCANNER:
                    if(scanner != null)
                        result.success(scanner.resumeScanner());
                    else scannerNotInitialized(result);
                    break;
                case _PAUSE_SCANNER:
                    if(scanner != null)
                        result.success(scanner.pauseScanner());
                    else scannerNotInitialized(result);
                    break;
                case _STOP_SCANNER:
                    if(scanner != null)
                        result.success(scanner.stopScanner());
                    else scannerNotInitialized(result);
                    break;
                case _SOFTWARE_TRIGGER:
                    if(scanner != null) {
                        if(call.arguments() != null)
                            scanner.softwareTrigger(call.arguments());
                        result.success(true);
                    }
                    else scannerNotInitialized(result);
                    break;
                case _START_SCANNING:
                    if(scanner != null) {
                        scanner.startScanning();
                        result.success(true);
                    }
                    else scannerNotInitialized(result);
                    break;
                case _STOP_SCANNING:
                    if(scanner != null) {
                        scanner.stopScanning();
                        result.success(true);
                    }
                    else scannerNotInitialized(result);
                    break;
                default:
                    result.notImplemented();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            result.error("error", e.getMessage(), e);
        }
    }


    /**
     * Called when decoder has successfully decoded the code
     * <br>
     * Note that this method always called on a worker thread
     *
     * @param scannedData Encapsulates the result of decoding a barcode within an image
     */
    @Override
    public void onDecoded(final ScannedData scannedData)
    {
        handler.post(() -> channel.invokeMethod(_ON_DECODED, scannedData.toMap()));
    }

    /**
     * Called when error has occurred
     * <br>
     * Note that this method always called on a worker thread
     *
     * @param error Exception that has been thrown
     */
    @Override
    public void onError(final Exception error)
    {
        handler.post(() -> channel.invokeMethod(_ON_ERROR, error.getMessage()));
    }
}