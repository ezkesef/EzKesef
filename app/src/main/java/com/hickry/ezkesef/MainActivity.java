package com.hickry.ezkesef;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private AdView mAdView;
    private Button btnEarn;
    private TextView tvAmount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MobileAds.initialize(this, "ca-app-pub-7714207943341773~7825460145");
        btnEarn = findViewById(R.id.angry_btn);
        btnEarn.setOnClickListener(this);
        tvAmount = findViewById(R.id.textView2);
        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d(TAG,"Error:"+e.getMessage());
                }
                else {
                    Toast.makeText(getApplicationContext(),"test" ,  Toast.LENGTH_LONG);
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {

                    switch (dc.getType()) {
                        case ADDED:


                            tvAmount.setText(dc.getDocument().getLong("points") + "");
                            Toast.makeText(getApplicationContext(), dc.getDocument().getLong("points") + "", Toast.LENGTH_LONG);

                            break;
                        case MODIFIED:

                            tvAmount.setText(dc.getDocument().getLong("points") + "");
                            Toast.makeText(getApplicationContext(), dc.getDocument().getLong("points") + "", Toast.LENGTH_LONG);

                            break;
                        case REMOVED:

                            break;
                    }


                }
            }
            }
        });

    }

    @Override
    public void onClick(View v) {
        openEarnActivity();
    }
    public void openEarnActivity(){
        Intent intent = new Intent(this, EarnActivity.class);
        startActivity(intent);
    }
}
