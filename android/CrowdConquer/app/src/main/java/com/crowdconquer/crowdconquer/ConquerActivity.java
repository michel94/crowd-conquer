package com.crowdconquer.crowdconquer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crowdconquer.crowdconquer.data.StaticData;
import com.crowdconquer.crowdconquer.services.BackgroundLocationService;

import org.w3c.dom.Text;

public class ConquerActivity extends Activity {

    private int progress = 0;
    private ProgressBar conquerProgressBar;
    private TextView conquerTextProgress;
    private TextView latlongTextView;
    private TextView usernameTextView;
    private TextView ownerTextView;
    private TextView textViewPhrase;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private boolean canIStart;
    private Context mContext;
    private String Owner;
    private String Team;
    private String Ratio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        canIStart = true;
        mContext = this;

        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);

        isGPSEnabled =locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled =locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isNetworkEnabled == false || isGPSEnabled == false) {
            showSettings(this);
        }
        else{
            initAllElements();
        }
    }


    private void showSettings(final Context mContext) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS or WIFI is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                initAllElements();
            }
        });
        alert.show();
    }


    private void initViews() {
        conquerProgressBar = (ProgressBar)findViewById(R.id.circularProgressbar);
        conquerTextProgress = (TextView)findViewById(R.id.conquerTextProgress);
        usernameTextView = (TextView)findViewById(R.id.textViewEmail);
        latlongTextView = (TextView)findViewById(R.id.textViewLatLong);
        ownerTextView = (TextView)findViewById(R.id.textViewOwner);
        textViewPhrase = (TextView)findViewById(R.id.textViewPhrase);

    }

    private void initAllElements(){

        setContentView(R.layout.activity_conquer);
        startLocationService();
        initViews();
        new Thread(updateProgressBar).start();

        usernameTextView.setText(getAccountInfo());
        latlongTextView = (TextView)findViewById(R.id.textViewLatLong);

    }

    private void startLocationService() {
        Intent intent = new Intent(this, BackgroundLocationService.class);
        startService(intent);
    }

    private Runnable updateProgressBar = new Runnable() {
        @Override
        public void run() {
        while (true) {
            if (progress == 100) {
                progress = 0;
            }
            progress++;
            runOnUiThread(refreshProgressBar);
            try {
                Thread.sleep(16);
            } catch (InterruptedException ignored) { }
        }
        }
    };

    private Runnable refreshProgressBar = new Runnable() {
        @Override
        public void run() {
        conquerProgressBar.setProgress(progress);
        conquerTextProgress.setText(progress + "%");

        if(StaticData.user.getLocation() != null) {
            String textTimer = StaticData.user.getTimeToWin();
            String textLatLong = "Lat: " + Double.toString(StaticData.user.getLocation().getLatitude()) + " Long:" + Double.toString(StaticData.user.getLocation().getLongitude());

            latlongTextView.setText(textLatLong);
        }
        if(StaticData.user.getTerritoryOwner() != null){
            String textTileOwner = StaticData.user.getTerritoryOwner();


            Owner = textTileOwner.substring(9,10);
            Team = textTileOwner.substring(18,19);
            //Ratio = textTileOwner.substring(28,33);
            textTileOwner = Owner;
            //Log.e("PUSH OWNER",textTileOwner);
            //Log.e("PUSH TEAM",Team);
            //Log.e("PUSH RATIO", Ratio);

            ownerTextView.setText("Actual Owner Team Number:\n" + textTileOwner);
            usernameTextView.setText(getAccountInfo() + Team);
            if(Owner.equals(Team))
                textViewPhrase.setText("DEFEND YOUR TERRITORY FROM INCOMING ENEMY FORCES!");
            else
                textViewPhrase.setText("KEEP GOING STRANGER THIS TILE IS ALMOST YOURS!");
        }
        else {
            ownerTextView.setText("Connecting ...");
            usernameTextView.setText("Connecting ... ");
        }
        if(StaticData.user.getTimeToWin() != null){
            String textTimeToWin = StaticData.user.getTimeToWin();
            //Log.e("REKT JSON TIME", textTimeToWin);
        }
        else{

        }
            //Log.e("REKT JSON TIME", "TIME IS FUCKING RELATIVE, GET OVER IT");
    }
    };

    private String getAccountInfo() {
        AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();

        String currentAccount = null;
        for (Account account: list) {
            currentAccount = account.name;
            if (currentAccount.contains("@gmail.com"))
                break;
            else currentAccount = null;
        }
        String[] splits = currentAccount.split("@");
            currentAccount = splits[0]  + " : Team ";

        return currentAccount;
    }
}
