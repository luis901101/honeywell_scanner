package com.plugin.flutter.honeywell_scanner;

import com.honeywell.aidc.BarcodeReadEvent;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * BarCode data encapsulation
 */
class ScannedData {
   static Map<String, String> codeSymbolCodeTypeMap;
   static {
      codeSymbolCodeTypeMap = new HashMap<>();
      codeSymbolCodeTypeMap.put("A", "Australian Post");
      codeSymbolCodeTypeMap.put("B", "British Post");
      codeSymbolCodeTypeMap.put("C", "Canadian Post");
      codeSymbolCodeTypeMap.put("D", "EAN-8");
      codeSymbolCodeTypeMap.put("E", "UPC-E");
      codeSymbolCodeTypeMap.put("H", "Chinese Sensible Code (Han Xin Code)");
      codeSymbolCodeTypeMap.put("J", "Japanese Post");
      codeSymbolCodeTypeMap.put("K", "KIX (Netherlands) Post");
      codeSymbolCodeTypeMap.put("L", "Planet Code");
      codeSymbolCodeTypeMap.put("M", "Intelligent Mail Bar Code");
      codeSymbolCodeTypeMap.put("N", "Postal-4i");
      codeSymbolCodeTypeMap.put("P", "Postnet");
      codeSymbolCodeTypeMap.put("Q", "China Post (Hong Kong 2 of 5)");
      codeSymbolCodeTypeMap.put("R", "MicroPDF417");
      codeSymbolCodeTypeMap.put("T", "TCIF Linked code 39 (TLC39)");
      codeSymbolCodeTypeMap.put("V", "Codablock A");
      codeSymbolCodeTypeMap.put("Y", "NEC 2 of 5");
      codeSymbolCodeTypeMap.put("a", "Codabar");
      codeSymbolCodeTypeMap.put("b", "Code 39");
      codeSymbolCodeTypeMap.put("c", "UPC-A");
      codeSymbolCodeTypeMap.put("d", "EAN-13 / Bookland EAN");
      codeSymbolCodeTypeMap.put("e", "Interleaved 2 of 5");
      codeSymbolCodeTypeMap.put("f", "Straight 2 Of 5 IATA / Industrial");
      codeSymbolCodeTypeMap.put("g", "MSI");
      codeSymbolCodeTypeMap.put("h", "Code 11");
      codeSymbolCodeTypeMap.put("i", "Code 93 and 93i");
      codeSymbolCodeTypeMap.put("j", "Code 128");
      codeSymbolCodeTypeMap.put("l", "Code 49");
      codeSymbolCodeTypeMap.put("m", "Matrix 2 of 5");
      codeSymbolCodeTypeMap.put("q", "Codablock F");
      codeSymbolCodeTypeMap.put("r", "PDF417");
      codeSymbolCodeTypeMap.put("s", "QR Code or Micro QR Code");
      codeSymbolCodeTypeMap.put("t", "Telepen");
      codeSymbolCodeTypeMap.put("w", "Data Matrix");
      codeSymbolCodeTypeMap.put("x", "MaxiCode");
      codeSymbolCodeTypeMap.put("y", "GS1 (Composite / DataBar / DataBar Omnidirectional)");
      codeSymbolCodeTypeMap.put("z", "Aztec");
      codeSymbolCodeTypeMap.put("{", "GS1 DataBar Limited");
      codeSymbolCodeTypeMap.put("|", "GS1-128");
      codeSymbolCodeTypeMap.put("}", "GS1 DataBar Expanded");
      codeSymbolCodeTypeMap.put("<", "Code 32 Pharmaceutical (PARAF)");
      codeSymbolCodeTypeMap.put(",", "InfoMail");
      codeSymbolCodeTypeMap.put("?", "Korea Post");
   }

   private final String code;
   private final String codeId;
   private final String codeType;
   private final String aimId;
   private final String charset;

   public ScannedData(String code, String codeId, String aimId, String charset)
   {
      this.code = code;
      this.codeId = codeId;
      this.codeType = codeTypeFromCodeSymbol(codeId);
      this.aimId = aimId;
      this.charset = charset;
   }

   /**
    * Retrieves the bar code data as a String. If you need the data as bytes, you can call String.getBytes(Charset) on the result of this method. The Charset needed to retrieve the bar code data as bytes can be determined by calling getCharset().
    * @return The bar code data as a String.
    */
   public String getCode()
   {
      return code;
   }

   public byte[] getCodeBytes() throws UnsupportedEncodingException
   {
      return getCode().getBytes(getCharset());
   }

   /**
    * Retrieves the unique characters defined by Honeywell to identify symbologies.
    * @return The symbology identifier.
    */
   public String getCodeId()
   {
      return codeId;
   }

   /**
    * Retrieves the code type that the scanned code belongs to. This code type is obtained from codeId.
    * For more details check this documentation:
    * https://support.honeywellaidc.com/s/article/How-to-determine-the-Symbology-from-the-DecodeResult
    * https://support.honeywellaidc.com/s/article/List-of-Honeywell-barcode-symbology-Code-Identifiers
    * @return The code type.
    */
   public String getCodeType()
   {
      return codeType;
   }

   /**
    * Retrieves the AIM identifier of the bar code.
    * @return The AIM ID.
    */
   public String getAimId()
   {
      return aimId;
   }

   /**
    * Retrieves the character encoding of the bar code data. This encoding information allows you to get access to the original binary data encoded in the bar code.
    * @return The character encoding of the bar code data
    */
   public String getCharset()
   {
      return charset;
   }

   Map<String, Object> toMap() {
      final Map<String, Object> map = new HashMap<>();
      map.put("code", code);
      map.put("codeId", codeId);
      map.put("codeType", codeType);
      map.put("aimId", aimId);
      map.put("charset", charset);
      return map;
   }

   static ScannedData from(@NonNull BarcodeReadEvent barcodeReadEvent) {
      return new ScannedData(
         barcodeReadEvent.getBarcodeData(),
         barcodeReadEvent.getCodeId(),
         barcodeReadEvent.getAimId(),
         barcodeReadEvent.getCharset() != null ? barcodeReadEvent.getCharset().name() : null
      );
   }

   static String codeTypeFromCodeSymbol(String codeId) {
      return codeSymbolCodeTypeMap.get(codeId);
   }
}
