package com.reactnativetradplusad;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.tradplus.ads.open.TradPlusSdk;

@ReactModule(name = TradplusAdSdk.NAME)
public class TradplusAdSdk extends ReactContextBaseJavaModule
  implements TradPlusSdk.TradPlusInitListener
{

  public static final String NAME = "TradplusAdSdk";

  public TradplusAdSdk(@Nullable ReactApplicationContext reactContext) {
    super(reactContext);
    TradPlusSdk.setTradPlusInitListener(this);
  }

  @NonNull
  @Override
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void initSdk(String appId) {
    TradPlusSdk.initSdk(getReactApplicationContext(), appId);
  }

  @ReactMethod
  public void setUserId(String userId) {
    TradPlusSdk.setUserId(userId);
  }


  @Override
  public void onInitSuccess() {
      sendLog("onInitSuccess");
  }

  private void sendLog(String lg) {
    Log.d(NAME, lg);
  }
}
