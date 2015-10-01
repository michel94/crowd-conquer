package com.crowdconquer.crowdconquer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crowdconquer.crowdconquer.R;
import com.crowdconquer.crowdconquer.utils.Callback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.rey.material.widget.FloatingActionButton;

import com.crowdconquer.crowdconquer.google.GoogleClient;


public class LoginActivity extends Activity {
    String TAG = "LoginActivity";

    FloatingActionButton locationButton;
    FloatingActionButton loginButton;

    GoogleClient googleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewsLogin();
        startListeners();
        initGoogleApiClient();
    }

    void initViewsLogin() {
        setContentView(R.layout.activity_login);
        locationButton = (FloatingActionButton) findViewById(R.id.locationButton);
        loginButton = (FloatingActionButton) findViewById(R.id.loginButton);
    }

    void startListeners() {
        setOnClickListenerLogin();
    }

    void setOnClickListenerLogin() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleClient.startSignIn();
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
        googleClient.onConnectedCallback(new Callback() {
            @Override
            public void action() {
                loadMainActivity();
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == googleClient.RC_SIGN_IN) {
            googleClient.processResolution(resultCode, RESULT_OK);
        }
    }
}
