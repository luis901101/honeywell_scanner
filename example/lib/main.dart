import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:honeywell_scanner/honeywell_scanner.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp])
      .then((_) => runApp(const MyApp()));
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp>
    with WidgetsBindingObserver
    implements ScannerCallback {
  HoneywellScanner honeywellScanner = HoneywellScanner();
  ScannedData? scannedData;
  String? errorMessage;
  bool scannerEnabled = false;
  bool scan1DFormats = true;
  bool scan2DFormats = true;
  bool isDeviceSupported = false;

  static const BTN_START_SCANNER = 0,
      BTN_STOP_SCANNER = 1,
      BTN_START_SCANNING = 2,
      BTN_STOP_SCANNING = 3;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    honeywellScanner.scannerCallback = this;
    // honeywellScanner.onScannerDecodeCallback = onDecoded;
    // honeywellScanner.onScannerErrorCallback = onError;
    init();
  }

  Future<void> init() async {
    updateScanProperties();
    isDeviceSupported = await honeywellScanner.isSupported();
    if (mounted) setState(() {});
  }

  void updateScanProperties() {
    List<CodeFormat> codeFormats = [];
    if (scan1DFormats) codeFormats.addAll(CodeFormatUtils.ALL_1D_FORMATS);
    if (scan2DFormats) codeFormats.addAll(CodeFormatUtils.ALL_2D_FORMATS);

//    codeFormats.add(CodeFormat.AZTEC);
//    codeFormats.add(CodeFormat.CODABAR);
//    codeFormats.add(CodeFormat.CODE_39);
//    codeFormats.add(CodeFormat.CODE_93);
//    codeFormats.add(CodeFormat.CODE_128);
//    codeFormats.add(CodeFormat.DATA_MATRIX);
//    codeFormats.add(CodeFormat.EAN_8);
//    codeFormats.add(CodeFormat.EAN_13);
////    codeFormats.add(CodeFormat.ITF);
//    codeFormats.add(CodeFormat.MAXICODE);
//    codeFormats.add(CodeFormat.PDF_417);
//    codeFormats.add(CodeFormat.QR_CODE);
//    codeFormats.add(CodeFormat.RSS_14);
//    codeFormats.add(CodeFormat.RSS_EXPANDED);
//    codeFormats.add(CodeFormat.UPC_A);
//    codeFormats.add(CodeFormat.UPC_E);
////    codeFormats.add(CodeFormat.UPC_EAN_EXTENSION);

    Map<String, dynamic> properties = {
      ...CodeFormatUtils.getAsPropertiesComplement(codeFormats),
      'DEC_CODABAR_START_STOP_TRANSMIT': true,
      'DEC_EAN13_CHECK_DIGIT_TRANSMIT': true,
    };
    honeywellScanner.setProperties(properties);
  }

  @override
  void onDecoded(ScannedData? scannedData) {
    setState(() {
      this.scannedData = scannedData;
    });
  }

  @override
  void onError(Exception error) {
    setState(() {
      errorMessage = error.toString();
    });
  }

  Widget get scannedDataView => RichText(
        textAlign: TextAlign.center,
        text: TextSpan(
            style: TextStyle(
                color: Theme.of(context).textTheme.bodyText1?.color,
                height: 0.8),
            children: [
              const TextSpan(text: 'Scanned code: '),
              TextSpan(
                  text: '${scannedData?.code}\n\n',
                  style: const TextStyle(fontWeight: FontWeight.bold)),
              const TextSpan(text: 'Scanned codeId symbol: '),
              TextSpan(
                  text: '${scannedData?.codeId}\n\n',
                  style: const TextStyle(fontWeight: FontWeight.bold)),
              const TextSpan(text: 'Scanned code type: '),
              TextSpan(
                  text: '${scannedData?.codeType}\n\n',
                  style: const TextStyle(fontWeight: FontWeight.bold)),
              const TextSpan(text: 'Scanned aimId: '),
              TextSpan(
                  text: '${scannedData?.aimId}\n\n',
                  style: const TextStyle(fontWeight: FontWeight.bold)),
              const TextSpan(text: 'Scanned charset: '),
              TextSpan(
                  text: '${scannedData?.charset}\n\n',
                  style: const TextStyle(fontWeight: FontWeight.bold)),
            ]),
      );

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Honeywell scanner example'),
        ),
        body: Center(
          child: Scrollbar(
            child: SingleChildScrollView(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Text(
                    'Device supported: $isDeviceSupported',
                    style: TextStyle(
                        color: isDeviceSupported ? Colors.green : Colors.red,
                        fontSize: 18,
                        fontWeight: FontWeight.bold),
                  ),
                  const SizedBox(height: 8),
                  Text(
                    'Scanner: ${scannerEnabled ? "Started" : "Stopped"}',
                    style: TextStyle(
                        color: scannerEnabled ? Colors.blue : Colors.orange),
                  ),
                  const SizedBox(height: 8),
                  if (scannedData != null && errorMessage == null)
                    scannedDataView,
                  const SizedBox(height: 8),
                  if (errorMessage != null) ...[
                    Text(
                      'Error: $errorMessage',
                      textAlign: TextAlign.center,
                      style: const TextStyle(
                          color: Colors.red, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 8),
                  ],
                  SwitchListTile(
                    title: const Text("Scan 1D Codes"),
                    subtitle:
                        const Text("like Code-128, Code-39, Code-93, etc"),
                    value: scan1DFormats,
                    onChanged: (value) {
                      scan1DFormats = value;
                      updateScanProperties();
                      setState(() {});
                    },
                  ),
                  SwitchListTile(
                    title: const Text("Scan 2D Codes"),
                    subtitle: const Text("like QR, Data Matrix, Aztec, etc"),
                    value: scan2DFormats,
                    onChanged: (value) {
                      scan2DFormats = value;
                      updateScanProperties();
                      setState(() {});
                    },
                  ),
                  const SizedBox(height: 16),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      ElevatedButton(
                        child: const Text("Start Scanner"),
                        style: ButtonStyle(
                            backgroundColor:
                                MaterialStateProperty.all(Colors.blue)),
                        onPressed: () => onClick(BTN_START_SCANNER),
                      ),
                      ElevatedButton(
                        child: const Text("Stop Scanner"),
                        style: ButtonStyle(
                            backgroundColor: MaterialStateProperty.all(
                                Colors.blue.shade700)),
                        onPressed: () => onClick(BTN_STOP_SCANNER),
                      ),
                    ],
                  ),
                  const SizedBox(height: 16),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      ElevatedButton(
                        child: const Text("Start Scanning"),
                        style: ButtonStyle(
                            backgroundColor:
                                MaterialStateProperty.all(Colors.green)),
                        onPressed: () => onClick(BTN_START_SCANNING),
                      ),
                      ElevatedButton(
                        child: const Text("Stop Scanning"),
                        style: ButtonStyle(
                            backgroundColor: MaterialStateProperty.all(
                                Colors.green.shade700)),
                        onPressed: () => onClick(BTN_STOP_SCANNING),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    super.didChangeAppLifecycleState(state);
    switch (state) {
      case AppLifecycleState.resumed:
        honeywellScanner.resumeScanner();
        break;
      case AppLifecycleState.inactive:
        honeywellScanner.pauseScanner();
        break;
      case AppLifecycleState
          .paused: //AppLifecycleState.paused is used as stopped state because deactivate() works more as a pause for lifecycle
        honeywellScanner.pauseScanner();
        break;
      case AppLifecycleState.detached:
        honeywellScanner.pauseScanner();
        break;
      default:
        break;
    }
  }

  Future<void> onClick(int id) async {
    try {
      errorMessage = null;
      switch (id) {
        case BTN_START_SCANNER:
          if (await honeywellScanner.startScanner()) {
            setState(() {
              scannerEnabled = true;
            });
          }
          break;
        case BTN_STOP_SCANNER:
          if (await honeywellScanner.stopScanner()) {
            setState(() {
              scannerEnabled = false;
            });
          }
          break;
        case BTN_START_SCANNING:
          await honeywellScanner.startScanning();
          break;
        case BTN_STOP_SCANNING:
          await honeywellScanner.stopScanning();
          break;
      }
    } catch (e) {
      print(e);
      setState(() {
        errorMessage = e.toString();
      });
    }
  }

  @override
  void dispose() {
    honeywellScanner.stopScanner();
    super.dispose();
  }
}
