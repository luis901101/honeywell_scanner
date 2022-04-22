/// BarCode data encapsulation
class ScannedData {
  /// Code data as a String. If you need the data as bytes, you can call String.getBytes(Charset) on the result of this method. The Charset needed to retrieve the bar code data as bytes can be determined by calling getCharset().
  final String? code;

  /// Retrieves the unique characters defined by Honeywell to identify symbologies.
  final String? codeId;

  /// Retrieves the code type that the scanned code belongs to. This code type is obtained from codeId.
  /// For more details check this documentation:
  /// https://support.honeywellaidc.com/s/article/How-to-determine-the-Symbology-from-the-DecodeResult
  /// https://support.honeywellaidc.com/s/article/List-of-Honeywell-barcode-symbology-Code-Identifiers
  final String? codeType;

  /// Retrieves the AIM identifier of the bar code.
  final String? aimId;

  /// Retrieves the character encoding of the bar code data. This encoding information allows you to get access to the original binary data encoded in the bar code.
  final String? charset;

  const ScannedData({
    this.code,
    this.codeId,
    this.codeType,
    this.aimId,
    this.charset,
  });

  factory ScannedData.fromMap(Map<dynamic, dynamic> map) => ScannedData(
        code: map['code']?.toString(),
        codeId: map['codeId']?.toString(),
        codeType: map['codeType']?.toString(),
        aimId: map['aimId']?.toString(),
        charset: map['charset']?.toString(),
      );
}
