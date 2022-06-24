import { requireNativeComponent, UIManager, ViewProps } from 'react-native';
import LINKING_ERROR from './Link';


interface props extends ViewProps {
    placementId: string;
    onAdLoaded?(event: any): void;
    onAdClicked?(event: any): void;
    onAdImpression?(event: any): void;
    onAdLoadFailed?(event: any): void;
    onAdClosed?(event: any): void;
    onBannerRefreshed?(): void
}

const TradplusAdBanner = UIManager.getViewManagerConfig('TradplusNativeBannerManager') != null
    ? requireNativeComponent<props>('TradplusNativeBannerManager') : () => { throw new Error(LINKING_ERROR) }


const BannerAd = TradplusAdBanner;
export default BannerAd;

