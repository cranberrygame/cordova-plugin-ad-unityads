
module.exports = {
	_loadedVideoAd: false,
	_loadedRewardedVideoAd: false,
	_isShowingVideoAd: false,
	_isShowingRewardedVideoAd: false,
	//
	setLicenseKey: function(email, licenseKey) {
		var self = this;	
        cordova.exec(
            null,
            null,
            'UnityAdsPlugin',
            'setLicenseKey',			
            [email, licenseKey]
        ); 
    },
	setUp: function(gameId, videoAdPlacementId, rewardedVideoAdPlacementId, isTest) {
		var self = this;	
        cordova.exec(
			function (result) {
				console.log('setUp succeeded.');
				
				if (typeof result == "string") {
					//
					if (result == "onVideoAdLoaded") {
						self._loadedVideoAd = true;

						if (self.onVideoAdLoaded)
							self.onVideoAdLoaded();
					}					
					if (result == "onVideoAdShown") {
						self._loadedVideoAd = false;
						self._isShowingVideoAd = true;
					
						if (self.onVideoAdShown)
							self.onVideoAdShown();
					}
					else if (result == "onVideoAdHidden") {
						self._isShowingVideoAd = false;
					
						 if (self.onVideoAdHidden)
							self.onVideoAdHidden();
					}
					//
					else if (result == "onRewardedVideoAdLoaded") {
						self._loadedRewardedVideoAd = true;

						if (self.onRewardedVideoAdLoaded)
							self.onRewardedVideoAdLoaded();
					}					
					else if (result == "onRewardedVideoAdShown") {
						self._loadedRewardedVideoAd = false;
						self._isShowingRewardedVideoAd = true;
					
						if (self.onRewardedVideoAdShown)
							self.onRewardedVideoAdShown();
					}
					else if (result == "onRewardedVideoAdHidden") {
						self._isShowingRewardedVideoAd = false;
					
						 if (self.onRewardedVideoAdHidden)
							self.onRewardedVideoAdHidden();
					}
					else if (result == "onRewardedVideoAdCompleted") {
						if (self.onRewardedVideoAdCompleted)
							self.onRewardedVideoAdCompleted();
					}
				}
				else {
					//var event = result["event"];
					//var location = result["message"];				
					//if (event == "onXXX") {
					//	if (self.onXXX)
					//		self.onXXX(location);
					//}
				}
			},
			function (error) {
				console.log('setUp failed.');
			},
            'UnityAdsPlugin',
            'setUp',			
			[gameId, videoAdPlacementId, rewardedVideoAdPlacementId, isTest]
        ); 
    },
    showVideoAd: function() {
		cordova.exec(
 			null,
            null,
            'UnityAdsPlugin',
            'showVideoAd',
            []
        ); 
    },
    showRewardedVideoAd: function() {
		cordova.exec(
			null,
            null,
            'UnityAdsPlugin',
            'showRewardedVideoAd',
            []
        ); 
    },
	loadedVideoAd: function() {
		return this._loadedVideoAd;
	},
	loadedRewardedVideoAd: function() {
		return this._loadedRewardedVideoAd;
	},
	isShowingVideoAd: function() {
		return this._isShowingVideoAd;
	},
	isShowingRewardedVideoAd: function() {
		return this._isShowingRewardedVideoAd;
	},
	onVideoAdLoaded: null,
	onVideoAdShown: null,
	onVideoAdHidden: null,	
	//
	onRewardedVideoAdLoaded: null,
	onRewardedVideoAdShown: null,
	onRewardedVideoAdHidden: null,
	onRewardedVideoAdCompleted: null
};
