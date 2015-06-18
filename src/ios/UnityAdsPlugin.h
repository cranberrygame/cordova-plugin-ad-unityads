//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://cranberrygame.github.io
//License: MIT (http://opensource.org/licenses/MIT)
#import <Cordova/CDV.h>
#import <UnityAds/UnityAds.h>

@interface UnityAdsPlugin : CDVPlugin

@property NSString *callbackIdKeepCallback;
//
@property NSString *email;
@property NSString *licenseKey_;
@property BOOL validLicenseKey;
//
@property NSString *gameId;
@property NSString *videoAdPlacementId;
@property NSString *rewardedVideoAdPlacementId;
@property BOOL isTest;
//
@property id myUnityAdsDelegate;
@property NSInteger videoOrRewardedVideo;

- (void) setLicenseKey: (CDVInvokedUrlCommand*)command;
- (void) setUp:(CDVInvokedUrlCommand*)command;
- (void) showVideoAd:(CDVInvokedUrlCommand*)command;
- (void) showRewardedVideoAd:(CDVInvokedUrlCommand*)command;

@end

@interface MyUnityAdsDelegate : NSObject <UnityAdsDelegate>

@property UnityAdsPlugin *unityAdsPlugin;

- (id) initWithUnityAdsPlugin:(UnityAdsPlugin *)unityAdsPlugin_ ;

@end

