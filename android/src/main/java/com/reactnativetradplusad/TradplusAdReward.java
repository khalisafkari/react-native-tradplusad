package com.reactnativetradplusad;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tradplus.ads.base.bean.TPAdError;
import com.tradplus.ads.base.bean.TPAdInfo;
import com.tradplus.ads.open.LoadAdEveryLayerListener;
import com.tradplus.ads.open.reward.RewardAdListener;
import com.tradplus.ads.open.reward.TPReward;

@ReactModule(name = TradplusAdReward.NAME)
public class TradplusAdReward extends ReactContextBaseJavaModule
  implements RewardAdListener, LifecycleEventListener
{

  public static final String NAME = "TradplusAdReward";
  TPReward tpReward;

  public TradplusAdReward(@Nullable ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addLifecycleEventListener(this);
  }

  @NonNull
  @Override
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void initAd(String adId, Boolean auto) {
      tpReward = new TPReward(getReactApplicationContext(), adId, auto);
      tpReward.setAdListener(this);
  }

  @ReactMethod
  public void entryAdScenario(String sceneId) {
     tpReward.entryAdScenario(sceneId);
  }

  @ReactMethod
  public void showAd(String sceneId) {
      tpReward.showAd(getCurrentActivity(), sceneId);
  }

  @ReactMethod
  public void reloadAd() {
    tpReward.reloadAd();
  }

  @ReactMethod
  public void clearCacheAd() {
    tpReward.clearCacheAd();
  }

  @ReactMethod
  public void onDestroy() {
    tpReward.onDestroy();
  }

  @ReactMethod
  public void isReady(Promise promise) {
      try {
        promise.resolve(tpReward.isReady());
      } catch (Exception e) {
        promise.reject(e);
      }
  }

  @Override
  public void onAdLoaded(TPAdInfo tpAdInfo) {
      WritableMap map = Arguments.createMap();
      map.putString("data", tpAdInfo.toString());
      sendEvent("onAdLoaded", null);
  }

  @Override
  public void onAdClicked(TPAdInfo tpAdInfo) {
    WritableMap map = Arguments.createMap();
    map.putString("data", tpAdInfo.toString());
    sendEvent("onAdClicked", null);
  }

  @Override
  public void onAdImpression(TPAdInfo tpAdInfo) {
    WritableMap map = Arguments.createMap();
    map.putString("data", tpAdInfo.toString());
    sendEvent("onAdImpression", null);
  }

  @Override
  public void onAdFailed(TPAdError tpAdError) {
      WritableMap map = Arguments.createMap();
      map.putInt("code", tpAdError.getErrorCode());
      map.putString("message", tpAdError.getErrorMsg());
      sendEvent("onAdClosed", null);
  }

  @Override
  public void onAdClosed(TPAdInfo tpAdInfo) {
      WritableMap map = Arguments.createMap();
      map.putString("data", tpAdInfo.toString());
      sendEvent("onAdClosed", null);
  }

  @Override
  public void onAdReward(TPAdInfo tpAdInfo) {
      WritableMap map = Arguments.createMap();
      map.putString("data", tpAdInfo.toString());
      sendEvent("onAdReward", null);
  }

  @Override
  public void onAdVideoError(TPAdInfo tpAdInfo, TPAdError tpAdError) {
      WritableMap map = Arguments.createMap();
      map.putString("data", tpAdInfo.toString());
      sendEvent("onAdVideoError", null);
  }

  @Override
  public void onAdPlayAgainReward(TPAdInfo tpAdInfo) {
      WritableMap map = Arguments.createMap();
      map.putString("data", tpAdInfo.toString());
      sendEvent("onAdPlayAgainReward", null);
  }

  private void sendLog(String l) {
    Log.d(NAME, l);
  }

  private void sendEvent(String eventName,@Nullable WritableMap map) {
    getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, map);
  }

  @Override
  public void onHostResume() {

  }

  @Override
  public void onHostPause() {

  }

  @Override
  public void onHostDestroy() {
      if (tpReward != null) {
        tpReward.onDestroy();
      }
  }
}
