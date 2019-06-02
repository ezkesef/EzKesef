package com.hickry.ezkesef;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import static android.app.PendingIntent.getActivity;

public class EarnActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private static final String TAG = "MainActivity";
    private Button btnVideo;
    private AdView mAdView2;
    private RewardedVideoAd mRewardedVideoAd;
    private SharedPreferences pointsAmount;

    FirebaseFirestore db;
    long points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn);
        btnVideo = findViewById(R.id.btnVideo);
        MobileAds.initialize(this, "ca-app-pub-7714207943341773~7825460145");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest);
        db = FirebaseFirestore.getInstance();

        pointsAmount = getSharedPreferences("points", Context.MODE_PRIVATE);
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mRewardedVideoAd.isLoaded()) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "not loaded", Toast.LENGTH_SHORT);
                }
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }

            }
        });

    }

    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(getApplicationContext(), "onRewarded", Toast.LENGTH_LONG).show();
        db.collection("Users").document("usersdocid").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Toast.makeText(getApplicationContext(), "onSuccess", Toast.LENGTH_LONG).show();
                points = documentSnapshot.getLong("points");
                points += 2;
                db.collection("Users").document("usersdocid").update("points", points);
            }
    });



    }

        // Reward the user.

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        // Load the next rewarded video ad.
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
    }

    @Override
    public void onRewardedVideoAdLoaded() {
            Toast.makeText(this, "פרסומת מוכנה לצפייה", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoCompleted() {
    }
}