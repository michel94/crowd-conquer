package com.crowdconquer.crowdconquer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesUtil;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        Log.e("googleapi", "" + resultCode);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

}
