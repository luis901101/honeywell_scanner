package com.plugin.flutter.honeywell_scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.util.Map;

/**
 * HoneywellScannerPlugin
 */
public class HoneywellScannerBroadcasts extends HoneywellScanner
{
    static class Constants
    {
        /**
         * Get the result of decoded data
         */
        public static final String ACTION_BARCODE_DATA = "com.flutter.plugin.honeywell.scanner.action.BARCODE_DATA";

        /**
         * Honeywell DataCollection Intent API
         * Claim scanner
         * Permissions:
         * "com.honeywell.decode.permission.DECODE"
         */
        public static final String ACTION_CLAIM_SCANNER = "com.honeywell.aidc.action.ACTION_CLAIM_SCANNER";

        /**
         * Honeywell DataCollection Intent API
         * Release scanner claim
         * Permissions:
         * "com.honeywell.decode.permission.DECODE"
         */
        public static final String ACTION_RELEASE_SCANNER = "com.honeywell.aidc.action.ACTION_RELEASE_SCANNER";

        /**
         * Honeywell DataCollection Intent API
         * Optional. Sets the scanner to claim. If scanner is not available or if extra is not used,
         * DataCollection will choose an available scanner. * Values : String
         * "dcs.scanner.imager" : Uses the internal scanner
         * "dcs.scanner.ring" : Uses the external ring scanner
         */
        public static final String EXTRA_SCANNER = "com.honeywell.aidc.extra.EXTRA_SCANNER";

        public static final String EXTRA_SCANNER_VALUE_IMAGER = "dcs.scanner.imager";

        /**
         * Honeywell DataCollection Intent API
         * Optional. Sets the profile to use. If profile is not available or if extra is not used, * the scanner will use factory default properties (not "DEFAULT" profile properties).
         * Values : String
         */
//        public static final String EXTRA_PROFILE = "com.honeywell.aidc.extra.EXTRA_PROFILE";

        /**
         * Honeywell DataCollection Intent API
         * Optional. Overrides the profile properties (non-persistent) until the next scanner claim. * Values : Bundle
         */
        public static final String EXTRA_PROPERTIES = "com.honeywell.aidc.extra.EXTRA_PROPERTIES";

        public static final String SCANNER_PACKAGE = "com.intermec.datacollectionservice";

        public static final String PROPERTY_DATA_PROCESSOR_DATA_INTENT = "DPR_DATA_INTENT";
        public static final String PROPERTY_DATA_PROCESSOR_DATA_INTENT_ACTION = "DPR_DATA_INTENT_ACTION";
        public static final String PROPERTY_DATA_PROCESSOR_LAUNCH_BROWSER = "DPR_LAUNCH_BROWSER";

        public static final String PROPERTY_AZTEC_ENABLED = "DEC_AZTEC_ENABLED";
        public static final String PROPERTY_CODABAR_ENABLED = "DEC_CODABAR_ENABLED";
        public static final String PROPERTY_CODE_39_ENABLED = "DEC_CODE39_ENABLED";
        public static final String PROPERTY_CODE_93_ENABLED = "DEC_CODE93_ENABLED";
        public static final String PROPERTY_CODE_128_ENABLED = "DEC_CODE128_ENABLED";
        public static final String PROPERTY_DATAMATRIX_ENABLED = "DEC_DATAMATRIX_ENABLED";
        public static final String PROPERTY_EAN_8_ENABLED = "DEC_EAN8_ENABLED";
        public static final String PROPERTY_EAN_13_ENABLED = "DEC_EAN13_ENABLED";
        public static final String PROPERTY_MAXICODE_ENABLED = "DEC_MAXICODE_ENABLED";
        public static final String PROPERTY_PDF_417_ENABLED = "DEC_PDF417_ENABLED";
        public static final String PROPERTY_QR_CODE_ENABLED = "DEC_QR_ENABLED";
        public static final String PROPERTY_RSS_ENABLED = "DEC_RSS_14_ENABLED";
        public static final String PROPERTY_RSS_EXPANDED_ENABLED = "DEC_RSS_EXPANDED_ENABLED";
        public static final String PROPERTY_UPC_A_ENABLE = "DEC_UPCA_ENABLE";
        public static final String PROPERTY_UPC_E_ENABLED = "DEC_UPCE0_ENABLED";
    }

