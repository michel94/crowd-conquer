package com.crowdconquer.crowdconquer.global;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;


public class LocationHelper {

    LocationManager locationManager;

    public LocationHelper(LocationManager locationManager){
        this.locationManager = locationManager;
    }

    public boolean checkLocationManagerStatus(Context context){
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            initLocationSettings(context);
            return false;
        } else {
            return true;
        }
    }

    void initLocationSettings(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Location Services Not Active");
        builder.setMessage("Please enable Location Services and GPS and try again");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        Dialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

}
