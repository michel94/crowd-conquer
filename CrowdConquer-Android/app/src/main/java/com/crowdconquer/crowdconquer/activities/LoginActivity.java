package com.crowdconquer.crowdconquer.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crowdconquer.crowdconquer.R;
import com.crowdconquer.crowdconquer.global.LocationHelper;
import com.rey.material.widget.FloatingActionButton;

import com.crowdconquer.crowdconquer.google.GoogleClient;


public class LoginActivity extends Activity {

    FloatingActionButton locationButton;
    FloatingActionButton loginButton;
    TextView loginStatusText;

    GoogleClient googleClient;

    LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewsLogin();
        startListeners();
        initGoogleApiClient();
        initLocationHelper();

    }


    void initViewsLogin() {
        setContentView(R.layout.activity_login);
        locationButton = (FloatingActionButton) findViewById(R.id.locationButton);
        loginButton = (FloatingActionButton) findViewById(R.id.loginButton);
        loginStatusText = (TextView) findViewById(R.id.loginStatusText);
    }

    void initLocationHelper(){
        locationHelper = new LocationHelper((LocationManager) getSystemService(LOCATION_SERVICE));
        locationHelper.checkLocationManagerStatus(this);
    }


    void startListeners() {
        setOnClickListenerLogin();
    }

    void setOnClickListenerLogin() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGoogleSignIn();
            }
        });
    }

    void loadMainActivity() {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

    }

    //Google Account
    private void initGoogleApiClient() {
        googleClient = new GoogleClient(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleClient.mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleClient.mGoogleApiClient.disconnect();
    }

    void startGoogleSignIn() {
        googleClient.mShouldResolve = true;
        googleClient.mGoogleApiClient.connect();
        loginStatusText.setText("Signing you in...");
    }

}
