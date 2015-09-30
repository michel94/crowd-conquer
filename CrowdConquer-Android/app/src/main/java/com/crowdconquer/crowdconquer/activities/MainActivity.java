package com.crowdconquer.crowdconquer.activities;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.crowdconquer.crowdconquer.global.LocationHelper;
import com.crowdconquer.crowdconquer.utils.Callback;
import com.crowdconquer.crowdconquer.R;
import com.crowdconquer.crowdconquer.utils.RPCCallback;
import com.crowdconquer.crowdconquer.global.Network;
import com.crowdconquer.crowdconquer.utils.ExtendedHandler;

public class MainActivity extends Activity {
    String T = "L";

    WebView webView;

    LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        startListeners();
        initLocationHelper();
        startNetworkTask();
    }

    void initViews() {
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webViewId);

        /* // needed for buggy phones
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient()); */
        
        webView.loadUrl("http://crowdconquer.meteor.com/");
    }

    void startListeners() {

    }

    void initLocationHelper() {
        locationHelper = new LocationHelper((LocationManager) getSystemService(LOCATION_SERVICE));
        locationHelper.checkLocationManagerStatus(this);
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
        Log.d(T, "Frontend is connected");
        Network.networkTask.getRpc().hello(new RPCCallback() {
            @Override
            public void action(Object response) {
                String info = (String) response;
                Log.d(T, "received response on callback: " + info);
            }
        });
    }
}
