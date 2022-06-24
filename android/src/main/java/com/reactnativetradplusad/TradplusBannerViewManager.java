package com.reactnativetradplusad;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

public class TradplusBannerViewManager extends ViewGroupManager<TradplusAdBanner> {
    public static final String REACT_CLASS = "TradplusBannerView";

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected TradplusAdBanner createViewInstance(@NonNull ThemedReactContext reactContext) {
      return new TradplusAdBanner(reactContext);
    }


    @ReactProp(name = "placementId")
    public void setAdId(final TradplusAdBanner viewGroup, final String plamentId) {
          viewGroup.attachView(plamentId);
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
