package com.crowdconquer.crowdconquer.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crowdconquer.crowdconquer.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.rey.material.widget.FloatingActionButton;

import com.crowdconquer.crowdconquer.google.GoogleClient;


public class LoginActivity extends Activity {

    FloatingActionButton locationButton;
    FloatingActionButton loginButton;
    TextView loginStatusText;

    GoogleClient googleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        startListeners();
        initGoogleApiClient();
    }

    void initViews() {
        setContentView(R.layout.activity_login);
        locationButton = (FloatingActionButton) findViewById(R.id.locationButton);
        loginButton = (FloatingActionButton) findViewById(R.id.loginButton);
        loginStatusText = (TextView) findViewById(R.id.loginStatusText);
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
        if (isAppReadyToLogin()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    boolean isAppReadyToLogin() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS and try again");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            return false;
        } else {
            return true;
        }
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
