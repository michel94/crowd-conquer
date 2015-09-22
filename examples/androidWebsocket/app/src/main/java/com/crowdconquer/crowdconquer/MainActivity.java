package com.crowdconquer.crowdconquer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Log.*;

import com.neovisionaries.ws.client.WebSocket;

import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class MainActivity extends AppCompatActivity {
    private String T = "Main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new NetworkManager().execute();
    }

}