    class ScanBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            try
            {
                switch(intent.getAction())
                {
                    case Constants.ACTION_BARCODE_DATA:
                        /*
                        These extras are available:
                            "version" (int) = Data Intent Api version
                            "aimId" (String) = The AIM Identifier
                            "charset" (String) = The charset used to convert "dataBytes" to "data" string "codeId" (String) = The Honeywell Symbology Identifier
                            "data" (String) = The barcode data as a string
                            "dataBytes" (byte[]) = The barcode data as a byte array
                            "timestamp" (String) = The barcode timestamp */
                        int version = intent.getIntExtra("version", 0);
                        if(version >= 1)
                        {
                            String aimId = intent.getStringExtra("aimId");
                            String charset = intent.getStringExtra("charset");
                            String codeId = intent.getStringExtra("codeId");
                            String data = intent.getStringExtra("data");
//                            byte[] dataBytes = intent.getByteArrayExtra("dataBytes");
//                            //                            String dataBytesStr = bytesToHexString(dataBytes);
//                            String timestamp = intent.getStringExtra("timestamp");
                            onDecoded(new ScannedData(data, codeId, aimId, charset));
                        }
                        break;
                    case Constants.ACTION_CLAIM_SCANNER:
                    case Constants.ACTION_RELEASE_SCANNER:
                        break;
                }
            }catch(Exception e)
            {
                onError(e);
            }
        }
    }


    private ScanBroadcastReceiver scanBroadcastReceiver;
    private IntentFilter intentFilter;
    private Bundle properties;

    public HoneywellScannerBroadcasts(Context context)
    {
        super(context);
        init();
    }

    @Override
    public boolean isSupported() { return true; }

    @Override
    public boolean isStarted() { return true; }

    private void initProperties(){
        properties = new Bundle();
        properties.putBoolean(Constants.PROPERTY_AZTEC_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_CODABAR_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_CODE_39_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_CODE_93_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_CODE_128_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_DATAMATRIX_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_EAN_8_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_EAN_13_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_MAXICODE_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_PDF_417_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_QR_CODE_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_RSS_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_RSS_EXPANDED_ENABLED, true);
        properties.putBoolean(Constants.PROPERTY_UPC_A_ENABLE, true);
        properties.putBoolean(Constants.PROPERTY_UPC_E_ENABLED, true);
    }

    @Override
    public void setProperties(Map<String, Object> mapProperties)
    {
        if(mapProperties == null || mapProperties.isEmpty()) return;
        initProperties();

        for(Map.Entry<String, Object> entry : mapProperties.entrySet())
        {
            Object value = entry.getValue();
            if(value == null) continue;
            if(value instanceof String) properties.putString(entry.getKey(), String.valueOf(value));
            if(value instanceof Boolean) properties.putBoolean(entry.getKey(), (Boolean) value);
            if(value instanceof Integer) properties.putInt(entry.getKey(), (Integer) value);
            if(value instanceof Long) properties.putLong(entry.getKey(), (Long) value);
        }
    }

    private void init()
    {
        scanBroadcastReceiver = new ScanBroadcastReceiver();
        intentFilter = new IntentFilter(Constants.ACTION_BARCODE_DATA);
        initProperties();
    }

    @Override
    public boolean resumeScanner()
    {
        startScanner();
        return true;
    }

    @Override
    public boolean pauseScanner()
    {
        stopScanner();
        return true;
    }

    @Override
    public boolean startScanner()
    {
        registerReceiver();
        try
        {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.PROPERTY_DATA_PROCESSOR_DATA_INTENT_ACTION, Constants.ACTION_BARCODE_DATA);
            bundle.putBoolean(Constants.PROPERTY_DATA_PROCESSOR_DATA_INTENT, true);
            bundle.putBoolean(Constants.PROPERTY_DATA_PROCESSOR_LAUNCH_BROWSER, false);
            if(properties != null) bundle.putAll(properties);

            Intent intent = new Intent(Constants.ACTION_CLAIM_SCANNER)
                    .setPackage(Constants.SCANNER_PACKAGE)
                    .putExtra(Constants.EXTRA_SCANNER, Constants.EXTRA_SCANNER_VALUE_IMAGER)
                    .putExtra(Constants.EXTRA_PROPERTIES, bundle);
            context.sendBroadcast(intent);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean stopScanner()
    {
        unregisterReceiver();
        try
        {
            Intent intent = new Intent(Constants.ACTION_RELEASE_SCANNER);
            context.sendBroadcast(intent);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void softwareTrigger(boolean state)
    {

    }

    @Override
    public void startScanning()
    {

    }

    @Override
    public void stopScanning()
    {

    }

    private void registerReceiver()
    {

        try
        {
            context.registerReceiver(scanBroadcastReceiver, intentFilter);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void unregisterReceiver()
    {
        try
        {
            context.unregisterReceiver(scanBroadcastReceiver);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

