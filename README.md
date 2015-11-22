Cordova UnityAds plugin
====================
# Overview #
Show unityads video ad and rewarded video ad (pays high $0.05 per ad view).

[android, ios] [cordova cli] [xdk] [cocoon] [phonegap build service]

Requires unityads account http://unityads.unity3d.com

UnityAds SDK v1.5.4 November 19th, 2015 (android)<br>
UnityAds SDK v1.5.4 November 19th, 2015 (ios)

This is open source cordova plugin.

You can see Plugins For Cordova in one page: http://cranberrygame.github.io?referrer=github

# Change log #
```c

1.0.28
	Supported ios callback event.
1.0.32
    Updated SDK v1.5.3
1.0.33
    Updated SDK v1.5.4
```
# Install plugin #

## Cordova cli ##
https://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-Line%20Interface - npm install -g cordova@5.0.0
```c
cordova plugin add cordova-plugin-ad-unityads
(when build error, use github url: cordova plugin add cordova plugin add https://github.com/cranberrygame/cordova-plugin-ad-unityads)
```

## Xdk ##
https://software.intel.com/en-us/intel-xdk - Download XDK - XDK PORJECTS - [specific project] - CORDOVA HYBRID MOBILE APP SETTINGS - Plugin Management - Add Plugins to this Project - Third Party Plugins -
```c
Plugin Source: Cordova plugin registry
Plugin ID: cordova-plugin-ad-unityads
```

```c
Fix crosswalk build error:
crosswalk 11 build error so use crosswalk upper version (crosswalk 12 or 14)
```
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-ad-unityads/master/doc/fix_crosswalk_build_error.png"><br>

## Cocoon ##
https://cocoon.io - Create project - [specific project] - Setting - Plugins - Custom - Git Url: https://github.com/cranberrygame/cordova-plugin-ad-unityads.git - INSTALL - Save<br>

## Phonegap build service (config.xml) ##
https://build.phonegap.com/ - Apps - [specific project] - Update code - Zip file including config.xml
```c
<gap:plugin name="cordova-plugin-ad-unityads" source="npm" />
```

## Construct2 ##
Download construct2 plugin<br>
https://dl.dropboxusercontent.com/u/186681453/pluginsforcordova/index.html<br>
How to install c2 native plugins in xdk, cocoon and cordova cli<br>
https://plus.google.com/102658703990850475314/posts/XS5jjEApJYV

# Server setting #
```c
```

<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-ad-unityads/master/doc/gameId1.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-ad-unityads/master/doc/gameId2.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-ad-unityads/master/doc/gameId3.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-ad-unityads/master/doc/gameId4.png">

# API #
```javascript
var gameId = "REPLACE_THIS_WITH_YOUR_GAME_ID";
var videoAdPlacementId = "defaultZone";
var rewardedVideoAdPlacementId = "rewardedVideoZone";
var isTest = true;
/*
var gameId;
var videoAdPlacementId;
var rewardedVideoAdPlacementId;
var isTest = true;
//android
if (navigator.userAgent.match(/Android/i)) {
	gameId = "REPLACE_THIS_WITH_YOUR_GAME_ID";
	videoAdPlacementId = "defaultZone";
	rewardedVideoAdPlacementId = "rewardedVideoZone";
}
//ios
else if (navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i)) {
	gameId = "REPLACE_THIS_WITH_YOUR_GAME_ID";
	videoAdPlacementId = "defaultZone";
	rewardedVideoAdPlacementId = "rewardedVideoZone";
}
*/

document.addEventListener("deviceready", function(){
	//if no license key, 2% ad traffic share for dev support.
	//you can get paid license key: https://cranberrygame.github.io/request_cordova_ad_plugin_paid_license_key
	//window.unityads.setLicenseKey("yourEmailId@yourEmaildDamin.com", "yourLicenseKey");

	window.unityads.setUp(gameId, videoAdPlacementId, rewardedVideoAdPlacementId, isTest);
	
	//
	window.unityads.onVideoAdLoaded = function() {
		alert('onVideoAdLoaded');
	};	
	window.unityads.onVideoAdShown = function() {
		alert('onVideoAdShown');
	};
	window.unityads.onVideoAdHidden = function() {
		alert('onVideoAdHidden');
	};
	//
	window.unityads.onRewardedVideoAdLoaded = function() {
		alert('onRewardedVideoAdLoaded');
	};	
	window.unityads.onRewardedVideoAdShown = function() {
		alert('onRewardedVideoAdShown');
	};
	window.unityads.onRewardedVideoAdHidden = function() {
		alert('onRewardedVideoAdHidden');
	};	
	window.unityads.onRewardedVideoAdCompleted = function() {
		alert('onRewardedVideoAdCompleted');
	};
}, false);

window.unityads.showVideoAd();

window.unityads.showRewardedVideoAd();

alert(window.unityads.loadedVideoAd());//boolean: true or false
alert(window.unityads.loadedRewardedVideoAd());//boolean: true or false

alert(window.unityads.isShowingVideoAd());//boolean: true or false
alert(window.unityads.isShowingRewardedVideoAd());//boolean: true or false
```
# Examples #
<a href="https://github.com/cranberrygame/cordova-plugin-ad-unityads/blob/master/example/basic/index.html">example/basic/index.html</a><br>

# Test #

[![](http://img.youtube.com/vi/L_TgOf-XwDY/0.jpg)](https://www.youtube.com/watch?v=L_TgOf-XwDY&feature=youtu.be "Youtube")

You can also run following test apk.
https://dl.dropboxusercontent.com/u/186681453/pluginsforcordova/unityads/apk.html

# Useful links #

Cordova Plugins<br>
http://cranberrygame.github.io?referrer=github

# Credits #
