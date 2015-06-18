//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://cranberrygame.github.io
//License: MIT (http://opensource.org/licenses/MIT)
#import "UnityAdsPlugin.h"
#import <CommonCrypto/CommonDigest.h> //md5

@implementation UnityAdsPlugin

@synthesize callbackIdKeepCallback;
//
@synthesize email;
@synthesize licenseKey_;
@synthesize validLicenseKey;
static NSString *TEST_GAME_ID = @"42521";
static NSString *TEST_VIDEO_AD_PLACEMENT_ID = @"defaultZone";
static NSString *TEST_REWARDED_VIDEO_AD_PLACEMENT_ID = @"rewardedVideoZone";
//
@synthesize gameId;
@synthesize videoAdPlacementId;
@synthesize rewardedVideoAdPlacementId;
@synthesize isTest;
//
@synthesize myUnityAdsDelegate;
@synthesize videoOrRewardedVideo;

- (void) pluginInitialize {
    [super pluginInitialize];    
    //
}

- (void) setLicenseKey: (CDVInvokedUrlCommand*)command {
    NSString *email = [command.arguments objectAtIndex: 0];
    NSString *licenseKey = [command.arguments objectAtIndex: 1];
    NSLog(@"%@", email);
    NSLog(@"%@", licenseKey);
    
    [self.commandDelegate runInBackground:^{
        [self _setLicenseKey:email aLicenseKey:licenseKey];
    }];
}

- (void) setUp: (CDVInvokedUrlCommand*)command {
    //self.viewController
    //self.webView	
    //NSString *adUnitBanner = [command.arguments objectAtIndex: 0];
    //NSString *adUnitFullScreen = [command.arguments objectAtIndex: 1];
    //BOOL isOverlap = [[command.arguments objectAtIndex: 2] boolValue];
    //BOOL isTest = [[command.arguments objectAtIndex: 3] boolValue];
	//NSArray *zoneIds = [command.arguments objectAtIndex:4];	
    //NSLog(@"%@", adUnitBanner);
    //NSLog(@"%@", adUnitFullScreen);
    //NSLog(@"%d", isOverlap);
    //NSLog(@"%d", isTest);
	NSString* gameId = [command.arguments objectAtIndex:0];
	NSString* videoAdPlacementId = [command.arguments objectAtIndex:1];
	NSString* rewardedVideoAdPlacementId = [command.arguments objectAtIndex:2];
    BOOL isTest = [[command.arguments objectAtIndex: 3] boolValue];	
	NSLog(@"%@", gameId);
	NSLog(@"%@", videoAdPlacementId);
	NSLog(@"%@", rewardedVideoAdPlacementId);
    NSLog(@"%d", isTest);
	
    self.callbackIdKeepCallback = command.callbackId;
	
    //[self.commandDelegate runInBackground:^{
		[self _setUp:gameId aVideoAdPlacementId:videoAdPlacementId aRewardedVideoAdPlacementId:rewardedVideoAdPlacementId anIsTest:isTest];	
    //}];
}

- (void) showVideoAd: (CDVInvokedUrlCommand*)command {

    [self.commandDelegate runInBackground:^{
		[self _showVideoAd];
    }];
}

- (void) showRewardedVideoAd: (CDVInvokedUrlCommand*)command {

    [self.commandDelegate runInBackground:^{
		[self _showRewardedVideoAd];
    }];
}

