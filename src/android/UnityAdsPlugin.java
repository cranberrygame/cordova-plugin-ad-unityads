//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://cranberrygame.github.io
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.cordova.plugin.ad.unityads;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.util.Log;
//
import org.apache.cordova.PluginResult.Status;

import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.util.Log;
import android.view.View;

import java.util.Iterator;
//md5
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//Util
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Surface;
//
import java.util.*;//Random
//
import com.unity3d.ads.android.IUnityAdsListener;
import com.unity3d.ads.android.UnityAds;
import com.unity3d.ads.android.campaign.UnityAdsCampaign;
import com.unity3d.ads.android.data.UnityAdsAdvertisingId;
import com.unity3d.ads.android.item.UnityAdsRewardItem;
import com.unity3d.ads.android.item.UnityAdsRewardItemManager;
import com.unity3d.ads.android.properties.UnityAdsConstants;
import com.unity3d.ads.android.properties.UnityAdsProperties;
import com.unity3d.ads.android.video.UnityAdsVideoPausedView;
import com.unity3d.ads.android.view.UnityAdsFullscreenActivity;
import com.unity3d.ads.android.view.UnityAdsMainView;
import com.unity3d.ads.android.webapp.UnityAdsWebData;
import com.unity3d.ads.android.webapp.IUnityAdsWebBridgeListener;
import com.unity3d.ads.android.webapp.IUnityAdsWebDataListener;
import com.unity3d.ads.android.zone.UnityAdsIncentivizedZone;
import com.unity3d.ads.android.zone.UnityAdsZone;

class Util {

	//ex) Util.alert(cordova.getActivity(),"message");
	public static void alert(Activity activity, String message) {
		AlertDialog ad = new AlertDialog.Builder(activity).create();  
		ad.setCancelable(false); // This blocks the 'BACK' button  
		ad.setMessage(message);  
		ad.setButton("OK", new DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();                      
			}  
		});  
		ad.show(); 		
	}
	
	//https://gitshell.com/lvxudong/A530_packages_app_Camera/blob/master/src/com/android/camera/Util.java
	public static int getDisplayRotation(Activity activity) {
	    int rotation = activity.getWindowManager().getDefaultDisplay()
	            .getRotation();
	    switch (rotation) {
	        case Surface.ROTATION_0: return 0;
	        case Surface.ROTATION_90: return 90;
	        case Surface.ROTATION_180: return 180;
	        case Surface.ROTATION_270: return 270;
	    }
	    return 0;
	}

	public static final String md5(final String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
        }
        return "";
    }
}

public class UnityAdsPlugin extends CordovaPlugin {
	private static final String LOG_TAG = "UnityAdsPlugin";
	private CallbackContext callbackContextKeepCallback;
	//
	protected String email;
	protected String licenseKey;
	public boolean validLicenseKey;
	protected String TEST_GAME_ID = "42520";
	protected String TEST_VIDEO_AD_PLACEMENT_ID = "defaultZone";
	protected String TEST_REWARDED_VIDEO_AD_PLACEMENT_ID = "rewardedVideoZone";
	//
	protected String gameId;
	protected String videoAdPlacementId;
	protected String rewardedVideoAdPlacementId;
	protected boolean isTest;
	//
	protected int videoOrRewardedVideo;
	
    @Override
	public void pluginInitialize() {
		super.pluginInitialize();
		//
    }
	
	//@Override
	//public void onCreate(Bundle savedInstanceState) {//build error
	//	super.onCreate(savedInstanceState);
	//	//
	//}
	
	//@Override
	//public void onStart() {//build error
	//	super.onStart();
	//	//
	//}
	
	@Override
	public void onPause(boolean multitasking) {
		super.onPause(multitasking);
		//
	}
	
	@Override
	public void onResume(boolean multitasking) {
		super.onResume(multitasking);
		UnityAds.changeActivity(cordova.getActivity());
	}
	
