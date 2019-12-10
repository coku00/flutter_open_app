package com.nongfadai.flutter_open_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import io.flutter.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterOpenAppPlugin */
public class FlutterOpenAppPlugin implements MethodCallHandler {
  private static Context mContext;
  private final String TAG = "FlutterOpenAppPlugin";
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    mContext = registrar.context();

    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_open_app");
    channel.setMethodCallHandler(new FlutterOpenAppPlugin());
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("isInstall")) {
      String packageName = call.argument("packageName");
      boolean isInstall = checkPackInfo(mContext,packageName);
      result.success(isInstall);
    } else if(call.method.equals("openApp")){
      String packageName = call.argument("packageName");
      openApp(mContext,packageName);
      result.success(null);
    }
  }


  private void openApp(Context context,String packageName){

    if(checkPackInfo(context,packageName)){
      PackageManager pkgMag = context.getPackageManager();
      Intent intent = pkgMag.getLaunchIntentForPackage(packageName);

      intent.setAction(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_LAUNCHER);
      intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    }else{
      Log.d(TAG,"没有安装");
    }

  }

  private boolean checkPackInfo(Context context,String packageName) {
    PackageInfo packageInfo = null;
    try {
      packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return packageInfo != null;
  }

}
