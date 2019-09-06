import 'package:meta/meta.dart';

/**
 * Callback of the decoding process
 */
abstract class ScannerCallBack {
  /**
   * Called when decoder has successfully decoded the code
   * <br>
   *
   * @param result Encapsulates the result of decoding a barcode within an image
   */
  void onDecoded(@required String result);
  /**
   * Called when error has occurred
   * <br>
   *
   * @param error Exception that has been thrown
   */
  void onError(@required Exception error);
}
