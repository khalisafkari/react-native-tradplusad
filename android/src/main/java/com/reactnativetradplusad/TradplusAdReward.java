package com.reactnativetradplusad;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
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

  @ReactMethod(isBlockingSynchronousMethod = true)
  public boolean isReady() {
      return tpReward.isReady();
  }



  @Override
  public void onAdLoaded(TPAdInfo tpAdInfo) {
      sendLog("onAdLoaded");
  }

  @Override
  public void onAdClicked(TPAdInfo tpAdInfo) {
      sendLog("onAdClicked");
  }

  @Override
  public void onAdImpression(TPAdInfo tpAdInfo) {
      sendLog("onAdImpression");
  }

  @Override
  public void onAdFailed(TPAdError tpAdError) {
      sendLog("onAdFailed");
  }

  @Override
  public void onAdClosed(TPAdInfo tpAdInfo) {
      sendLog("onAdClosed");
  }

  @Override
  public void onAdReward(TPAdInfo tpAdInfo) {
      sendLog("onAdReward");
  }

  @Override
  public void onAdVideoError(TPAdInfo tpAdInfo, TPAdError tpAdError) {
      sendLog("onAdVideoError");
  }

  @Override
  public void onAdPlayAgainReward(TPAdInfo tpAdInfo) {
      sendLog("onAdPlayAgainReward");
  }

  private void sendLog(String l) {
    Log.d(NAME, l);
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
