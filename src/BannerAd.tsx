import React from 'react';
import { requireNativeComponent, StyleProp, UIManager, ViewProps, ViewStyle } from 'react-native';
import LINKING_ERROR from './Link';


interface props extends ViewProps {
    style?: StyleProp<ViewStyle>
    placementId: string;
    onAdLoaded?(event: any): void;
    onAdClicked?(event: any): void;
    onAdImpression?(event: any): void;
    onAdLoadFailed?(event: any): void;
    onAdClosed?(event: any): void;
    onBannerRefreshed?(): void
}


const TradplusAdBanner = UIManager.getViewManagerConfig('TradplusBannerView') != null
    ? requireNativeComponent<props>('TradplusBannerView') : () => { throw new Error(LINKING_ERROR) }
 

class BannerAd extends React.PureComponent<props> {
    

    render(): React.ReactNode {
        return (
            <TradplusAdBanner
                {...this.props}
            />
        )
    }
}



export default BannerAd


