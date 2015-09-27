package com.example.crowdconquer.crowdconquer_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.rey.material.widget.FloatingActionButton;

class ExtendedHandler extends Handler {
    private RPC rpc;

    public void setRPC(RPC rpc){
        this.rpc = rpc;
    }
    public ExtendedHandler(){
    }

    @Override
    public void handleMessage(Message message){

        int id = message.getData().getInt("callbackId");
        RPCCallback callback = rpc.getCallback(id);
        Object response = rpc.getResponse(id);

        callback.action(response);
    }
}

public class LoginActivity extends Activity {
    FloatingActionButton loginButton;
    String T = "L";

    private NetworkTask networkTask; // TODO Where should we declare variables that are going to be accessed by all activities?


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (FloatingActionButton) findViewById(R.id.fabId);

        setOnClickListenerLogin();

        networkTask = new NetworkTask();

        ExtendedHandler handler = new ExtendedHandler();
        networkTask.setHandler(handler);


        networkTask.onConnect(new Callback() {
            @Override
            public void action() {
                onConnect();
            }
        });


        networkTask.start();


    }

    public void onConnect() {
        Log.d(T, "Frontend is connected");

        networkTask.getRpc().hello(new RPCCallback() {
            @Override
            public void action(Object response) {
                String info = (String) response;
                Log.d(T, "received response on callback: " + info);
            }
        });


    }


    void setOnClickListenerLogin(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAppReadyToLogin()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    boolean isAppReadyToLogin(){

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS and try again");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            return false;
        }else{
            return true;
        }

    }


}
