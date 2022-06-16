// import {
//   requireNativeComponent,
//   UIManager,
//   Platform,
//   ViewStyle,
// } from 'react-native';




import { NativeModules,
  requireNativeComponent,
  UIManager,
  Platform,
  ViewStyle,
} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-tradplusad' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const {TradplusAdReward, TradplusAdSdk} = NativeModules;
const ComponentName = 'TradplusadView';

interface TradplusAdSdkInterface {
  initSdk(appId: string): void
  setUserId(userId: string): void;
}

interface TradplusAdRewardInterface {
  initAd(unitId: string, autoInstall: boolean): void;
  entryAdScenario(scene: string): void;
  showAd(scene: string): void;
  isReady(): boolean
}

interface TradplusadProps {
  color: string;
  style: ViewStyle;
}

export const TradplusSdk = TradplusAdSdk as TradplusAdSdkInterface;
export const TradplusReward = TradplusAdReward as TradplusAdRewardInterface;

export const TradplusadView = UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<TradplusadProps>(ComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
    };