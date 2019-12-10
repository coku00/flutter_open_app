import 'dart:async';

import 'package:flutter/services.dart';

class FlutterOpenApp {
  static const MethodChannel _channel = const MethodChannel('flutter_open_app');

  static Future<void> openApp(String packageName) async {
    await _channel.invokeMethod('openApp', {'packageName': packageName});
  }

  static Future<bool> isInstalled(String packageName) async {
    return await _channel
        .invokeMethod('isInstall', {'packageName': packageName});
  }
}
