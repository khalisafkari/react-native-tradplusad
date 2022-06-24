
// import { NativeModules,
//   requireNativeComponent,
//   UIManager,
//   Platform,
//   ViewStyle,
//   NativeEventEmitter
// } from 'react-native';

import { requireNativeComponent, UIManager, ViewProps } from 'react-native';
import LINKING_ERROR from './Link';



// const LINKING_ERROR =
//   `The package 'react-native-tradplusad' doesn't seem to be linked. Make sure: \n\n` +
//   Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
//   '- You rebuilt the app after installing the package\n' +
//   '- You are not using Expo managed workflow\n';

// const {TradplusAdReward, TradplusAdSdk} = NativeModules;
// const ComponentName = 'TradplusadView';
// const ComponentNameBanner  = 'TradplusBannerView';


// interface TradplusAdSdkInterface {
//   initSdk(appId: string): void
//   setUserId(userId: string): void;
// }

// interface TradplusAdRewardInterface {
//   initAd(unitId: string, autoInstall: boolean): void;
//   entryAdScenario(scene: string): void;
//   showAd(scene: string): void;
//   isReady(): Promise<boolean>
//   reloadAd(): void;
//   clearCacheAd(): void;
//   onDestroy(): void;
// }

// interface TradplusadProps {
//   color: string;
//   style: ViewStyle;
// }

// export const TradplusadView = UIManager.getViewManagerConfig(ComponentName) != null
//     ? requireNativeComponent<TradplusadProps>(ComponentName)
//     : () => {
//         throw new Error(LINKING_ERROR);
//     };

// export const TradplusAdBanner = UIManager.getViewManagerConfig(ComponentNameBanner) != null
//     ? requireNativeComponent<{
//       placementId: string;
//       style: ViewStyle
//     }>(ComponentNameBanner) 
//     : () => {
//       throw new Error(LINKING_ERROR);
//     };   

// export const TradplusSdk = TradplusAdSdk as TradplusAdSdkInterface;
// export const TradplusReward = TradplusAdReward as TradplusAdRewardInterface;

// interface eventKey {
//   [key: string]: any;
// }

// const rewardEvent = new NativeEventEmitter(TradplusAdReward);
// const subscriptionsReward: eventKey = {};

// export const AddEventReward = (event: string, callback: (event: any) => void) => {
//    let new_event = rewardEvent.addListener(event, callback);
//    if (subscriptionsReward[event]) {
//       subscriptionsReward[event].remove();
//       delete subscriptionsReward[event];
//    }   
//    subscriptionsReward[event] = new_event;
//    return;
// };

// export const RemoveEventReward = (event: string) => {
//    if (subscriptionsReward[event]) {
//       subscriptionsReward[event].remove();
//       delete subscriptionsReward[event]
//    }
//    return;
// };

// export const RemoveAllEventReward = () => {
//   const keys = Object.keys(subscriptionsReward);
//   if (keys.length > 0) {
//     keys.map((i) => {
//       subscriptionsReward[i].remove();
//       delete subscriptionsReward[i];
//     });
//   }
//   return;
// }


// export * as AdSDK from './Ad';

export { default as AdSdk } from './Ad';
export { default as AdIntertitial } from './IntertitialAd';
export { default as AdReward } from './RewardAd';

const BannerName  = 'TradplusBannerView';
const NativeBannerName = 'TradplusNativeBannerManager';

interface props  extends ViewProps {
    placementId: string;
    onAdLoaded?(event: any): void;
    onAdClicked?(event: any): void;
    onAdImpression?(event: any): void;
    onAdLoadFailed?(event: any): void;
    onAdClosed?(event: any): void;
    onBannerRefreshed?(): void
}

export const BannerAd = UIManager.getViewManagerConfig(BannerName) != null
    ? requireNativeComponent<props>(BannerName) 
    : () => {
      throw new Error(LINKING_ERROR);
};   


export const NativeBanner = UIManager.getViewManagerConfig(NativeBannerName) != null
    ? requireNativeComponent<props>(NativeBannerName) 
    : () => {
      throw new Error(LINKING_ERROR);
};   
