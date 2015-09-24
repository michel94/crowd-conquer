package com.example.crowdconquer.crowdconquer_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.FloatingActionButton;



public class LoginActivity extends Activity {

    FloatingActionButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (FloatingActionButton) findViewById(R.id.fabId);

        setOnClickListenerLogin();



    }


    void setOnClickListenerLogin(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }


}
