import 'package:flutter/material.dart';
import 'package:honeywell_scanner/honeywell_scanner.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  HoneywellScanner honeywellScanner = HoneywellScanner();

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> implements ScannerCallBack{
  String _scannedCode = 'Empty';
  String _scannedStatus = 'Stopped';

  @override
  void initState() {
    super.initState();
    widget.honeywellScanner.scannerCallBack = this;
    widget.honeywellScanner.setProperties(
        CodeFormatUtils.get().getFormatsAsProperties(
            [CodeFormat.CODE_128, CodeFormat.QR_CODE])
    );
  }

  @override
  void onDecoded(String result) {
    setState(() {
      _scannedCode = result;
    });
  }

  @override
  void onError(Exception error) {
    setState(() {
      _scannedCode = error.toString();
    });
  }

//  // Platform messages are asynchronous, so we initialize in an async method.
//  Future showPlatformVersion() async {
//    String platformVersion;
//    // Platform messages may fail, so we use a try/catch PlatformException.
//    try {
//      platformVersion = await HoneywellScanner.platformVersion;
//    } on PlatformException {
//      platformVersion = 'Failed to get platform version.';
//    }
//
//    // If the widget was removed from the tree while the asynchronous platform
//    // message was in flight, we want to discard the reply rather than calling
//    // setState to update our non-existent appearance.
//    if (!mounted) return;
//
//    setState(() {
//      _platformVersion = platformVersion;
//    });
//  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text('Scanner: $_scannedStatus'),
            Divider(color: Colors.transparent,),
            Text('Scanned code: $_scannedCode'),
            Divider(color: Colors.transparent,),
            RaisedButton(
              child: Text("Start Scanner"),
              onPressed: (){
                widget.honeywellScanner.startScanner();
                _scannedStatus = "Started";
                setState(() {});
              },
            ),
            Divider(color: Colors.transparent,),
            RaisedButton(
              child: Text("Stop Scanner"),
              onPressed: (){
                widget.honeywellScanner.stopScanner();
                _scannedStatus = "Stopped";
                setState(() {});
              },
            ),
          ],
        ),
      ),
    );
  }
}
