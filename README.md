# AdLib Android SDK

##Configure your Project

1. Download the Android AdLib AAR file under SDK/adlib-beta-release.aar
2. Add it to your Android project.

Alternately, if you would prefer to work with JAR files rather than an AAR file:  
  1. Download the Android AdLib JAR file under SDK/adlib-beta-release.jar  
  2. Download the required which it depends under SDK/Required JARs/  
  3. Add all of these to your Android project.  

In your project's Android Manifest, make sure you have the following:

```XML
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>


<activity android:name="com.google.android.gms.ads.AdActivity" android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize" />
<activity android:name="com.inmobi.rendering.InMobiAdActivity" />
<activity android:name="com.applovin.adview.AppLovinInterstitialActivity"/>
<activity android:name="com.applovin.adview.AppLovinConfirmationActivity"/>
<meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version"/>
```

##Setup AdLib

Initialize AdLibManager with a Context and your AdLib App ID: 
```Java
AdLibManager.getInstance().init(getActivity(), "<Your App ID>")
```
##Banner Usage

Add an AdLibView to your layout. Initialize it with your AdLib Banner Ad Unit ID:

```Java
mAdLibView = (AdLibView) rootView.findViewById(R.id.ad_lib_view);
mAdLibView.setAdUnit("<Your Ad Unit ID>");
```

Start loading ads
```Java
AdLibManager.getInstance().loadNextAd();
```

##Banner Listener
Set a listener to receive a callback when an banner ad loads, is clicked or receives an error.
```Java
AdLibManager.getInstance().setListener(mAdLibView, new AdLibListener() {
  @Override
  public void onLoad() {
    Log.d("AdLib", "Ad Loaded");
  }
  @Override
  public void onClick() {
    Log.d("AdLib", "Ad Clicked");
  }
  @Override
  public void onError() {
    Log.d("AdLib", "Error");
  }
});
```

##Interstitial Usage

Add an AdLibInterstitialView to your layout. Initialize it with your AdLib Interstitial Ad Unit ID:
```Java
AdLibInterstitialView mInterstitialAdView = new AdLibInterstitialView(this.getContext());
mInterstitialAdView.setAdUnit(mInterstitialUnitId);
```

To load an interstitial ad, call loadInterstitialAd
```Java
AdLibManager.getInstance().loadInterstitialAd(mInterstitialAdView);
```

After an interstitial ad has been loaded, you may show the interstitial ad. An interstitial will not show if it is not ready, so it may be useful to utlilze the listener functions.
```Java
mInterstitialAdView.showInterstitial();
```

##Interstitial Listener
```Java
AdLibManager.getInstance().setInterstitialListener(mInterstitialAdView, new AdLibInterstitialListener()
{
    @Override
    public void onLoad() { Log.d("LISTENER", "LOAD_INTERSTITIAL"); }

    @Override
    public void onClick() { Log.d("LISTENER", "CLICK_INTERSTITIAL"); }

    @Override
    public void onError() { Log.d("LISTENER", "ERROR_INTERSTITIAL"); }

    @Override
    public void onShow() { Log.d("LISTENER", "SHOW_INTERSTITIAL"); }

    @Override
    public void onDismiss() { Log.d("LISTENER", "DISMISS_INTERSTITIAL"); }
});
```

##Demographic Parameters

AdLib accepts the following demographic parameters, which you should set when available:
```Java
AdLibManager.getInstance().putExtra("age", "21");
AdLibManager.getInstance().putExtra("gender", "female");
AdLibManager.getInstance().putExtra("zip", "12345");
AdLibManager.getInstance().putExtra("marital", "single");
AdLibManager.getInstance().putExtra("income", "75000");
AdLibManager.getInstance().putExtra("lat", "-71.0");
AdLibManager.getInstance().putExtra("lon", "25.0");
```
