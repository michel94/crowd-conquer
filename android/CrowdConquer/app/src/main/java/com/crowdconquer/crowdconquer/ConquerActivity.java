package com.crowdconquer.crowdconquer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.crowdconquer.crowdconquer.services.LocationService;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class ConquerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conquer);

        startLocationService();
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }
}
