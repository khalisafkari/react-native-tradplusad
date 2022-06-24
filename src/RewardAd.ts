import { NativeModules } from 'react-native';

interface props {
    initAd(unitId: string, autoInstall: boolean): void;
    loadAd(): void;
    entryAdScenario(scene: string): void;
    showAd(scene: string): void;
    isReady(): Promise<boolean>
    reloadAd(): void;
    clearCacheAd():void;
    onDestroy(): void;
}

const AdReward = NativeModules.TradplusAdReward;

export default AdReward as props;