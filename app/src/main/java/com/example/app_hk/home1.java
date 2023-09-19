package com.example.app_hk;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home1 extends AppCompatActivity {
    private TextView rewardTextView;
    private int reward;
    FirebaseAuth auth;
    Button logoutbtn;
    TextView ctruser;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home1);


        auth=FirebaseAuth.getInstance();
        logoutbtn = findViewById(R.id.logoutbtn);
        ctruser = (TextView) findViewById(R.id.ctruser);
        user=auth.getCurrentUser();
        if(user==null)
        {
            Intent intent = new Intent(getApplicationContext(), signin.class);
            startActivity(intent);
            finish();
        }
        else
        {
            //ctruser.setText(user.getEmail());
            rewardTextView = findViewById(R.id.rewardTextView);
            Button increaseButton = findViewById(R.id.increaseButton);

            // Load the reward from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            reward = sharedPreferences.getInt("reward", 0);
            rewardTextView.setText("Reward: " + reward);

            increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Increase the reward and update the TextView
                    reward++;
                    rewardTextView.setText("Reward: " + reward);

                    // Save the reward to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("reward", reward);
                    editor.apply();
                }
            });

        }
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), signin.class);
                startActivity(intent);
                finish();
            }
        });





    }
}