- (void) _setLicenseKey:(NSString *)email aLicenseKey:(NSString *)licenseKey {
	self.email = email;
	self.licenseKey_ = licenseKey;
	
	//
	NSString *str1 = [self md5:[NSString stringWithFormat:@"cordova-plugin-: %@", email]];
	NSString *str2 = [self md5:[NSString stringWithFormat:@"cordova-plugin-ad-unityads: %@", email]];
	NSString *str3 = [self md5:[NSString stringWithFormat:@"com.cranberrygame.cordova.plugin.: %@", email]];
	NSString *str4 = [self md5:[NSString stringWithFormat:@"com.cranberrygame.cordova.plugin.ad.unityads: %@", email]];
	if(licenseKey_ != Nil && ([licenseKey_ isEqualToString:str1] || [licenseKey_ isEqualToString:str2] || [licenseKey_ isEqualToString:str3] || [licenseKey_ isEqualToString:str4])){
		self.validLicenseKey = YES;
		NSArray *excludedLicenseKeys = [NSArray arrayWithObjects: @"xxx", nil];
		for (int i = 0 ; i < [excludedLicenseKeys count] ; i++) {
			if([[excludedLicenseKeys objectAtIndex:i] isEqualToString:licenseKey]) {
				self.validLicenseKey = NO;
				break;
			}
		}
	}
	else {
		self.validLicenseKey = NO;
	}
	if (self.validLicenseKey)
		NSLog(@"valid licenseKey");
	else {
		NSLog(@"invalid licenseKey");
		//UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Alert" message:@"Cordova UnityAds: invalid email / license key. You can get free license key from https://play.google.com/store/apps/details?id=com.cranberrygame.pluginsforcordova" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
		//[alert show];
	}
}

- (NSString*) md5:(NSString*) input {
    const char *cStr = [input UTF8String];
    unsigned char digest[16];
    CC_MD5( cStr, strlen(cStr), digest ); // This is the md5 call
    
    NSMutableString *output = [NSMutableString stringWithCapacity:CC_MD5_DIGEST_LENGTH * 2];
    
    for(int i = 0; i < CC_MD5_DIGEST_LENGTH; i++)
        [output appendFormat:@"%02x", digest[i]];
    
    return  output;
}

- (void) _setUp:(NSString *)gameId aVideoAdPlacementId:(NSString *)videoAdPlacementId aRewardedVideoAdPlacementId:(NSString *)rewardedVideoAdPlacementId anIsTest:(BOOL)isTest {
	self.gameId = gameId;
	self.videoAdPlacementId = videoAdPlacementId;
	self.rewardedVideoAdPlacementId = rewardedVideoAdPlacementId;
	self.isTest = isTest;
	
	if (!validLicenseKey) {
		if (arc4random() % 100 <= 1) {//0 ~ 99		
			self.gameId = TEST_GAME_ID;
			self.videoAdPlacementId = TEST_VIDEO_AD_PLACEMENT_ID;
			self.rewardedVideoAdPlacementId = TEST_REWARDED_VIDEO_AD_PLACEMENT_ID;
		}
	}
 
	//https://unityads.unity3d.com/help/Documentation%20for%20Publishers/Integration-Guide-for-iOS
	//https://unityads.unity3d.com/help/Documentation%20for%20Publishers/Integration-Guide-for-Unity
	//http://www.unityads.co.kr/?page_id=866#guide_ios
	[[UnityAds sharedInstance] startWithGameId:self.gameId andViewController:self.viewController];
	//[[UnityAds sharedInstance] startWithGameId:self.gameId];
	//[[UnityAds sharedInstance] setViewController:self.viewController];
	[[UnityAds sharedInstance] setTestMode:self.isTest];
    //[[UnityAds sharedInstance] setDebugMode:YES];
    [[UnityAds sharedInstance] setDebugMode:NO];
    
    //[[UnityAds sharedInstance] setDelegate:[[MyUnityAdsDelegate alloc] initWithUnityAdsPlugin:self]];//
    self.myUnityAdsDelegate = [[MyUnityAdsDelegate alloc] initWithUnityAdsPlugin:self];
    [[UnityAds sharedInstance] setDelegate: self.myUnityAdsDelegate];
}

-(void) _showVideoAd {
    videoOrRewardedVideo = 1;
    
	[[UnityAds sharedInstance] setZone:self.videoAdPlacementId];
	//Use the canShow method to check for zone readiness,
	//then use the canShowAds method to check for ad readiness.
	if ([[UnityAds sharedInstance] canShow] && [[UnityAds sharedInstance] canShowAds]) {
		// If both are ready, show the ad.
		[[UnityAds sharedInstance] show];
	}
}

