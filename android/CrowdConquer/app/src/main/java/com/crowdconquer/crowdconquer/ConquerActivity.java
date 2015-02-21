package com.crowdconquer.crowdconquer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crowdconquer.crowdconquer.services.Api;
import com.crowdconquer.crowdconquer.services.BackgroundLocationService;

public class ConquerActivity extends Activity {
    private int progress = 0;

    //views
    private ProgressBar conquerProgressBar;
    private TextView conquerTextProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isGPSEnabled;
        boolean isNetworkEnabled;
        boolean canIStart = true;

        Context mContext = this;

        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);

        isGPSEnabled =locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled =locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isNetworkEnabled == false || isGPSEnabled == false) {

              showSettings(this);
            }

        isGPSEnabled =locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled =locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(isNetworkEnabled == false && isGPSEnabled == false) {
            finish();
        }
        else {
            setContentView(R.layout.activity_conquer);

            startLocationService();
            initViews();
            new Thread(updateProgressBar).start();
        }
    }


    private void showSettings(final Context mContext) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS or WIFI is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);

            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        // Showing Alert Message
        alertDialog.show();

    }


    private void initViews() {
        conquerProgressBar = (ProgressBar)findViewById(R.id.circularProgressbar);
        conquerTextProgress = (TextView)findViewById(R.id.conquerTextProgress);
    }

    private void startLocationService() {
        Intent intent = new Intent(this, BackgroundLocationService.class);
        startService(intent);
    }

    private Runnable updateProgressBar = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (progress == 100) {
                    Api.startConquer();
                    break;
                }
                progress++;
                runOnUiThread(refreshProgressBar);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) { }
            }
        }
    };

    private Runnable refreshProgressBar = new Runnable() {
        @Override
        public void run() {
            conquerProgressBar.setProgress(progress);
            conquerTextProgress.setText(progress + "%");
        }
    };
}
