Cordova UnityAds plugin
====================
# Overview #
Show unityads video ad and rewarded video ad.

[android, ios] [cordova cli] [xdk]

Requires unityads account http://unityads.unity3d.com

UnityAds SDK v1.4.2 April 15th, 2015

This is open source cordova plugin.

You can see Plugins For Cordova in one page: http://cranberrygame.github.io?referrer=github

# Change log #
```c

To-Do:
	Support ios callback event.

```
# Install plugin #

## Cordova cli ##
https://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-Line%20Interface - npm install -g cordova@4.1.2
```c
cordova plugin add com.cranberrygame.cordova.plugin.ad.unityads
```

## Xdk ##
https://software.intel.com/en-us/intel-xdk - Download XDK - XDK PORJECTS - [specific project] - CORDOVA 3.X HYBRID MOBILE APP SETTINGS - PLUGINS - Third Party Plugins - Add a Third Party Plugin - Get Plugin from the Web -
```c
Name: unityads
Plugin ID: com.cranberrygame.cordova.plugin.ad.unityads
[v] Plugin is located in the Apache Cordova Plugins Registry
```

## Cocoon ##
https://cocoon.io - Create project - [specific project] - Setting - Plugins - Search - cranberrygame - unityads

## Phonegap build service (config.xml) ##
https://build.phonegap.com/ - Apps - [specific project] - Update code - Zip file including config.xml
```c
<gap:plugin name="com.cranberrygame.cordova.plugin.ad.unityads" source="plugins.cordova.io" />
```

## Construct2 ##
Download construct2 plugin: http://www.paywithapost.de/pay?id=4ef3f2be-26e8-4a04-b826-6680db13a8c8
<br>
Now all the native plugins are installed automatically: https://plus.google.com/102658703990850475314/posts/XS5jjEApJYV
# Server setting #
```c
```

<img src="https://github.com/cranberrygame/cordova-plugin-ad-unityads/blob/master/doc/gameId1.png"><br>
<img src="https://github.com/cranberrygame/cordova-plugin-ad-unityads/blob/master/doc/gameId2.png"><br>
<img src="https://github.com/cranberrygame/cordova-plugin-ad-unityads/blob/master/doc/gameId3.png"><br>
<img src="https://github.com/cranberrygame/cordova-plugin-ad-unityads/blob/master/doc/gameId4.png">

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
	//you can get free license key from https://play.google.com/store/apps/details?id=com.cranberrygame.pluginsforcordova
	//window.unityads.setLicenseKey("yourEmailId@yourEmaildDamin.com", "yourFreeLicenseKey");

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

Plugins For Cordova<br>
http://cranberrygame.github.io?referrer=github

# Credits #