-(void) _showRewardedVideoAd {
    videoOrRewardedVideo = 2;
    
	[[UnityAds sharedInstance] setZone:self.rewardedVideoAdPlacementId];
	//Use the canShow method to check for zone readiness,
	//then use the canShowAds method to check for ad readiness.
	if ([[UnityAds sharedInstance] canShow] && [[UnityAds sharedInstance] canShowAds]) {
		// If both are ready, show the ad.
		[[UnityAds sharedInstance] show];
	}
}

@end

@implementation MyUnityAdsDelegate

@synthesize unityAdsPlugin;

- (id) initWithUnityAdsPlugin:(UnityAdsPlugin *)unityAdsPlugin_ {
    self = [super init];
    if (self) {
        self.unityAdsPlugin = unityAdsPlugin_;
    }
    return self;
}	

- (void)unityAdsFetchCompleted{
    NSLog(@"unityAdsFetchCompleted");
    
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onVideoAdLoaded"];
    [pr setKeepCallbackAsBool:YES];
    [unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];

    pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdLoaded"];
    [pr setKeepCallbackAsBool:YES];
    [unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
}

- (void)unityAdsFetchFailed{
    NSLog(@"unityAdsFetchFailed");
}

- (void)unityAdsVideoStarted{
    NSLog(@"unityAdsVideoStarted");
}

- (void)unityAdsWillShow{
    NSLog(@"unityAdsWillShow");
}

- (void)unityAdsDidShow{
    NSLog(@"unityAdsDidShow");
    
	if (unityAdsPlugin.videoOrRewardedVideo == 1) {	
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onVideoAdShown"];
		[pr setKeepCallbackAsBool:YES];
		[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
		//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
		//[pr setKeepCallbackAsBool:YES];
		//[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
	}
	else if (unityAdsPlugin.videoOrRewardedVideo == 2) {
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdShown"];
		[pr setKeepCallbackAsBool:YES];
		[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
		//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
		//[pr setKeepCallbackAsBool:YES];
		//[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
	}
}

- (void)unityAdsWillHide{
    NSLog(@"unityAdsWillHide");
}
- (void)unityAdsDidHide{
    NSLog(@"unityAdsDidHide");
    
	if (unityAdsPlugin.videoOrRewardedVideo == 1) {
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onVideoAdHidden"];
		[pr setKeepCallbackAsBool:YES];
		[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
		//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
		//[pr setKeepCallbackAsBool:YES];
		//[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
		
		if ([[UnityAds sharedInstance] canShow] && [[UnityAds sharedInstance] canShowAds]) {
			pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onVideoAdLoaded"];
			[pr setKeepCallbackAsBool:YES];
			[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
			//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
			//[pr setKeepCallbackAsBool:YES];
			//[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
		}		
	}
	else if (unityAdsPlugin.videoOrRewardedVideo == 2) {
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdHidden"];
		[pr setKeepCallbackAsBool:YES];
		[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
		//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
		//[pr setKeepCallbackAsBool:YES];
		//[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
		
		if ([[UnityAds sharedInstance] canShow] && [[UnityAds sharedInstance] canShowAds]) {
			pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdLoaded"];
			[pr setKeepCallbackAsBool:YES];
			[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
			//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
			//[pr setKeepCallbackAsBool:YES];
			//[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
		}
	}			
}

- (void)unityAdsWillLeaveApplication{
    NSLog(@"unityAdsWillLeaveApplication");
}

- (void)unityAdsVideoCompleted:(NSString *)rewardItemKey skipped:(BOOL)skipped {
    NSLog(@"unityAdsVideoCompleted");
    
	if (!skipped) {	
		if (unityAdsPlugin.videoOrRewardedVideo == 1) {			
		}
		else if (unityAdsPlugin.videoOrRewardedVideo == 2) {	
			CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdCompleted"];
			[pr setKeepCallbackAsBool:YES];
			[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
			//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
			//[pr setKeepCallbackAsBool:YES];
			//[unityAdsPlugin.commandDelegate sendPluginResult:pr callbackId:unityAdsPlugin.callbackIdKeepCallback];
		}
	}
}

@end

