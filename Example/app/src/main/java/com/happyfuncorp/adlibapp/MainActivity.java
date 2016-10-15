package com.happyfuncorp.adlibapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.happyfuncorp.adlib.listeners.AdLibInterstitialListener;
import com.happyfuncorp.adlib.listeners.AdLibListener;
import com.happyfuncorp.adlib.utils.AdLibManager;
import com.happyfuncorp.adlib.views.AdLibInterstitialView;
import com.happyfuncorp.adlib.views.AdLibView;


public class MainActivity extends AppCompatActivity implements SetupListener {

    private static final  String TAG = "TAG";

    private SetupFragment mChooser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(), TAG)
                    .commit();
        }

        mChooser = new SetupFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mChooser, SetupFragment.TAG).addToBackStack(SetupFragment.TAG).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void networkAvailabilityChanged(String networkKey, boolean isAdd) {
        if (isAdd) {
            AdLibManager.getInstance().putExtra(networkKey, String.valueOf(true));
        } else {
            AdLibManager.getInstance().putExtra(networkKey, String.valueOf(false));
        }

        Log.d("AD_LIB_APP", "AVAILABILITY CHANGED: " + networkKey + " " + isAdd);
    }

    @Override
    public void latLongChanged(String lat, String lon) {
        Log.d("AD_LIB_APP", "Lat/Long Changed: " + lat +" / " + lon);

        if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lon)) {
            AdLibManager.getInstance().removeExtra("lat");
            AdLibManager.getInstance().removeExtra("lon");
        } else {
            AdLibManager.getInstance().putExtra("lat", lat);
            AdLibManager.getInstance().putExtra("lon", lon);
        }
    }

    @Override
    public void genderChanged(String gender) {
        Log.d("AD_LIB_APP", "Gender Changed: " + gender);
        if (TextUtils.isEmpty(gender)) {
            AdLibManager.getInstance().removeExtra("gender");
        } else {
            AdLibManager.getInstance().putExtra("gender", gender);
        }
    }

    @Override
    public void ageChanged(String age) {
        Log.d("AD_LIB_APP", "Age Changed: " + age);
        if (TextUtils.isEmpty(age)) {
            AdLibManager.getInstance().removeExtra("age");
        } else {
            AdLibManager.getInstance().putExtra("age", age);
        }
    }

    @Override
    public void adUnitChanged(String id) {
        PlaceholderFragment frag = (PlaceholderFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (frag != null) {
            Log.d("AD_LIB_APP", "Ad Unit ID Changed: " + id);
            if (TextUtils.isEmpty(id)) {
                frag.setAdUnit(Constants.DEFAULT_UNIT_ID);
            } else {
                frag.setAdUnit(id);
            }
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private AdLibView mAdView;
        private AdLibInterstitialView mInterstitialAdView;
        private WebView mWebView;
        private String mTopBannerUnitId = Constants.DEFAULT_UNIT_ID;
        private String mInterstitialUnitId = Constants.DEFAULT_INTERSTITIAL_UNIT_ID;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mAdView = (AdLibView) rootView.findViewById(R.id.ad_view);
            mAdView.setAdUnit(mTopBannerUnitId);
            mInterstitialAdView = new AdLibInterstitialView(this.getContext());
            mInterstitialAdView.setAdUnit(mInterstitialUnitId);
            AdLibManager.getInstance().init(getActivity(), Constants.APP_ID);
            AdLibManager.getInstance().putExtra("zip", "123456");
            AdLibManager.getInstance().putExtra("marital", "single");
            AdLibManager.getInstance().putExtra("income", "7777");

            mInterstitialAdView.loadInterstitial();

            AdLibManager.getInstance().setListener(mAdView, new AdLibListener()
            {
                @Override
                public void onLoad()
                {
                    Log.d("LISTENER", "LOAD_BANNER");
                }

                @Override
                public void onClick()
                {
                    Log.d("LISTENER", "CLICK_BANNER");
                }

                @Override
                public void onError()
                {
                    Log.d("LISTENER", "ERROR_BANNER");
                }
            });


            AdLibManager.getInstance().setInterstitialListener(mInterstitialAdView, new AdLibInterstitialListener()
            {
                @Override
                public void onLoad()
                {
                    Log.d("LISTENER", "LOAD_INTERSTITIAL");
                    mInterstitialAdView.showInterstitial();
                }

                @Override
                public void onClick() { Log.d("LISTENER", "CLICK_INTERSTITIAL"); }

                @Override
                public void onError() {
                    mInterstitialAdView.loadInterstitial();
                    Log.d("LISTENER", "ERROR_INTERSTITIAL");
                }

                @Override
                public void onShow() { Log.d("LISTENER", "SHOW_INTERSTITIAL"); }

                @Override
                public void onDismiss() {
                    Log.d("LISTENER", "DISMISS_INTERSTITIAL");
                    mInterstitialAdView.loadInterstitial();}
            });


            mWebView = (WebView) rootView.findViewById(R.id.web_view);
            mWebView.loadUrl("http://en.wikipedia.org/wiki/Main_Page");
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //AdLibManager.getInstance().loadNextAd(mAdView);
                   // AdLibManager.getInstance().loadInterstitialAd(mInterstitialAdView);
                    return false;
                }

            });

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            //AdLibManager.getInstance().loadNextAd(mAdView);
           // mInterstitialAdView.loadInterstitial();
            Log.d("RESUME", "ON RESUME");
        }

        @Override
        public void onStop() {
            super.onStop();
            AdLibManager.getInstance().stop();
        }

        public void setAdUnit(String adUnit) {
            mTopBannerUnitId = adUnit;
            if (mAdView != null) {
                mAdView.setAdUnit(mTopBannerUnitId);
            }
        }
    }
}
