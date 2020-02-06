
enum CodeFormat
{
  /// Aztec 2D barcode format.
  AZTEC,

  /// CODABAR 1D format.
  CODABAR,

  /// Code 39 1D format.
  CODE_39,

  /// Code 93 1D format.
  CODE_93,

  /// Code 128 1D format.
  CODE_128,

  /// Data Matrix 2D barcode format.
  DATA_MATRIX,

  /// EAN-8 1D format.
  EAN_8,

  /// EAN-13 1D format.
  EAN_13,

  /// ITF (Interleaved Two of Five) 1D format.
  ITF,

  /// MaxiCode 2D barcode format.
  MAXICODE,

  /// PDF417 format.
  PDF_417,

  /// QR Code 2D barcode format.
  QR_CODE,

  /// RSS 14
  RSS_14,

  /// RSS EXPANDED
  RSS_EXPANDED,

  /// UPC-A 1D format.
  UPC_A,

  /// UPC-E 1D format.
  UPC_E,

  /// UPC/EAN extension format. Not a stand-alone format.
  UPC_EAN_EXTENSION,
}

class CodeFormatUtils
{
  static final String _CODE_FORMAT_PROPERTY_AZTEC_ENABLED = "DEC_AZTEC_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_CODABAR_ENABLED = "DEC_CODABAR_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_CODE_39_ENABLED = "DEC_CODE39_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_CODE_93_ENABLED = "DEC_CODE93_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_CODE_128_ENABLED = "DEC_CODE128_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_DATAMATRIX_ENABLED = "DEC_DATAMATRIX_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_EAN_8_ENABLED = "DEC_EAN8_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_EAN_13_ENABLED = "DEC_EAN13_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_MAXICODE_ENABLED = "DEC_MAXICODE_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_PDF_417_ENABLED = "DEC_PDF417_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_QR_CODE_ENABLED = "DEC_QR_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_RSS_ENABLED = "DEC_RSS_14_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_RSS_EXPANDED_ENABLED = "DEC_RSS_EXPANDED_ENABLED";
  static final String _CODE_FORMAT_PROPERTY_UPC_A_ENABLE = "DEC_UPCA_ENABLE";
  static final String _CODE_FORMAT_PROPERTY_UPC_E_ENABLED = "DEC_UPCE0_ENABLED";

  CodeFormatUtils.get();

  static final _mapValueOfName = {
  "AZTEC": CodeFormat.AZTEC,
  "CODABAR": CodeFormat.CODABAR,
  "CODE_39": CodeFormat.CODE_39,
  "CODE_93": CodeFormat.CODE_93,
  "CODE_128": CodeFormat.CODE_128,
  "DATA_MATRIX": CodeFormat.DATA_MATRIX,
  "EAN_8": CodeFormat.EAN_8,
  "EAN_13": CodeFormat.EAN_13,
  "ITF": CodeFormat.ITF,
  "MAXICODE": CodeFormat.MAXICODE,
  "PDF_417": CodeFormat.PDF_417,
  "QR_CODE": CodeFormat.QR_CODE,
  "RSS_14": CodeFormat.RSS_14,
  "RSS_EXPANDED": CodeFormat.RSS_EXPANDED,
  "UPC_A": CodeFormat.UPC_A,
  "UPC_E": CodeFormat.UPC_E,
  "UPC_EAN_EXTENSION": CodeFormat.UPC_EAN_EXTENSION,
  };

  CodeFormat valueOf(String name) {
    CodeFormat value = null;
    try
    {
      value = _mapValueOfName[name];
    }
    catch(e)
    {
      print(e);
    }
    return value;
  }

  String getPropertyName(CodeFormat value) {
    if(value == null) return null;
    try
    {
      switch(value){
        case CodeFormat.AZTEC: return _CODE_FORMAT_PROPERTY_AZTEC_ENABLED;
        case CodeFormat.CODABAR: return _CODE_FORMAT_PROPERTY_CODABAR_ENABLED;
        case CodeFormat.CODE_39:return _CODE_FORMAT_PROPERTY_CODE_39_ENABLED;
        case CodeFormat.CODE_93:return _CODE_FORMAT_PROPERTY_CODE_93_ENABLED;
        case CodeFormat.CODE_128:return _CODE_FORMAT_PROPERTY_CODE_128_ENABLED;
        case CodeFormat.DATA_MATRIX:return _CODE_FORMAT_PROPERTY_DATAMATRIX_ENABLED;
        case CodeFormat.EAN_8:return _CODE_FORMAT_PROPERTY_EAN_8_ENABLED;
        case CodeFormat.EAN_13:return _CODE_FORMAT_PROPERTY_EAN_13_ENABLED;
        case CodeFormat.ITF:break;
        case CodeFormat.MAXICODE:return _CODE_FORMAT_PROPERTY_MAXICODE_ENABLED;
        case CodeFormat.PDF_417:return _CODE_FORMAT_PROPERTY_PDF_417_ENABLED;
        case CodeFormat.QR_CODE:return _CODE_FORMAT_PROPERTY_QR_CODE_ENABLED;
        case CodeFormat.RSS_14:return _CODE_FORMAT_PROPERTY_RSS_ENABLED;
        case CodeFormat.RSS_EXPANDED:return _CODE_FORMAT_PROPERTY_RSS_EXPANDED_ENABLED;
        case CodeFormat.UPC_A:return _CODE_FORMAT_PROPERTY_UPC_A_ENABLE;
        case CodeFormat.UPC_E:return _CODE_FORMAT_PROPERTY_UPC_E_ENABLED;
        case CodeFormat.UPC_EAN_EXTENSION:break;
        default:
      }
    }
    catch(e)
    {
      print(e);
    }
    return null;
  }

  Map<String, dynamic> getFormatsAsProperties(final List<CodeFormat> codeFormats) {
    if(codeFormats == null || codeFormats.isEmpty) return {};

    Map<String, dynamic> mapProperties = {};
    codeFormats.forEach((codeFormat){
      mapProperties[getPropertyName(codeFormat)] = true;
    });
    CodeFormat.values.forEach((codeFormat){
      String propertyName = getPropertyName(codeFormat);
      if(propertyName != null) mapProperties[propertyName] = mapProperties.containsKey(propertyName);
    });
    return mapProperties;
  }

  String nameOf(CodeFormat value) {
    return value != null ? value.toString().split(".")[1] : null;
  }

}