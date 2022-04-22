package com.plugin.flutter.honeywell_scanner;

import android.app.Activity;
import android.os.Handler;

/**
 * Created by krrigan on 5/31/19.
 */

public interface ScannerCallBack
{
    /**
     * Called when decoder has successfully decoded the code
     * <br>
     * Note that this method always called on a worker thread
     *
     * @param scannedData Encapsulates the result of decoding a barcode within an image
     * @see Handler
     * @see Activity#runOnUiThread(Runnable)
     */
    void onDecoded(ScannedData scannedData);
    /**
     * Called when error has occurred
     * <br>
     * Note that this method always called on a worker thread
     *
     * @param error Exception that has been thrown
     * @see Handler
     * @see Activity#runOnUiThread(Runnable)
     */
    void onError(Exception error);
}