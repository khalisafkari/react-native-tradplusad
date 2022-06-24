import React from 'react';
import { requireNativeComponent, StyleProp, ViewStyle } from 'react-native';


interface props {
    style?: StyleProp<ViewStyle>
    placementId: string;
    onAdLoaded?(event: any): void;
    onAdClicked?(event: any): void;
    onAdImpression?(event: any): void;
    onAdLoadFailed?(event: any): void;
    onAdClosed?(event: any): void;
    onBannerRefreshed?(): void
}

const TradplusAdBanner = requireNativeComponent<props>('TradplusBannerView');


class BannerAd extends React.PureComponent<props> {
    

    render(): React.ReactNode {
        return (
            <TradplusAdBanner
                style={{ height: 50 }}
                {...this.props}
            />
        )
    }
}



export default React.memo(BannerAd, (prev, next) => {
    return prev.placementId === next.placementId;
});


