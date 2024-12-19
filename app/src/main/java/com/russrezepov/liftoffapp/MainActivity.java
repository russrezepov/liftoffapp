package com.russrezepov.liftoffapp;


import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;
import static androidx.core.content.ContentProviderCompat.requireContext;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.russrezepov.liftoff.R;
import com.vungle.ads.BannerAd;
import com.vungle.ads.BannerAdListener;
import com.vungle.ads.BannerAdSize;
import com.vungle.ads.BannerView;
import com.vungle.ads.BaseAdListener;
import com.vungle.ads.InitializationListener;
import com.vungle.ads.VungleAdSize;
import com.vungle.ads.VungleAds;
import com.vungle.ads.AdConfig;
import com.vungle.ads.BaseAd;
import com.vungle.ads.InterstitialAd;
import com.vungle.ads.InterstitialAdListener;
import com.vungle.ads.VungleBannerView;
import com.vungle.ads.VungleError;
import com.vungle.ads.internal.AdInternal;


public class MainActivity extends AppCompatActivity implements InterstitialAdListener, BannerAdListener, BaseAdListener {

    // Declare the variables definitions
    private static final String APP_ID = "643d1db1143d3bfd6bcf6510";
    private static final String BANNER_PLACEMENT_ID = "BANNER_NON_BIDDING-4570799";
    private static final String INTERSTITIAL_PLACEMENT_ID = "INTERSTITIAL_NON_BIDDING-5048200";
    private ViewGroup bannerAdContainer;
    private VungleBannerView bannerAd;
    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Buttons in the Layout
        Button showInterstitionalAdButton = findViewById(R.id.showInterstitialBtn);
        Button showBannerAdButton = findViewById(R.id.showBannerAdBtn);
        Button showCreditButton = findViewById(R.id.showCreditBtn);


        // Banner Container Relative Layout within the MainActivity Layout
        // for the BannerAd to be played
        bannerAdContainer = findViewById(R.id.bannerAdContainer);

        // Initializing Vungle SDK
        VungleAds.init(getApplicationContext(), APP_ID, new InitializationListener() {
            @Override
            public void onSuccess() {
                Log.d("VungleSDK", "SDK initialized successfully.");
                Toast.makeText(MainActivity.this, "Vungle SDK initialized successfully", Toast.LENGTH_SHORT).show();
                showBannerAd();
//                showInterstitialAd();
            }

            @Override
            public void onError(@NonNull VungleError vungleError) {
                Log.e("VungleSDK", "Initialization error: " + vungleError.getLocalizedMessage());
                Toast.makeText(MainActivity.this, "Vungle SDK initializtion failed", Toast.LENGTH_SHORT).show();
            }

            public void onAutoCacheAdAvailable(String placementId) {
                Log.d("Vungle", "Ad cached for placement: " + BANNER_PLACEMENT_ID);
            }
        });

        // The First Button From the top to show the Interstitional Ad
        showInterstitionalAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitialAd();
            }
        });

        //The Second Button From The Top to Show Banner Ad
        showBannerAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBannerAd();
            }
        });

        showCreditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Thank you for using the App. Created by Russ Rezepov", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Create and Load an interstitialAd.
    // The setAdListener registers a listener and enables the app
    // to dynamically respond to the events related to the Ad.
    private void showInterstitialAd() {
        interstitialAd = new InterstitialAd(MainActivity.this, INTERSTITIAL_PLACEMENT_ID, new AdConfig());
        interstitialAd.setAdListener(this);
        interstitialAd.load(null);
    }


    // Create and Load an BannerAd.
    // The setAdListener registers a listener and enables the app
    // to dynamically respond to the events related to the Ad.
    private void showBannerAd() {
        Toast.makeText(MainActivity.this, "Banner Ad", Toast.LENGTH_SHORT).show();
        bannerAd = new VungleBannerView(MainActivity.this, BANNER_PLACEMENT_ID, VungleAdSize.BANNER);
        bannerAd.setAdListener(this);
        bannerAd.load(null);
    }

    @Override
    public void onAdClicked(@NonNull BaseAd baseAd) {

    }

    @Override
    // onAdLoaded Invoked when the ad is successfully loaded
    // and ready to be displayed. For the demo purpose
    // the same shared Callback function is used for both
    // Interstitial and Banner Ads identifying the type of the Ad loaded.
    // As an alternative for complex projects I would recommend to use separate
    // listeners: BannerAdListener and InterstitialAdListener.

    public void onAdLoaded(@NonNull BaseAd baseAd) {

        // if the Ad loaded is the InterstitialAd
        // then play the Interstitial Ad
        if (baseAd instanceof InterstitialAd) {
            //Play InterstitialAd
            if (interstitialAd != null && interstitialAd.canPlayAd()) {
                interstitialAd.play(MainActivity.this);
                Toast.makeText(MainActivity.this, "Interstitial Ad Play", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Interstitial Ad is NULL", Toast.LENGTH_LONG).show();
            }
            // Otherwise, play the BannerAd.
        } else {

            VungleBannerView bannerView = bannerAd;
            ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_HORIZONTAL);
            bannerAdContainer.removeAllViews();
            bannerAdContainer.addView(bannerView, params);
        }
    }

    @Override
    public void onAdStart(@NonNull BaseAd baseAd) {

    }

    @Override
    public void onAdImpression(@NonNull BaseAd baseAd) {

    }

    @Override
    public void onAdEnd(@NonNull BaseAd baseAd) {}

    @Override
    public void onAdLeftApplication(@NonNull BaseAd baseAd) {

    }

    @Override
    public void onAdFailedToLoad(@NonNull BaseAd baseAd, @NonNull VungleError vungleError) {


    }

    @Override
    public void onAdFailedToPlay(@NonNull BaseAd baseAd, @NonNull VungleError vungleError) {
        Toast.makeText(MainActivity.this, "Inters Fail To Load" + vungleError, Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Destroy the banner when the activity is destroyed
        if (bannerAd != null) {
            bannerAdContainer.removeAllViews();
            bannerAd.finishAd();
            bannerAd.setAdListener(null);
            bannerAd = null;
        }
    }

}