package com.reactnativetradplusad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tradplus.ads.base.bean.TPAdError;
import com.tradplus.ads.base.bean.TPAdInfo;
import com.tradplus.ads.open.interstitial.InterstitialAdListener;
import com.tradplus.ads.open.interstitial.TPInterstitial;

@ReactModule(name = TradplusAdIntertitial.NAME)
public class TradplusAdIntertitial extends ReactContextBaseJavaModule
    implements InterstitialAdListener, LifecycleEventListener
{

  public static final String NAME = "TradplusAdIntertitial";
  TPInterstitial tpInterstitial;

  @NonNull
  @Override
  public String getName() {
    return NAME;
  }

  public TradplusAdIntertitial(@Nullable ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addLifecycleEventListener(this);
  }

  @ReactMethod
  public void initAd(String adId, Boolean auto) {
      tpInterstitial = new TPInterstitial(getReactApplicationContext(), adId, auto);
      tpInterstitial.setAdListener(this);
  }

  @ReactMethod
  public void loadAd() {
      tpInterstitial.loadAd();
  }

  @ReactMethod
  public void entryAdScenario(String sceneId) {
      tpInterstitial.entryAdScenario(sceneId);
  }

  @ReactMethod
  public void showAd(String sceneId) {
      tpInterstitial.showAd(getCurrentActivity(), sceneId);
  }

  @ReactMethod
  public void reloadAd() {
    tpInterstitial.reloadAd();
  }


  @ReactMethod
  public void onDestroy() {
    tpInterstitial.onDestroy();
  }

  @ReactMethod
  public void isReady(Promise promise) {
      try {
          promise.resolve(tpInterstitial.isReady());
      } catch (Exception e) {
          promise.reject(e);
      }
  }


  @Override
  public void onAdLoaded(TPAdInfo tpAdInfo) {
    WritableMap map = Arguments.createMap();
    map.putString("data", tpAdInfo.toString());
    sendEvent("onAdLoaded", map);
  }

  @Override
  public void onAdFailed(TPAdError tpAdError) {
    WritableMap map = Arguments.createMap();
    map.putString("msg", tpAdError.getErrorMsg());
    map.putInt("code", tpAdError.getErrorCode());
    sendEvent("onAdFailed", map);
  }

  @Override
  public void onAdImpression(TPAdInfo tpAdInfo) {
    WritableMap map = Arguments.createMap();
    map.putString("data", tpAdInfo.toString());
    sendEvent("onAdImpression", map);
  }

  @Override
  public void onAdClicked(TPAdInfo tpAdInfo) {
    WritableMap map = Arguments.createMap();
    map.putString("data", tpAdInfo.toString());
    sendEvent("onAdClicked", map);
  }

  @Override
  public void onAdClosed(TPAdInfo tpAdInfo) {
    WritableMap map = Arguments.createMap();
    map.putString("data", tpAdInfo.toString());
    sendEvent("onAdClosed", map);
  }

  @Override
  public void onAdVideoError(TPAdInfo tpAdInfo, TPAdError tpAdError) {
    WritableMap map = Arguments.createMap();
    map.putString("data", tpAdInfo.toString());
    map.putString("msg", tpAdError.getErrorMsg());
    map.putInt("code", tpAdError.getErrorCode());
    sendEvent("onAdClosed", map);
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
      if (tpInterstitial != null) {
        tpInterstitial.onDestroy();
      }
  }
}