	//@Override
	//public void onStop() {//build error
	//	super.onStop();
	//	//
	//}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//
	}
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		if (action.equals("setLicenseKey")) {
			setLicenseKey(action, args, callbackContext);

			return true;
		}	
		else if (action.equals("setUp")) {
			setUp(action, args, callbackContext);

			return true;
		}			
		else if (action.equals("showVideoAd")) {
			showVideoAd(action, args, callbackContext);
						
			return true;
		}
		else if (action.equals("showRewardedVideoAd")) {
			showRewardedVideoAd(action, args, callbackContext);
						
			return true;
		}
		
		return false; // Returning false results in a "MethodNotFound" error.
	}

	private void setLicenseKey(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String email = args.getString(0);
		final String licenseKey = args.getString(1);				
		Log.d(LOG_TAG, String.format("%s", email));			
		Log.d(LOG_TAG, String.format("%s", licenseKey));
		
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_setLicenseKey(email, licenseKey);
			}
		});
	}
	
	private void setUp(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		//Activity activity=cordova.getActivity();
		//webView
		//args.length()
		//args.getString(0)
		//args.getString(1)
		//args.getInt(0)
		//args.getInt(1)
		//args.getBoolean(0)
		//args.getBoolean(1)
		//JSONObject json = args.optJSONObject(0);
		//json.optString("adUnitBanner")
		//json.optString("adUnitFullScreen")
		//JSONObject inJson = json.optJSONObject("inJson");
		//final String adUnitBanner = args.getString(0);
		//final String adUnitFullScreen = args.getString(1);				
		//final boolean isOverlap = args.getBoolean(2);				
		//final boolean isTest = args.getBoolean(3);
		//final String[] zoneIds = new String[args.getJSONArray(4).length()];
		//for (int i = 0; i < args.getJSONArray(4).length(); i++) {
		//	zoneIds[i] = args.getJSONArray(4).getString(i);
		//}			
		//Log.d(LOG_TAG, String.format("%s", adUnitBanner));			
		//Log.d(LOG_TAG, String.format("%s", adUnitFullScreen));
		//Log.d(LOG_TAG, String.format("%b", isOverlap));
		//Log.d(LOG_TAG, String.format("%b", isTest));	
		final String gameId = args.getString(0);
		final String videoAdPlacementId = args.getString(1);
		final String rewardedVideoAdPlacementId = args.getString(2);
		final boolean isTest = args.getBoolean(3);		
		Log.d(LOG_TAG, String.format("%s", gameId));			
		Log.d(LOG_TAG, String.format("%s", videoAdPlacementId));			
		Log.d(LOG_TAG, String.format("%s", rewardedVideoAdPlacementId));			
		Log.d(LOG_TAG, String.format("%b", isTest));
		
		callbackContextKeepCallback = callbackContext;
			
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_setUp(gameId, videoAdPlacementId, rewardedVideoAdPlacementId, isTest);
			}
		});
	}
	
	private void showVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showVideoAd();
			}
		});
	}

	private void showRewardedVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showRewardedVideoAd();
			}
		});
	}
	
	public void _setLicenseKey(String email, String licenseKey) {
		this.email = email;
		this.licenseKey = licenseKey;
		
		//
		String str1 = Util.md5("cordova-plugin-: " + email);
		String str2 = Util.md5("cordova-plugin-ad-unityads: " + email);
		String str3 = Util.md5("com.cranberrygame.cordova.plugin.: " + email);
		String str4 = Util.md5("com.cranberrygame.cordova.plugin.ad.unityads: " + email);
		if(licenseKey != null && (licenseKey.equalsIgnoreCase(str1) || licenseKey.equalsIgnoreCase(str2) || licenseKey.equalsIgnoreCase(str3) || licenseKey.equalsIgnoreCase(str4))) {
			this.validLicenseKey = true;
			//
			String[] excludedLicenseKeys = {"xxx"};
			for (int i = 0 ; i < excludedLicenseKeys.length ; i++) {
				if (excludedLicenseKeys[i].equals(licenseKey)) {
					this.validLicenseKey = false;
					break;
				}
			}			
			if (this.validLicenseKey)
				Log.d(LOG_TAG, String.format("%s", "valid licenseKey"));
			else
				Log.d(LOG_TAG, String.format("%s", "invalid licenseKey"));
		}
		else {
			Log.d(LOG_TAG, String.format("%s", "invalid licenseKey"));
			this.validLicenseKey = false;			
		}
		//if (!this.validLicenseKey)
		//	Util.alert(cordova.getActivity(),"Cordova UnityAds: invalid email / license key. You can get free license key from https://play.google.com/store/apps/details?id=com.cranberrygame.pluginsforcordova");			
	}
	
	private void _setUp(String gameId, String videoAdPlacementId, String rewardedVideoAdPlacementId, boolean isTest) {

		this.gameId = gameId;
		this.videoAdPlacementId = videoAdPlacementId;
		this.rewardedVideoAdPlacementId = rewardedVideoAdPlacementId;
		this.isTest = isTest;
	
		if (!validLicenseKey) {
			if (new Random().nextInt(100) <= 1) {//0~99					
				this.gameId = TEST_GAME_ID;
				this.videoAdPlacementId = TEST_VIDEO_AD_PLACEMENT_ID;
				this.rewardedVideoAdPlacementId = TEST_REWARDED_VIDEO_AD_PLACEMENT_ID;
			}
		}

		//https://unityads.unity3d.com/help/Documentation%20for%20Publishers/Integration-Guide-for-Android
		UnityAds.init(cordova.getActivity(), this.gameId, new MyUnityAdsListener());
		UnityAds.setTestMode(isTest);
		//UnityAds.setDebugMode(true);
	}

	private void _showVideoAd() {
		videoOrRewardedVideo = 1;
		
		UnityAds.setZone(this.videoAdPlacementId);
		if(UnityAds.canShow() && UnityAds.canShowAds()) {
			UnityAds.show();
		}
	}

	private void _showRewardedVideoAd() {
		videoOrRewardedVideo = 2;
		
		UnityAds.setZone(this.rewardedVideoAdPlacementId);
		if(UnityAds.canShow() && UnityAds.canShowAds()) {
			UnityAds.show();
		}
	}
	
	class MyUnityAdsListener implements IUnityAdsListener {

		public void onFetchCompleted() {
			Log.d(LOG_TAG, String.format("%s", "onFetchCompleted"));
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoAdLoaded");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			//callbackContextKeepCallback.sendPluginResult(pr);
			
			pr = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdLoaded");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			//callbackContextKeepCallback.sendPluginResult(pr);
			
		}
		public void onFetchFailed() {
			Log.d(LOG_TAG, String.format("%s", "onFetchFailed"));
		}		
		//Called when video playback is initiated by the user
		public void onVideoStarted() {
			Log.d(LOG_TAG, String.format("%s", "onVideoStarted"));
		}
		//Called when the Unity Ads is shown to the user
		public void onShow() {
			Log.d(LOG_TAG, String.format("%s", "onShow"));
			
			if (videoOrRewardedVideo == 1) {
				PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoAdShown");
				pr.setKeepCallback(true);
				callbackContextKeepCallback.sendPluginResult(pr);
				//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
				//pr.setKeepCallback(true);
				//callbackContextKeepCallback.sendPluginResult(pr);
			}
			else if (videoOrRewardedVideo == 2) {
				PluginResult pr = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdShown");
				pr.setKeepCallback(true);
				callbackContextKeepCallback.sendPluginResult(pr);
				//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
				//pr.setKeepCallback(true);
				//callbackContextKeepCallback.sendPluginResult(pr);
			}
		}
		//Called when the Unity Ads is closed by the user
		public void onHide() {
			Log.d(LOG_TAG, String.format("%s", "onHide"));
			
			if (videoOrRewardedVideo == 1) {			
				PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoAdHidden");
				pr.setKeepCallback(true);
				callbackContextKeepCallback.sendPluginResult(pr);
				//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
				//pr.setKeepCallback(true);
				//callbackContextKeepCallback.sendPluginResult(pr);
				
				if(UnityAds.canShow() && UnityAds.canShowAds()) {
					pr = new PluginResult(PluginResult.Status.OK, "onVideoAdLoaded");
					pr.setKeepCallback(true);
					callbackContextKeepCallback.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//callbackContextKeepCallback.sendPluginResult(pr);
				}					
			}
			else if (videoOrRewardedVideo == 2) {
				PluginResult pr = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdHidden");
				pr.setKeepCallback(true);
				callbackContextKeepCallback.sendPluginResult(pr);
				//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
				//pr.setKeepCallback(true);
				//callbackContextKeepCallback.sendPluginResult(pr);
				
				if(UnityAds.canShow() && UnityAds.canShowAds()) {
					pr = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdLoaded");
					pr.setKeepCallback(true);
					callbackContextKeepCallback.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//callbackContextKeepCallback.sendPluginResult(pr);
				}				
			}
		}
		//Called when the video playback is completed. 
		//This step also notifies you that the user should be rewarded.
		public void onVideoCompleted(String itemKey, boolean skipped) {
			Log.d(LOG_TAG, String.format("%s", "onVideoCompleted"));
			
			if (!skipped) {
				if (videoOrRewardedVideo == 1) {			
				}
				else if (videoOrRewardedVideo == 2) {
					PluginResult pr = new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdCompleted");
					pr.setKeepCallback(true);
					callbackContextKeepCallback.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//callbackContextKeepCallback.sendPluginResult(pr);
				}
			}
		}
	}
}
