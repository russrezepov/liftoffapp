//package com.russrezepov.liftoffapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.russrezepov.liftoff.R;
//import com.vungle.warren.AdConfig;
//import com.vungle.warren.Banners;
//import com.vungle.warren.InitCallback;
//import com.vungle.warren.LoadAdCallback;
//import com.vungle.warren.Vungle;
//import com.vungle.warren.VungleBanner;
//import com.vungle.warren.error.VungleException;
//
//public class MainActivityLegacy extends AppCompatActivity {
//    Button showInterstitialBtn,showBannerBtn;
//    ViewGroup bannerAdContainer;
//    VungleBanner vungleBanner;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_legacy);
//
//        showInterstitialBtn = findViewById(R.id.showInterstitialBtn);
//        showBannerBtn = findViewById(R.id.showBannerBtn);
//
//        bannerAdContainer = (LinearLayout) findViewById(R.id.bannerAdContainer);
//
//        Vungle.init("643d1db1143d3bfd6bcf6510", getApplicationContext(), new InitCallback() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(MainActivityLegacy.this, "Vungle SDK initialized successfully", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(VungleException exception) {
//                Toast.makeText(MainActivityLegacy.this, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onAutoCacheAdAvailable(String placementId) {
//
//            }
//        });
//
//        showInterstitialBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Vungle.loadAd("INTERSTITIAL_NON_BIDDING-5048200", new LoadAdCallback() {
//                    @Override
//                    public void onAdLoad(String id) {
//                        if (Vungle.canPlayAd("INTERSTITIAL_NON_BIDDING-5048200")){
//                            Vungle.playAd("INTERSTITIAL_NON_BIDDING-5048200",null,null);
//                        }
//                    }
//
//                    @Override
//                    public void onError(String id, VungleException exception) {
//                        Toast.makeText(MainActivityLegacy.this, exception.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//        showBannerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Banners.loadBanner("BANNER_NON_BIDDING-4570799", AdConfig.AdSize.BANNER, new LoadAdCallback() {
//                    @Override
//                    public void onAdLoad(String id) {
//                        if (Banners.canPlayAd("BANNER_NON_BIDDING-4570799", AdConfig.AdSize.BANNER)){
//                            vungleBanner = Banners.getBanner("BANNER_NON_BIDDING-4570799", AdConfig.AdSize.BANNER,null);
//                            bannerAdContainer.removeAllViews();
//                            bannerAdContainer.addView(vungleBanner);
//                        }
//                    }
//
//                    @Override
//                    public void onError(String id, VungleException exception) {
//                        Toast.makeText(MainActivityLegacy.this, exception.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        vungleBanner.destroyAd();
//        super.onDestroy();
//    }
//}
