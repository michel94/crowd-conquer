package com.crowdconquer.crowdconquer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
    //views
    Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        startListeners();
    }

    private void initViews() {
        buttonSignIn = (Button) findViewById(R.id.btn_sign_in);
    }

    private void startListeners() {
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
