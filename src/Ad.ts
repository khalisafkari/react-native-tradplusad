import { NativeModules } from 'react-native';

interface props {
    initSdk(appId: string): void;
    setUserId(userId: string): void;
}

const AdSDK = NativeModules.TradplusAdSdk;

export default AdSDK as props;