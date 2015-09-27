package com.crowdconquer.crowdconquer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.crowdconquer.crowdconquer.utils.Callback;
import com.crowdconquer.crowdconquer.R;
import com.crowdconquer.crowdconquer.utils.RPCCallback;
import com.crowdconquer.crowdconquer.global.Network;


public class MainActivity extends Activity {
    String T = "L";

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        startListeners();
        startNetworkTask();
    }

    void initViews() {
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webViewId);
        webView.loadUrl("http://crowdconquer.meteor.com/");
    }

    void startListeners() {

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
    }

    public void onConnect(){
        Log.d(T, "Frontend is connected");
        Network.networkTask.getRpc().hello(new RPCCallback() {
            @Override
            public void action(Object response) {
                String info = (String) response;
                Log.d(T, "received response: " + info);
            }
        });

    }

}
