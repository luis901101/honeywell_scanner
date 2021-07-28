package com.plugin.flutter.honeywell_scanner;

import android.content.Context;
import android.os.Handler;

import java.util.Map;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * HoneywellScannerPlugin
 */
public class HoneywellScannerPlugin implements FlutterPlugin, MethodCallHandler, ScannerCallBack
{
    private static final String _METHOD_CHANNEL = "honeywellscanner";
    private static final String _IS_SUPPORTED = "isSupported";
    private static final String _SET_PROPERTIES = "setProperties";
    private static final String _START_SCANNER = "startScanner";
    private static final String _RESUME_SCANNER = "resumeScanner";
    private static final String _PAUSE_SCANNER = "pauseScanner";
    private static final String _STOP_SCANNER = "stopScanner";
    private static final String _ON_DECODED = "onDecoded";
    private static final String _ON_ERROR = "onError";

    private final Handler handler;
    private MethodChannel channel;
    private HoneywellScanner scanner;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        Context context = flutterPluginBinding.getApplicationContext();
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), _METHOD_CHANNEL);
        channel.setMethodCallHandler(this);
        (scanner = new HoneywellScannerNative(context)).setScannerCallBack(this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        if(channel != null) channel.setMethodCallHandler(null);
    }

    public HoneywellScannerPlugin()
    {
        handler = new Handler();
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
                default:
                    result.notImplemented();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            result.error(e.getMessage(), null, null);
        }
    }


    /**
     * Called when decoder has successfully decoded the code
     * <br>
     * Note that this method always called on a worker thread
     *
     * @param code Encapsulates the result of decoding a barcode within an image
     */
    @Override
    public void onDecoded(final String code)
    {
        handler.post(() -> channel.invokeMethod(_ON_DECODED, code));
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