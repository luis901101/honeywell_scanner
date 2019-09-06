#import "HoneywellScannerPlugin.h"
#import <honeywell_scanner/honeywell_scanner-Swift.h>

@implementation HoneywellScannerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftHoneywellScannerPlugin registerWithRegistrar:registrar];
}
@end
