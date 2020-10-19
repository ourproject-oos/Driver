package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferencesUser =
                        SplashActivity.this.getSharedPreferences("user_sp", MODE_PRIVATE);
                File fileSharedPref =
                        new File("/data/data/" + getPackageName() + "/shared_prefs/" +
                                getString(R.string.shared_preference_usr) + ".xml");
                if (fileSharedPref.exists()) {


                    startActivity(new Intent(SplashActivity.this, MainAllActivity.class));



                } else {


                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));


                }


                finish();


            }
        }, 3000);


    }
}