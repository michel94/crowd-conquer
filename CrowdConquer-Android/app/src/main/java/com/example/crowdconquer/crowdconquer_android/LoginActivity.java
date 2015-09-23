package com.example.crowdconquer.crowdconquer_android;

import android.app.Activity;
import android.os.Bundle;

import com.rey.material.widget.ProgressView;


public class LoginActivity extends Activity {

    ProgressView loadingLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //loadingLogin = (ProgressView) findViewById(R.id.progressLoginId);
        //loadingLogin.start();
    }
}
