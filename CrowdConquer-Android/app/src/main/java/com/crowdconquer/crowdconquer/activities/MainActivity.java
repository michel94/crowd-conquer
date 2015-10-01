package com.crowdconquer.crowdconquer.activities;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.crowdconquer.crowdconquer.global.LocationHelper;
import com.crowdconquer.crowdconquer.utils.Callback;
import com.crowdconquer.crowdconquer.R;
import com.crowdconquer.crowdconquer.utils.RPCCallback;
import com.crowdconquer.crowdconquer.global.Network;
import com.crowdconquer.crowdconquer.utils.ExtendedHandler;
import com.mapbox.mapboxgl.views.MapView;

public class MainActivity extends Activity {
    String TAG = "MainActivity";

    LocationHelper locationHelper;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
        initMapbox();
        startListeners();
        initLocationHelper();
        startNetworkTask();
    }

    void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mainMapView);
        mapView.setAccessToken("sk.eyJ1IjoiY3Jvd2Rjb25xdWVyIiwiYSI6ImNpZjhkamZ0eTAwNG90Zmx4eW5vYnBpa3IifQ.-VwL7o-gHsWOWAmfO_be-Q");
        mapView.onCreate(savedInstanceState);
    }

    void startListeners() {

    }

    void initLocationHelper() {
        locationHelper = new LocationHelper((LocationManager) getSystemService(LOCATION_SERVICE));
        locationHelper.checkLocationManagerStatus(this);
    }

    void initMapbox() {

    }

    void startNetworkTask() {
        if (!Network.networkTask.isRunning()) {
            Network.networkTask.onConnect(new Callback() {
                @Override
                public void action() {
                    onConnect();
                }
            });
            Network.networkTask.start();
        }

        ExtendedHandler handler = new ExtendedHandler();
        Network.networkTask.setHandler(handler);
    }

    public void onConnect() {
        Log.d(TAG, "Frontend is connected");
        Network.networkTask.getRpc().hello(new RPCCallback() {
            @Override
            public void action(Object response) {
                String info = (String) response;
                Log.d(TAG, "received response on callback: " + info);
            }
        });
    }
}
