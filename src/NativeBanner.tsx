import React from 'react';
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

const TradplusAdBanner = UIManager.getViewManagerConfig('TradplusNativeBanner') != null
    ? requireNativeComponent<props>('TradplusNativeBanner') : () => { throw new Error(LINKING_ERROR) }

class NativeAdBanner extends React.PureComponent<props> {

        render(): React.ReactNode {
            return (
                <TradplusAdBanner
                    {...this.props}
                />
            )
        }

}
    
    
    
export default NativeAdBanner;
    

