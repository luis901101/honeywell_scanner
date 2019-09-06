package com.plugin.flutter.honeywell_scanner;

import android.content.Context;
import android.os.Handler;

import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * Created by luis901101 on 05/30/19.
 */

/**
 * HoneywellScannerPlugin
 */
public class HoneywellScannerPlugin implements MethodCallHandler, ScannerCallBack
{
    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar)
    {
        new HoneywellScannerPlugin(registrar);
    }

    private static final String _METHOD_CHANNEL = "honeywellscanner";
    private static final String _SET_PROPERTIES = "setProperties";
    private static final String _START_SCANNER = "startScanner";
    private static final String _RESUME_SCANNER = "resumeScanner";
    private static final String _PAUSE_SCANNER = "pauseScanner";
    private static final String _STOP_SCANNER = "stopScanner";
    private static final String _ON_DECODED = "onDecoded";
    private static final String _ON_ERROR = "onError";

    private Handler handler;
    private MethodChannel channel;
    private HoneywellScanner scanner;

    public HoneywellScannerPlugin(Registrar registrar)
    {
        handler = new Handler();
        Context context = registrar.context();
        channel = new MethodChannel(registrar.messenger(), _METHOD_CHANNEL);
        channel.setMethodCallHandler(this);
        (scanner = new HoneywellScannerNative(context)).setScannerCallBack(this);
    }

    private void scannerNotInitialized(Result result){
        result.error("Scanner has not been initialized.", null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMethodCall(MethodCall call, Result result)
    {
        try
        {
            switch(call.method)
            {
                case _SET_PROPERTIES:
                    if(scanner != null){
                        scanner.setProperties((Map<String, Object>) call.arguments);
                        result.success(true);
                    } else scannerNotInitialized(result);
                    break;

                case _START_SCANNER:
                    if(scanner != null){
                        scanner.startScanner();
                        result.success(true);
                    } else scannerNotInitialized(result);
                    break;
                case _RESUME_SCANNER:
                    if(scanner != null){
                        scanner.resumeScanner();
                        result.success(true);
                    } else scannerNotInitialized(result);
                    break;
                case _PAUSE_SCANNER:
                    if(scanner != null){
                        scanner.pauseScanner();
                        result.success(true);
                    } else scannerNotInitialized(result);
                    break;
                case _STOP_SCANNER:
                    if(scanner != null){
                        scanner.stopScanner();
                        result.success(true);
                    } else scannerNotInitialized(result);
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