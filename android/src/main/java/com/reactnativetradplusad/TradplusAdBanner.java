package com.reactnativetradplusad;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;
import com.tradplus.ads.base.bean.TPAdError;
import com.tradplus.ads.base.bean.TPAdInfo;
import com.tradplus.ads.open.banner.BannerAdListener;
import com.tradplus.ads.open.banner.TPBanner;

public class TradplusAdBanner extends ReactViewGroup {

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

  public TradplusAdBanner(Context context) {
    super(context);
    this.reactContext = (ThemedReactContext) context;
  }

  public void attachView(final String placement) {

     final TPBanner tpBanner = new TPBanner(reactContext);
     final TPBanner oldAd = (TPBanner) getChildAt(0);

     if (oldAd != null) {
        oldAd.onDestroy();
     }

     addView(tpBanner, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
     tpBanner.setAdListener(new TpListener());
     tpBanner.loadAd(placement);
  }

  @Override
  public void requestLayout() {
    super.requestLayout();
    post(runnable);
  }

  private class TpListener extends BannerAdListener {

    @Override
    public void onAdLoaded(TPAdInfo tpAdInfo) {
      super.onAdLoaded(tpAdInfo);
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      sendEvent("onAdLoaded", map);
      requestLayout();
    }

    @Override
    public void onAdClicked(TPAdInfo tpAdInfo) {
      super.onAdClicked(tpAdInfo);
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      sendEvent("onAdClicked", map);
    }

    @Override
    public void onAdImpression(TPAdInfo tpAdInfo) {
      super.onAdImpression(tpAdInfo);
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      sendEvent("onAdImpression", map);
    }

    @Override
    public void onAdShowFailed(TPAdError tpAdError, TPAdInfo tpAdInfo) {
      super.onAdShowFailed(tpAdError, tpAdInfo);
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      map.putString("TPAdError", tpAdError.getErrorMsg());
      sendEvent("onAdShowFailed", map);
    }

    @Override
    public void onAdLoadFailed(TPAdError tpAdError) {
      super.onAdLoadFailed(tpAdError);
      WritableMap map = Arguments.createMap();
      map.putString("TPAdError", tpAdError.getErrorMsg());
      sendEvent("onAdLoadFailed", map);
    }

    @Override
    public void onAdClosed(TPAdInfo tpAdInfo) {
      super.onAdClosed(tpAdInfo);
      WritableMap map = Arguments.createMap();
      map.putString("TPAdInfo", tpAdInfo.toString());
      sendEvent("onAdClosed", map);
    }

    @Override
    public void onBannerRefreshed() {
      super.onBannerRefreshed();
      sendEvent("onBannerRefreshed", null);
    }
  }

  private RCTEventEmitter getEmitter() {
    ReactContext context = (ReactContext) reactContext;
    return context.getJSModule(RCTEventEmitter.class);
  }

  private void sendEvent(final String eventName, @Nullable final WritableMap map) {
    getEmitter().receiveEvent(getId(), eventName, map);
  }


}
