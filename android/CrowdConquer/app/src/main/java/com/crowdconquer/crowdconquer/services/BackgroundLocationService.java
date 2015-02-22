package com.crowdconquer.crowdconquer.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.crowdconquer.crowdconquer.data.StaticData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class BackgroundLocationService extends Service implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener {

    public static final long UPDATE_INTERVAL = 10000;

    private LocationClient mLocationClient;
    private LocationRequest mLocationRequest;
    private boolean mInProgress;
    private boolean servicesAvailable = false;

    @Override
    public void onCreate() {
        super.onCreate();

        mInProgress = false;
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(UPDATE_INTERVAL);

        servicesAvailable = servicesConnected();
        setUpLocationClientIfNeeded();
    }

    private boolean servicesConnected() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        return ConnectionResult.SUCCESS == resultCode;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if(!servicesAvailable || mLocationClient.isConnected() || mInProgress)
            return START_STICKY;

        setUpLocationClientIfNeeded();

        if(!mLocationClient.isConnected() || !mLocationClient.isConnecting() && !mInProgress) {
            mInProgress = true;
            mLocationClient.connect();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        mInProgress = false;
        if(servicesAvailable && mLocationClient != null) {
            mLocationClient.removeLocationUpdates((com.google.android.gms.location.LocationListener) this);
            mLocationClient = null;
        }

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setUpLocationClientIfNeeded() {
        if(mLocationClient == null)
            mLocationClient = new LocationClient(this, this, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("rekt", String.valueOf(location));
        StaticData.user.setLocation(location);
        new Thread(sendLocation).start();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mLocationClient != null) {
            mLocationClient.requestLocationUpdates(mLocationRequest, this);
        }
    }

    @Override
    public void onDisconnected() {
        mInProgress = false;
        mLocationClient = null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mInProgress = false;

        if (!connectionResult.hasResolution()) {

        }
    }

    private Runnable sendLocation = new Runnable() {
        @Override
        public void run() {
            Api.sendLocation();
        }
    };
}

//TODO AUTO TURN-ON LOCATION IF DISABLED