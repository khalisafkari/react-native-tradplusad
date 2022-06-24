package com.reactnativetradplusad;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;
import com.tradplus.ads.base.bean.TPAdError;
import com.tradplus.ads.base.bean.TPAdInfo;
import com.tradplus.ads.open.banner.BannerAdListener;
import com.tradplus.ads.open.nativead.TPNativeBanner;

import java.util.Map;

class TPBannerStandar extends ReactViewGroup {

  private final ThemedReactContext reactContext;

  private final Runnable runnable = () -> {
    for (int i = 0; i < getChildCount();i++) {
      View child = getChildAt(i);
      child.measure(
        MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY)
      );
      child.layout(0,0, child.getMeasuredWidth(), child.getMeasuredHeight());
    }
  };

  public TPBannerStandar(Context context) {
    super(context);
    this.reactContext = (ThemedReactContext) context;
  }

  public void attachView(final String placement) {
      final TPNativeBanner tpNativeBanner = new TPNativeBanner(reactContext);
      final TPNativeBanner oldNativeBanner = (TPNativeBanner) getChildAt(0);

      if (oldNativeBanner != null) {
          oldNativeBanner.onDestroy();
      }

      addView(tpNativeBanner, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
      tpNativeBanner.loadAd(placement);
      tpNativeBanner.setAdListener(new AdListener());
  }

  @Override
  public void requestLayout() {
    super.requestLayout();
    post(runnable);
  }

  private void sendEvent(final String eventName, @Nullable WritableMap map) {
    ReactContext context = (ReactContext) reactContext;
    context.getJSModule(RCTEventEmitter.class).receiveEvent(getId(),eventName, map);
    return;
  }

  private class AdListener extends BannerAdListener {

    @Override
    public void onAdLoaded(TPAdInfo tpAdInfo) {
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      sendEvent("onAdLoaded", map);
      requestLayout();
    }

    @Override
    public void onAdClicked(TPAdInfo tpAdInfo) {
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      sendEvent("onAdClicked", map);
    }

    @Override
    public void onAdImpression(TPAdInfo tpAdInfo) {
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      sendEvent("onAdImpression", map);
    }

    @Override
    public void onAdShowFailed(TPAdError tpAdError, TPAdInfo tpAdInfo) {
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      map.putString("TPAdError", tpAdError.getErrorMsg());
      sendEvent("onAdShowFailed", map);
    }

    @Override
    public void onAdLoadFailed(TPAdError tpAdError) {
      WritableMap map = Arguments.createMap();
      map.putString("TPAdError", tpAdError.getErrorMsg());
      sendEvent("onAdLoadFailed", map);
    }

    @Override
    public void onAdClosed(TPAdInfo tpAdInfo) {
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      sendEvent("onAdClosed", map);
    }

    @Override
    public void onBannerRefreshed() {
      sendEvent("onBannerRefreshed", null);
    }
  }

}

public class TradplusNativeBannerManager extends SimpleViewManager<TPBannerStandar> {

  public static final String REACT_CLASS = "TradplusNativeBanner";

  @NonNull
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @NonNull
  @Override
  protected TPBannerStandar createViewInstance(@NonNull ThemedReactContext reactContext) {
    return new TPBannerStandar(reactContext);
  }

  @ReactProp(name = "placementId")
  public void setAdId(final TPBannerStandar view, final String placement) {
      view.attachView(placement);
  }

  @Nullable
  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(
      "onAdLoaded",
      MapBuilder.of("registrationName", "didLoad"),
      "onAdClicked",
      MapBuilder.of("registrationName","onAdClicked"),
      "onAdImpression",
      MapBuilder.of("registrationName","onAdImpression"),
      "onAdLoadFailed",
      MapBuilder.of("registrationName","onAdLoadFailed"),
      "onAdClosed",
      MapBuilder.of("registrationName","onAdClosed"),
      "onBannerRefreshed",
      MapBuilder.of("registrationName","onBannerRefreshed")
    );
  }

}
