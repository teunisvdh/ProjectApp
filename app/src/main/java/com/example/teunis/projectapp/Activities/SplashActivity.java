/* SplashActivity generates a splash screen containing the app logo. It automatically
 * goes on to TimeActivity after 1.5 seconds.
 */

package com.example.teunis.projectapp.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.teunis.projectapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, TimeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
