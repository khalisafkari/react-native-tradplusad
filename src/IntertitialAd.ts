import { NativeModules } from 'react-native';

interface props {
    initAd(unitId: string, autoInstall: boolean): void;
    loadAd(): void;
    entryAdScenario(scene: string): void;
    showAd(scene: string): void;
    isReady(): Promise<boolean>
    reloadAd(): void;
    onDestroy(): void;
}

const AdIntertitial = NativeModules.TradplusAdIntertitial;

export default AdIntertitial as props;